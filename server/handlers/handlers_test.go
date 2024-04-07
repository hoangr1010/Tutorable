package handlers_test

import (
	"bytes"
	"database/sql"
	"encoding/json"
	"fmt"
	"net/http"
	"net/http/httptest"
	"testing"
	"time"

	"github.com/macewanCS/w24MacroHard/server/handlers"

	"github.com/macewanCS/w24MacroHard/server/util"
	"github.com/stretchr/testify/assert"
)

// READ THIS PLEASE:
//
// I MOVED THIS HERE TO TEST THE MOCK, THESE TESTS ARE STILL USABLE BUT YOU CAN KEEP USING THE MOCK INSTEAD IF YOU WANT
//
//
//

// RUNNING TESTS:
// 1. use "go test -coverprofile="coverage.out" ./..." | this will run the test and output an out file of results
// 2. use "go tool cover -html="coverage.out" -o ./reports/coverage.html"
// Step 2 will convert the out file to a detailed coverage report in html format the name of file is important
// because if you run the same command again then it will replace the old test reports (we want them as test logs)
// so naming scheme should follow: ie. "2024-03-04-handlers.out" and "2024-03-04-handlers.html"

// Define constants for database connection
const (
	DBHost     = "macrohard-onlytutor.cj0646k6g181.us-east-2.rds.amazonaws.com"
	DBPort     = 5432
	DBUser     = "MacroHard"
	DBPassword = "chopperiscute"
	DBName     = "postgres"
	Key        = "codingiscool"
)

// Test: Checks if the register handler works
func TestRegisterHandler(t *testing.T) {
	// Construct connection string
	connStr := fmt.Sprintf("host=%s port=%d user=%s password=%s dbname=%s sslmode=require", DBHost, DBPort, DBUser, DBPassword, DBName)

	// Open database connection
	db, err := sql.Open("postgres", connStr)
	if err != nil {
		t.Fatalf("Failed to connect to database: %v", err)
	}
	defer db.Close()

	// Create a sample register payload
	register := util.Register{
		Email:       "test@example.com",
		Password:    "password123",
		Role:        "student",
		FirstName:   "Joe",
		LastName:    "Feete",
		DateOfBirth: "07/12/1989",
		Expertise:   []string{"mathematics", "computer science", "literature"},
		Experience:  "senior",
		Description: "I am the greatest tutor to ever exist.",
		Degrees:     []string{"high school diploma", "proserve(expired)", "plat 4 in league of legends"},
		Grade:       12,
	}
	registerJSON, err := json.Marshal(register)
	if err != nil {
		t.Fatalf("Failed to marshal register JSON: %v", err)
	}

	// Create a new HTTP request with the register payload
	req := httptest.NewRequest(http.MethodPost, "/register", bytes.NewBuffer(registerJSON))
	req.Header.Set("Content-Type", "application/json")

	// Create a new recorder to record the response
	rr := httptest.NewRecorder()

	// Call the handler function with the recorder, request, and real DB
	handlers.RegisterHandler(db)(rr, req)

	// Check the response status code
	if rr.Code != http.StatusOK {
		t.Errorf("Handler returned wrong status code: got %v want %v", rr.Code, http.StatusOK)
	}

	// Print the response body (results)
	fmt.Println("Registration Results:")
	fmt.Println(rr.Body.String())
}

// Test: Tests the registration and login handler functionality.
func TestRegistrationAndLoginHandler(t *testing.T) {
	// Construct connection string
	connStr := fmt.Sprintf("host=%s port=%d user=%s password=%s dbname=%s sslmode=require", DBHost, DBPort, DBUser, DBPassword, DBName)

	// Open database connection
	db, err := sql.Open("postgres", connStr)
	if err != nil {
		t.Fatalf("Failed to connect to database: %v", err)
	}
	defer db.Close()

	fmt.Println("")
	fmt.Println("Testing Registration and Login Handlers")
	fmt.Println("")

	// Test case 1: Invalid email in login
	fmt.Println("Test 1: Invalid email in login")
	login := util.Login{
		Email:    "invalidemail@example.com",
		Password: "passwd",
		Role:     "tutor",
	}
	loginJSON, err := json.Marshal(login)
	assert.NoError(t, err, "Failed to marshal login JSON")

	req := httptest.NewRequest(http.MethodPost, "/login", bytes.NewBuffer(loginJSON))
	req.Header.Set("Content-Type", "application/json")

	rr := httptest.NewRecorder()
	handlers.LoginHandler(db)(rr, req)

	assert.Equal(t, http.StatusUnauthorized, rr.Code, "Handler returned wrong status code for invalid email")

	// Test case 2: Incorrect password in login
	fmt.Println("")
	fmt.Println("Test 2: Incorrect password in login")
	login = util.Login{
		Email:    "tutorjoe@COOLCODE.com",
		Password: "incorrectpassword",
		Role:     "tutor",
	}
	loginJSON, err = json.Marshal(login)
	assert.NoError(t, err, "Failed to marshal login JSON")

	req = httptest.NewRequest(http.MethodPost, "/login", bytes.NewBuffer(loginJSON))
	req.Header.Set("Content-Type", "application/json")

	rr = httptest.NewRecorder()
	handlers.LoginHandler(db)(rr, req)

	assert.Equal(t, http.StatusUnauthorized, rr.Code, "Handler returned wrong status code for incorrect password")

	// Test case 3: Missing role in login
	fmt.Println("")
	fmt.Println("Test 3: Missing role in login")
	login = util.Login{
		Email:    "tutorjoe@COOLCODE.com",
		Password: "passwd",
	}
	loginJSON, err = json.Marshal(login)
	assert.NoError(t, err, "Failed to marshal login JSON")

	req = httptest.NewRequest(http.MethodPost, "/login", bytes.NewBuffer(loginJSON))
	req.Header.Set("Content-Type", "application/json")

	rr = httptest.NewRecorder()
	handlers.LoginHandler(db)(rr, req)

	assert.Equal(t, http.StatusBadRequest, rr.Code, "Handler returned wrong status code for missing role")

	// Test case 4: Empty request body in login
	fmt.Println("")
	fmt.Println("Test 4: Empty request body in login")
	req = httptest.NewRequest(http.MethodPost, "/login", nil)

	rr = httptest.NewRecorder()
	handlers.LoginHandler(db)(rr, req)

	assert.Equal(t, http.StatusBadRequest, rr.Code, "Handler returned wrong status code for empty request body")

	// Test case 5: Successful registration and login
	fmt.Println("")
	fmt.Println("Test 5: Successful registration and login")
	register := util.Register{
		Email:       "test@example.com",
		Password:    "password123",
		Role:        "student",
		FirstName:   "Joe",
		LastName:    "Feete",
		DateOfBirth: "07/12/1989",
		Expertise:   []string{"mathematics", "computer science", "literature"},
		Experience:  "senior",
		Description: "I am the greatest tutor to ever exist.",
		Degrees:     []string{"high school diploma", "proserve(expired)", "plat 4 in league of legends"},
		Grade:       12,
	}
	registerJSON, err := json.Marshal(register)
	assert.NoError(t, err, "Failed to marshal register JSON")

	// Register new account
	reqRegister := httptest.NewRequest(http.MethodPost, "/register", bytes.NewBuffer(registerJSON))
	reqRegister.Header.Set("Content-Type", "application/json")

	rrRegister := httptest.NewRecorder()
	handlers.RegisterHandler(db)(rrRegister, reqRegister)

	assert.Equal(t, http.StatusOK, rrRegister.Code, "Handler returned wrong status code for registration")

	// Login with registered account
	login = util.Login{
		Email:    "test@example.com",
		Password: "password123",
		Role:     "student",
	}
	loginJSON, err = json.Marshal(login)
	assert.NoError(t, err, "Failed to marshal login JSON")

	reqLogin := httptest.NewRequest(http.MethodPost, "/login", bytes.NewBuffer(loginJSON))
	reqLogin.Header.Set("Content-Type", "application/json")

	rrLogin := httptest.NewRecorder()
	handlers.LoginHandler(db)(rrLogin, reqLogin)

	assert.Equal(t, http.StatusOK, rrLogin.Code, "Handler returned wrong status code for valid login")

	fmt.Println("")
	fmt.Println("Registration and Login Handlers test complete")
	fmt.Println("")
}

// Test: Checks if Add availability handlers works
func TestAddTutorAvailability(t *testing.T) {
	// Construct connection string
	connStr := fmt.Sprintf("host=%s port=%d user=%s password=%s dbname=%s sslmode=require", DBHost, DBPort, DBUser, DBPassword, DBName)

	// Open database connection
	db, err := sql.Open("postgres", connStr)
	if err != nil {
		t.Fatalf("Failed to connect to database: %v", err)
	}
	defer db.Close()

	// Define a mock request payload
	tutorAvailability := util.TutorAvailability{
		ID:              1,
		Date:            "2024-03-04",
		TimeBlockIdList: []int{1, 2, 3}, // Sample list of time block IDs
	}

	// Marshal request payload to JSON
	reqBody, err := json.Marshal(tutorAvailability)
	if err != nil {
		t.Fatalf("Failed to marshal JSON request body: %v", err)
	}

	// Create a new HTTP request with the mock payload
	req := httptest.NewRequest(http.MethodPost, "/add_tutor_availability", bytes.NewBuffer(reqBody))
	req.Header.Set("Content-Type", "application/json")

	// Create a new recorder to record the response
	rr := httptest.NewRecorder()

	// Call the handler function with the recorder and request
	handlers.AddTutorAvailability(db)(rr, req)
	// Check the response status code
	if rr.Code != http.StatusOK {
		t.Errorf("Handler returned wrong status code: got %v want %v", rr.Code, http.StatusOK)
	}

	// Parse the response body
	var response struct {
		Date            time.Time `json:"date"`
		TimeBlockIdList []int     `json:"time_block_id_list"`
	}
	if err := json.NewDecoder(rr.Body).Decode(&response); err != nil {
		t.Fatalf("Failed to decode JSON response: %v", err)
	}

	// Check if the response date matches the input date
	expectedDate, err := time.Parse("2006-01-02", tutorAvailability.Date)
	if err != nil {
		t.Fatalf("Failed to parse expected date: %v", err)
	}
	if !response.Date.Equal(expectedDate) {
		t.Errorf("Handler returned incorrect date: got %v want %v", response.Date, expectedDate)
	}

	// Check if the response time block IDs match the input time block IDs
	if len(response.TimeBlockIdList) != len(tutorAvailability.TimeBlockIdList) {
		t.Errorf("Handler returned incorrect number of time block IDs: got %d want %d", len(response.TimeBlockIdList), len(tutorAvailability.TimeBlockIdList))
	}
	for i := range response.TimeBlockIdList {
		if response.TimeBlockIdList[i] != tutorAvailability.TimeBlockIdList[i] {
			t.Errorf("Handler returned incorrect time block ID at index %d: got %d want %d", i, response.TimeBlockIdList[i], tutorAvailability.TimeBlockIdList[i])
		}
	}

	// Additional test cases:

	fmt.Println("")
	// Test case 2: Empty payload
	fmt.Println("Test case 2: Empty payload")
	reqEmpty := httptest.NewRequest(http.MethodPost, "/add_tutor_availability", nil)
	reqEmpty.Header.Set("Content-Type", "application/json")

	rrEmpty := httptest.NewRecorder()
	handlers.AddTutorAvailability(db)(rrEmpty, reqEmpty)
	if rrEmpty.Code != http.StatusBadRequest {
		t.Errorf("Handler returned wrong status code for empty payload: got %v want %v", rrEmpty.Code, http.StatusBadRequest)
	}

	fmt.Println("")
	// Test case 3: Invalid date format
	fmt.Println("Test case 3: Invalid date format")
	reqInvalidDate := httptest.NewRequest(http.MethodPost, "/add_tutor_availability", bytes.NewBuffer([]byte("{")))
	reqInvalidDate.Header.Set("Content-Type", "application/json")

	rrInvalidDate := httptest.NewRecorder()
	handlers.AddTutorAvailability(db)(rrInvalidDate, reqInvalidDate)
	if rrInvalidDate.Code != http.StatusBadRequest {
		t.Errorf("Handler returned wrong status code for invalid date format: got %v want %v", rrInvalidDate.Code, http.StatusBadRequest)
	}

	fmt.Println("")
	// Test case 4: Tutor with existing availability
	fmt.Println("Test case 4: Tutor with existing availability")
	rrExisting := httptest.NewRecorder()
	handlers.AddTutorAvailability(db)(rrExisting, req)
	if rrExisting.Code != http.StatusOK {
		t.Errorf("Handler returned wrong status code for tutor with existing availability: got %v want %v", rrExisting.Code, http.StatusOK)
	}

	fmt.Println("")
	fmt.Println("Add Tutor Availability test complete")
	fmt.Println("")
}

// Test: tests the GetTutorAvailability handler functionality.
func TestGetTutorAvailabilityHandler(t *testing.T) {
	// Construct connection string
	connStr := fmt.Sprintf("host=%s port=%d user=%s password=%s dbname=%s sslmode=require", DBHost, DBPort, DBUser, DBPassword, DBName)

	// Open database connection
	db, err := sql.Open("postgres", connStr)
	if err != nil {
		t.Fatalf("Failed to connect to database: %v", err)
	}
	defer db.Close()

	// Test case 1: Get Tutor Availability with valid input
	fmt.Println("Test case 1: Get Tutor Availability with valid input")

	// Define a mock tutor availability
	tutorAvailability := util.TutorAvailability{
		ID:   1,
		Date: "2024-03-04",
	}
	// Marshal tutor availability to JSON
	reqBody, err := json.Marshal(tutorAvailability)
	assert.NoError(t, err, "Failed to marshal JSON request body")

	// Create a new HTTP request with the mock payload
	req := httptest.NewRequest(http.MethodPost, "/get_tutor_availability", bytes.NewBuffer(reqBody))
	req.Header.Set("Content-Type", "application/json")

	// Create a new recorder to record the response
	rr := httptest.NewRecorder()

	// Call the handler function with the recorder and request
	handler := handlers.GetTutorAvailability(db)
	handler(rr, req)

	// Check the response status code
	assert.Equal(t, http.StatusOK, rr.Code, "Handler returned wrong status code for valid input")

	// Parse the JSON response
	var response struct {
		TimeBlockIDList []int `json:"time_block_id_list"`
	}
	err = json.NewDecoder(rr.Body).Decode(&response)
	assert.NoError(t, err, "Failed to decode JSON response")

	// Test case 2: Get Tutor Availability with invalid JSON
	fmt.Println("")
	fmt.Println("Test case 2: Get Tutor Availability with invalid JSON")

	// Create a new HTTP request with invalid JSON payload
	reqInvalid := httptest.NewRequest(http.MethodPost, "/get_tutor_availability", bytes.NewBufferString("{"))
	reqInvalid.Header.Set("Content-Type", "application/json")

	// Create a new recorder to record the response
	rrInvalid := httptest.NewRecorder()

	// Call the handler function with the recorder and request
	handler(rrInvalid, reqInvalid)

	// Check the response status code
	assert.Equal(t, http.StatusBadRequest, rrInvalid.Code, "Handler returned wrong status code for invalid JSON")
}

// Test: Test all availability handlers as a component
func TestAvailabilityHandlers(t *testing.T) {
	// Construct connection string
	connStr := fmt.Sprintf("host=%s port=%d user=%s password=%s dbname=%s sslmode=require", DBHost, DBPort, DBUser, DBPassword, DBName)

	// Open database connection
	db, err := sql.Open("postgres", connStr)
	if err != nil {
		t.Fatalf("Failed to connect to database: %v", err)
	}
	defer db.Close()

	// Test case 1: Add Tutor Availability with valid input
	fmt.Println("Test case 1: Add Tutor Availability with valid input")
	// Define a mock request payload
	tutorAvailability := util.TutorAvailability{
		ID:              1,
		Date:            "2024-03-04",
		TimeBlockIdList: []int{1, 2, 3}, // Sample list of time block IDs
	}
	// Marshal request payload to JSON
	reqBody, err := json.Marshal(tutorAvailability)
	assert.NoError(t, err, "Failed to marshal JSON request body")

	// Create a new HTTP request with the mock payload
	reqAdd := httptest.NewRequest(http.MethodPost, "/add_tutor_availability", bytes.NewBuffer(reqBody))
	reqAdd.Header.Set("Content-Type", "application/json")

	// Create a new recorder to record the response
	rrAdd := httptest.NewRecorder()

	// Call the handler function with the recorder and request
	handlers.AddTutorAvailability(db)(rrAdd, reqAdd)
	assert.Equal(t, http.StatusOK, rrAdd.Code, "Handler returned wrong status code for valid input")

	// Test case 2: Add Tutor Availability with empty payload
	fmt.Println("")
	fmt.Println("Test case 2: Add Tutor Availability with empty payload")
	reqEmpty := httptest.NewRequest(http.MethodPost, "/add_tutor_availability", nil)
	reqEmpty.Header.Set("Content-Type", "application/json")

	rrEmpty := httptest.NewRecorder()
	handlers.AddTutorAvailability(db)(rrEmpty, reqEmpty)
	assert.Equal(t, http.StatusBadRequest, rrEmpty.Code, "Handler returned wrong status code for empty payload")

	// Test case 3: Add Tutor Availability with invalid date format
	fmt.Println("")
	fmt.Println("Test case 3: Add Tutor Availability with invalid date format")
	reqInvalidDate := httptest.NewRequest(http.MethodPost, "/add_tutor_availability", bytes.NewBuffer([]byte("{")))
	reqInvalidDate.Header.Set("Content-Type", "application/json")

	rrInvalidDate := httptest.NewRecorder()
	handlers.AddTutorAvailability(db)(rrInvalidDate, reqInvalidDate)
	assert.Equal(t, http.StatusBadRequest, rrInvalidDate.Code, "Handler returned wrong status code for invalid date format")

	// Test case 4: Search Tutor Availability
	fmt.Println("")
	fmt.Println("Test case 4: Search Tutor Availability")

	// Mock request body
	requestBody := util.TutorAvailability{
		Date:            "2024-03-04",
		TimeBlockIdList: []int{1, 2, 3},
	}
	// Marshal request body to JSON
	requestJSON, err := json.Marshal(requestBody)
	assert.NoError(t, err, "Failed to marshal JSON request body")

	// Create a new HTTP request with the mock payload
	reqSearch := httptest.NewRequest(http.MethodPost, "/search_tutor_availability", bytes.NewBuffer(requestJSON))
	reqSearch.Header.Set("Content-Type", "application/json")

	// Create a new recorder to record the response
	rrSearch := httptest.NewRecorder()

	// Call the handler function with the recorder and request
	handlerSearch := handlers.SearchTutorAvailability(db)
	handlerSearch(rrSearch, reqSearch)

	// Check the response status code
	assert.Equal(t, http.StatusOK, rrSearch.Code, "Handler returned wrong status code for search tutor availability")

	// Parse the JSON response
	var response struct {
		TutorList []util.User `json:"tutor_list"`
	}
	err = json.NewDecoder(rrSearch.Body).Decode(&response)
	assert.NoError(t, err, "Failed to decode JSON response")

}

// go test -coverprofile="delete_session.out" ./...
// go tool cover -html="delete_session.out" -o ./reports/delete_session.html
func TestDeleteandAddTutorSession(t *testing.T) {
	// Construct connection string
	connStr := fmt.Sprintf("host=%s port=%d user=%s password=%s dbname=%s sslmode=require", DBHost, DBPort, DBUser, DBPassword, DBName)

	// Open database connection
	db, err := sql.Open("postgres", connStr)
	if err != nil {
		t.Fatalf("Failed to connect to database: %v", err)
	}
	defer db.Close()

	fmt.Println("")
	fmt.Println("DeleteTutorSession and AddTutoring test")

	type SessionID struct {
		SessionID int `json:"tutor_session_id"`
	}

	session := SessionID{
		SessionID: 1,
	}

	// Marshal session to JSON
	sessionJSON, err := json.Marshal(session)
	assert.NoError(t, err, "Failed to marshal session JSON")

	reqAdd := httptest.NewRequest(http.MethodPost, "/add_tutoring_session", bytes.NewBuffer(sessionJSON))
	reqAdd.Header.Set("Content-Type", "application/json")

	rrAdd := httptest.NewRecorder()

	// Call AddTutoringSession handler function
	handlers.AddTutoringSession(db)(rrAdd, reqAdd)

	// Check if the response status code is 200 OK
	assert.Equal(t, http.StatusOK, rrAdd.Code, "Expected 200 OK response")

	sessionJSON, err = json.Marshal(session)
	assert.NoError(t, err, "Failed to marshal session ID JSON")

	reqDel := httptest.NewRequest(http.MethodPost, "/delete_tutoring_session", bytes.NewBuffer(sessionJSON))
	reqDel.Header.Set("Content-Type", "application/json")

	rrDel := httptest.NewRecorder()

	// Call DeleteTutorSession handler function
	handlers.DeleteTutorSession(db)(rrDel, reqDel)

	// Check if the response status code is 200 OK
	assert.Equal(t, http.StatusOK, rrDel.Code, "Expected 200 OK response")

	fmt.Println("")
	fmt.Println("DeleteTutorSession and AddTutoring test complete")
	fmt.Println("")
}

func TestDeleteTutorSession(t *testing.T) {
	// Construct connection string
	connStr := fmt.Sprintf("host=%s port=%d user=%s password=%s dbname=%s sslmode=require", DBHost, DBPort, DBUser, DBPassword, DBName)

	// Open database connection
	db, err := sql.Open("postgres", connStr)
	if err != nil {
		t.Fatalf("Failed to connect to database: %v", err)
	}
	defer db.Close()

	fmt.Println("")
	fmt.Println("Testing DeleteTutorSession")
	fmt.Println("")

	type SessionID struct {
		SessionID int `json:"tutor_session_id"`
	}

	session := SessionID{
		SessionID: 1,
	}

	// Marshal session to JSON
	sessionJSON, err := json.Marshal(session)
	assert.NoError(t, err, "Failed to marshal session JSON")

	reqDel := httptest.NewRequest(http.MethodPost, "/delete_tutoring_session", bytes.NewBuffer(sessionJSON))
	reqDel.Header.Set("Content-Type", "application/json")

	rrDel := httptest.NewRecorder()

	// Call DeleteTutorSession handler function
	handlers.DeleteTutorSession(db)(rrDel, reqDel)

	// Check if the response status code is 200 OK
	assert.Equal(t, http.StatusOK, rrDel.Code, "Expected 200 OK response")

	fmt.Println("")
	fmt.Println("DeleteTutorSession test complete")
	fmt.Println("")
}

func TestDeleteTutorSession2(t *testing.T) {
	// Construct connection string
	connStr := fmt.Sprintf("host=%s port=%d user=%s password=%s dbname=%s sslmode=require", DBHost, DBPort, DBUser, DBPassword, DBName)

	// Open database connection
	db, err := sql.Open("postgres", connStr)
	if err != nil {
		t.Fatalf("Failed to connect to database: %v", err)
	}
	defer db.Close()

	fmt.Println("")
	fmt.Println("Testing DeleteTutorSession")
	fmt.Println("")

	// Create a tutoring session to delete
	session := util.TutoringSession{
		TutoringSessionID: 29,
		TutorID:           1,
		StudentID:         2,
		Date:              "2024-04-01",
		TimeBlockIDList:   []int{10, 11},
	}

	// Insert the session into the database
	err = util.InsertTutoringSession(db, session)
	assert.NoError(t, err, "Failed to insert tutoring session")

	// Create JSON payload for deletion
	payload := map[string]int{
		"session_id": 29,
	}

	payloadJSON, err := json.Marshal(payload)
	assert.NoError(t, err, "Failed to marshal payload JSON")

	reqDelete := httptest.NewRequest(http.MethodPost, "/delete_tutoring_session", bytes.NewBuffer(payloadJSON))
	reqDelete.Header.Set("Content-Type", "application/json")

	rrDelete := httptest.NewRecorder()

	// Call the handler
	handlers.DeleteTutorSession(db)(rrDelete, reqDelete)

	// Check the response status code
	assert.Equal(t, http.StatusOK, rrDelete.Code, "Expected 200 OK Response")

	// Unmarshal the response JSON
	var response struct {
		SessionIDDeleted int   `json:"session_id_deleted"`
		TimeBlockIDList  []int `json:"time_block_id_list"`
	}
	err = json.Unmarshal(rrDelete.Body.Bytes(), &response)
	assert.NoError(t, err, "Failed to unmarshal response JSON")

	// Check the deleted session ID and time block IDs
	assert.Equal(t, 29, response.SessionIDDeleted, "Unexpected deleted session ID")
	assert.ElementsMatch(t, []int{10, 11}, response.TimeBlockIDList, "Unexpected time block IDs")

	fmt.Println("")
	fmt.Println("Testing DeleteTutorSession Complete")
	fmt.Println("")
}

// Input:
// {
//   “tutor_id”: int
//   “student_id”: int
//   “name”: string
//   “description”: string
//   “subject:” tutoring_subject
//   “grade”: grade
//   ‘’date”: “YYYY-MM-DD”
//   “time_block_id_list”: [unique <time_block_id<Int>>]
// }
//
// Output:
// {
//   “time_block_id_list”: [unique <time_block_id<Int>>]
// }

// go test -coverprofile="add_session.out" ./...
// go tool cover -html="add_session.out" -o ./reports/add_session.html
func TestAddTutoringSession(t *testing.T) {
	// Construct connection string
	connStr := fmt.Sprintf("host=%s port=%d user=%s password=%s dbname=%s sslmode=require", DBHost, DBPort, DBUser, DBPassword, DBName)

	// Open database connection
	db, err := sql.Open("postgres", connStr)
	if err != nil {
		t.Fatalf("Failed to connect to database: %v", err)
	}
	defer db.Close()

	fmt.Println("")
	fmt.Println("Testing Adding Tutor Session")
	fmt.Println("")

	// Define a sample tutoring session payload
	tutoringSession := util.TutoringSession{
		TutorID:         1,
		StudentID:       10,
		Name:            "Stupid session",
		Description:     "Sample Description",
		Subject:         "computer science",
		Grade:           11,
		Date:            "2024-04-06",
		TimeBlockIDList: []int{1, 2},
	}

	// Marshal tutoring session to JSON
	tutoringSessionJSON, err := json.Marshal(tutoringSession)
	assert.NoError(t, err, "Failed to marshal tutoring session JSON")

	// Create a new HTTP request with the JSON payload
	reqAdd := httptest.NewRequest(http.MethodPost, "/add_tutoring_session", bytes.NewBuffer(tutoringSessionJSON))
	reqAdd.Header.Set("Content-Type", "application/json")

	// Create a new HTTP recorder
	rrAdd := httptest.NewRecorder()

	// Call AddTutoringSession handler function
	handlers.AddTutoringSession(db)(rrAdd, reqAdd)

	// Check if the response status code is 200 OK
	if rrAdd.Code != http.StatusOK {
		fmt.Println("Received non-200 response:", rrAdd.Code)
		fmt.Println("Raw Response Body:", rrAdd.Body.String())
		t.Fatalf("Received non-200 response: %d", rrAdd.Code)
		return // Exit the function to prevent further processing
	}

	// Attempt to parse the response body as JSON
	var responseBody map[string][]int
	err = json.Unmarshal(rrAdd.Body.Bytes(), &responseBody)
	if err != nil {
		fmt.Println("Failed to unmarshal response JSON:", err)
		fmt.Println("Response Body:", rrAdd.Body.String())
		t.Fatalf("Failed to unmarshal response JSON")
		return // Exit the function to prevent further processing
	}

	timeBlockIDList := responseBody["time_block_id_list"]

	// Print the timeBlockIDList for debugging
	fmt.Println("timeBlockIDList:", timeBlockIDList)

	// Check if all operations were successful -> return status code 200
	assert.Equal(t, http.StatusOK, rrAdd.Code, "Expected 200 OK response")

	fmt.Println("")
	fmt.Println("Testing Adding Tutor Session Complete")
	fmt.Println("")
}

//	input: {
//	  “session_id”: int (required)
//	  ‘’date”: “YYYY-MM-DD” (required)
//	  “time_block_id_list”: [unique <time_block_id<Int>>]
//	}
//
// output:(return list of time_block_id)
//
//	{
//		“session_id”: int (required)
//		‘’new_date”: “YYYY-MM-DD” (required)
//		“new_time_block_id_list”: [unique <time_block_id<Int>>]
//	  }
//
// go test -coverprofile="edit_session.out" ./...
// go tool cover -html="edit_session.out" -o ./reports/add_session.html
func TestEditSession(t *testing.T) {

	// Construct connection string
	connStr := fmt.Sprintf("host=%s port=%d user=%s password=%s dbname=%s sslmode=require", DBHost, DBPort, DBUser, DBPassword, DBName)

	// Open database connection
	db, err := sql.Open("postgres", connStr)
	if err != nil {
		t.Fatalf("Failed to connect to database: %v", err)
	}
	defer db.Close()

	fmt.Println("")
	fmt.Println("Testing EditTutorSession")
	fmt.Println("")

	type EditSession struct {
		TutoringSessionID int    `json:"tutor_session_id"`
		Date              string `json:"date"`
		TimeBlockIDList   []int  `json:"time_block_id_list"`
	}

	session := EditSession{
		TutoringSessionID: 58,
		Date:              "2024-04-08",
		TimeBlockIDList:   []int{1, 2},
	}

	sessionJSON, err := json.Marshal(session)
	assert.NoError(t, err, "Failed to marshal session JSON")

	reqEdit := httptest.NewRequest(http.MethodPost, "/edit_tutoring_session", bytes.NewBuffer(sessionJSON))
	reqEdit.Header.Set("Content-Type", "application/json")

	rrEdit := httptest.NewRecorder()

	handlers.EditTutorSession(db)(rrEdit, reqEdit)

	assert.Equal(t, http.StatusOK, rrEdit.Code, "Expected 200 OK Response")

	fmt.Println("")
	fmt.Println("Testing EditTutorSession Complete")
	fmt.Println("")
}

func TestGetTutoringSessionListHandler(t *testing.T) {
	// Construct connection string
	connStr := fmt.Sprintf("host=%s port=%d user=%s password=%s dbname=%s sslmode=require", DBHost, DBPort, DBUser, DBPassword, DBName)

	// Open database connection
	db, err := sql.Open("postgres", connStr)
	if err != nil {
		t.Fatalf("Failed to connect to database: %v", err)
	}
	defer db.Close()

	// Test case 1: Get Tutoring Session List with valid input
	fmt.Println("Test case 1: Get Tutoring Session List with valid input")

	// Define a mock user
	user := util.User{
		ID:   1,
		Role: "tutor",
	}
	// Marshal user to JSON
	reqBody, err := json.Marshal(user)
	assert.NoError(t, err, "Failed to marshal JSON request body")

	// Create a new HTTP request with the mock payload
	req := httptest.NewRequest(http.MethodPost, "/get_tutoring_session_list", bytes.NewBuffer(reqBody))
	req.Header.Set("Content-Type", "application/json")

	// Create a new recorder to record the response
	rr := httptest.NewRecorder()

	// Call the handler function with the recorder and request
	handler := handlers.GetTutoringSessionList(db)
	handler(rr, req)

	// Check the response status code
	assert.Equal(t, http.StatusOK, rr.Code, "Handler returned wrong status code for valid input")

	// Parse the JSON response
	var response struct {
		ID               int                    `json:"id"`
		Role             string                 `json:"role"`
		TutoringSessions []util.TutoringSession `json:"tutoring_session_list"`
	}
	err = json.NewDecoder(rr.Body).Decode(&response)
	assert.NoError(t, err, "Failed to decode JSON response")

	// Test case 2: Get Tutoring Session List with invalid JSON
	fmt.Println("")
	fmt.Println("Test case 2: Get Tutoring Session List with invalid JSON")

	// Create a new HTTP request with invalid JSON payload
	reqInvalid := httptest.NewRequest(http.MethodPost, "/get_tutoring_session_list", bytes.NewBuffer([]byte("{")))
	reqInvalid.Header.Set("Content-Type", "application/json")

	// Create a new recorder to record the response
	rrInvalid := httptest.NewRecorder()

	// Call the handler function with the recorder and request
	handler(rrInvalid, reqInvalid)

	// Check the response status code
	assert.Equal(t, http.StatusBadRequest, rrInvalid.Code, "Handler returned wrong status code for invalid JSON")
}

// TestLoginHandler tests the login handler functionality.
func TestLoginHandler(t *testing.T) {
	// Construct connection string
	connStr := fmt.Sprintf("host=%s port=%d user=%s password=%s dbname=%s sslmode=require", DBHost, DBPort, DBUser, DBPassword, DBName)

	// Open database connection
	db, err := sql.Open("postgres", connStr)
	if err != nil {
		t.Fatalf("Failed to connect to database: %v", err)
	}
	defer db.Close()

	fmt.Println("")
	fmt.Println("Testing LoginHandler")
	fmt.Println("")

	// Test case 1: Invalid email
	fmt.Println("Test 1: Invalid email")
	login := util.Login{
		Email:    "invalidemail@example.com",
		Password: "passwd",
		Role:     "tutor",
	}
	loginJSON, err := json.Marshal(login)
	assert.NoError(t, err, "Failed to marshal login JSON")

	req := httptest.NewRequest(http.MethodPost, "/login", bytes.NewBuffer(loginJSON))
	req.Header.Set("Content-Type", "application/json")

	rr := httptest.NewRecorder()
	handlers.LoginHandler(db)(rr, req)

	assert.Equal(t, http.StatusUnauthorized, rr.Code, "Handler returned wrong status code for invalid email")

	// Test case 2: Incorrect password
	fmt.Println("")
	fmt.Println("Test 2: Incorrect password")
	login = util.Login{
		Email:    "tutorjoe@COOLCODE.com",
		Password: "incorrectpassword",
		Role:     "tutor",
	}
	loginJSON, err = json.Marshal(login)
	assert.NoError(t, err, "Failed to marshal login JSON")

	req = httptest.NewRequest(http.MethodPost, "/login", bytes.NewBuffer(loginJSON))
	req.Header.Set("Content-Type", "application/json")

	rr = httptest.NewRecorder()
	handlers.LoginHandler(db)(rr, req)

	assert.Equal(t, http.StatusUnauthorized, rr.Code, "Handler returned wrong status code for incorrect password")

	// Test case 3: Missing role
	fmt.Println("")
	fmt.Println("Test 3: Missing role")
	login = util.Login{
		Email:    "tutorjoe@COOLCODE.com",
		Password: "passwd",
	}
	loginJSON, err = json.Marshal(login)
	assert.NoError(t, err, "Failed to marshal login JSON")

	req = httptest.NewRequest(http.MethodPost, "/login", bytes.NewBuffer(loginJSON))
	req.Header.Set("Content-Type", "application/json")

	rr = httptest.NewRecorder()
	handlers.LoginHandler(db)(rr, req)

	assert.Equal(t, http.StatusBadRequest, rr.Code, "Handler returned wrong status code for missing role")

	// Test case 4: Empty request body
	fmt.Println("")
	fmt.Println("Test 4: Empty request body")
	req = httptest.NewRequest(http.MethodPost, "/login", nil)

	rr = httptest.NewRecorder()
	handlers.LoginHandler(db)(rr, req)

	assert.Equal(t, http.StatusBadRequest, rr.Code, "Handler returned wrong status code for empty request body")

	// Test case 4.1: Error validation
	fmt.Println("")
	fmt.Println("Test 4.1: Invalid login")
	login = util.Login{
		Email:    "demotest@COOLCODE.com",
		Password: "passwd",
		Role:     "student",
	}
	loginJSON, err = json.Marshal(login)
	assert.NoError(t, err, "Failed to marsal login JSON")

	req = httptest.NewRequest(http.MethodPost, "/login", bytes.NewBuffer(loginJSON))
	req.Header.Set("Content-type", "application/json")

	rr = httptest.NewRecorder()
	handlers.LoginHandler(db)(rr, req)

	assert.Equal(t, http.StatusOK, rr.Code, "Handler returned wrong status code for valid login")

	// Test case 5: Valid login
	fmt.Println("")
	fmt.Println("Test 5: Valid login")
	login = util.Login{
		Email:    "tutorjoe@COOLCODE.com",
		Password: "passwd",
		Role:     "tutor",
	}
	loginJSON, err = json.Marshal(login)
	assert.NoError(t, err, "Failed to marshal login JSON")

	req = httptest.NewRequest(http.MethodPost, "/login", bytes.NewBuffer(loginJSON))
	req.Header.Set("Content-Type", "application/json")

	rr = httptest.NewRecorder()
	handlers.LoginHandler(db)(rr, req)

	assert.Equal(t, http.StatusOK, rr.Code, "Handler returned wrong status code for valid login")

	fmt.Println("")
	fmt.Println("LoginHandler test complete")
	fmt.Println("")
}
