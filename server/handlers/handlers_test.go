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

// Define constants for database connection
const (
	DBHost     = "macrohard-onlytutor.cj0646k6g181.us-east-2.rds.amazonaws.com"
	DBPort     = 5432
	DBUser     = "MacroHard"
	DBPassword = "chopperiscute"
	DBName     = "postgres"
	Key        = "codingiscool"
)

// Test: Checks if the login handler works
func TestLoginHandler(t *testing.T) {
	// Construct connection string
	connStr := fmt.Sprintf("host=%s port=%d user=%s password=%s dbname=%s sslmode=require", DBHost, DBPort, DBUser, DBPassword, DBName)

	// Open database connection
	db, err := sql.Open("postgres", connStr)
	if err != nil {
		t.Fatalf("Failed to connect to database: %v", err)
	}
	defer db.Close()

  fmt.Println("Testing LoginHandler")
  fmt.Println("")
  fmt.Println("Test 1: Invalid email")
	// Test case 1: Invalid email
	login := util.Login{
		Email:    "invalidemail@example.com",
		Password: "passwd",
		Role:     "tutor",
	}
	loginJSON, err := json.Marshal(login)
	if err != nil {
		t.Fatalf("Failed to marshal login JSON: %v", err)
	}

	req := httptest.NewRequest(http.MethodPost, "/login", bytes.NewBuffer(loginJSON))
	req.Header.Set("Content-Type", "application/json")

	rr := httptest.NewRecorder()
	handlers.LoginHandler(db)(rr, req)

	if rr.Code != http.StatusUnauthorized {
		t.Errorf("Handler returned wrong status code for invalid email: got %v want %v", rr.Code, http.StatusUnauthorized)
	}
  
  fmt.Println("")
  fmt.Println("Test 2: Incorrect password")
	// Test case 2: Incorrect password
	login = util.Login{
		Email:    "tutorjoe@COOLCODE.com",
		Password: "incorrectpassword",
		Role:     "tutor",
	}
	loginJSON, err = json.Marshal(login)
	if err != nil {
		t.Fatalf("Failed to marshal login JSON: %v", err)
	}

	req = httptest.NewRequest(http.MethodPost, "/login", bytes.NewBuffer(loginJSON))
	req.Header.Set("Content-Type", "application/json")

	rr = httptest.NewRecorder()
	handlers.LoginHandler(db)(rr, req)

	if rr.Code != http.StatusUnauthorized {
		t.Errorf("Handler returned wrong status code for incorrect password: got %v want %v", rr.Code, http.StatusUnauthorized)
	}

  fmt.Println("")
  fmt.Println("Test 3: Missing role")
	// Test case 3: Missing role
	login = util.Login{
		Email:    "tutorjoe@COOLCODE.com",
		Password: "passwd",
	}
	loginJSON, err = json.Marshal(login)
	if err != nil {
		t.Fatalf("Failed to marshal login JSON: %v", err)
	}

	req = httptest.NewRequest(http.MethodPost, "/login", bytes.NewBuffer(loginJSON))
	req.Header.Set("Content-Type", "application/json")
rr = httptest.NewRecorder()
	handlers.LoginHandler(db)(rr, req)

	if rr.Code != http.StatusBadRequest {
		t.Errorf("Handler returned wrong status code for missing role: got %v want %v", rr.Code, http.StatusBadRequest)
	}

  fmt.Println("")
  fmt.Println("Test 4: Empty request body")
  // Test case 4: Empty request body
	req = httptest.NewRequest(http.MethodPost, "/login", nil)

	rr = httptest.NewRecorder()
	handlers.LoginHandler(db)(rr, req)

	if rr.Code != http.StatusBadRequest {
		t.Errorf("Handler returned wrong status code for empty request body: got %v want %v", rr.Code, http.StatusBadRequest)
	}

  fmt.Println("")
  fmt.Println("Test 5: Valid login")
	// Test case 5: Valid login
	login = util.Login{
		Email:    "tutorjoe@COOLCODE.com",
		Password: "passwd",
		Role:     "tutor",
	}
	loginJSON, err = json.Marshal(login)
	if err != nil {
		t.Fatalf("Failed to marshal login JSON: %v", err)
	}

	req = httptest.NewRequest(http.MethodPost, "/login", bytes.NewBuffer(loginJSON))
	req.Header.Set("Content-Type", "application/json")

	rr = httptest.NewRecorder()
	handlers.LoginHandler(db)(rr, req)

	if rr.Code != http.StatusOK {
		t.Errorf("Handler returned wrong status code for valid login: got %v want %v", rr.Code, http.StatusOK)
	}
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
		ID:             1,
		Date:           "2024-03-04",
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


