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

// RUNNING TESTS:
// 1. use "go test -coverprofile="NAME_OF_FILE.out" ./..." | this will run the test and output an out file of results
// 2. use "go tool cover -html="NAME_OF_FILE.out" -o ./reports/NAME_OF_FILE.html"
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
	fmt.Println("Test 5: Valid login")
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
func TestAddTutorAvailabilityHandler(t *testing.T) {
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

	// Call the GetTutorAvailability handler with the tutor ID and date
	tutorID := 1
	date := "2024-03-04"
	req := httptest.NewRequest(http.MethodGet, fmt.Sprintf("/get_tutor_availability?tutor_id=%d&date=%s", tutorID, date), nil)
	rr := httptest.NewRecorder()

	handler := handlers.GetTutorAvailability(db)
	handler(rr, req)

	// Check the response status code
	if rr.Code != http.StatusOK {
		t.Errorf("Handler returned wrong status code: got %v want %v", rr.Code, http.StatusOK)
	}

	// Parse the JSON response
	var response struct {
		TimeBlockIDList []int `json:"time_block_id_list"`
	}
	err = json.NewDecoder(rr.Body).Decode(&response)
	if err != nil {
		t.Fatalf("Failed to decode JSON response: %v", err)
	}

	// Check if the response contains time block IDs
	if len(response.TimeBlockIDList) == 0 {
		t.Errorf("Handler returned empty time block IDs")
	}

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
