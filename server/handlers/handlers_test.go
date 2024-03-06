package handlers_test

import (
	"bytes"
	"database/sql"
	"encoding/json"
	"fmt"
	"net/http"
	"net/http/httptest"
	"testing"

	"github.com/macewanCS/w24MacroHard/server/handlers"

	"github.com/macewanCS/w24MacroHard/server/util"
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

func TestLoginHandler(t *testing.T) {
	// Construct connection string
	connStr := fmt.Sprintf("host=%s port=%d user=%s password=%s dbname=%s sslmode=require", DBHost, DBPort, DBUser, DBPassword, DBName)

	// Open database connection
	db, err := sql.Open("postgres", connStr)
	if err != nil {
		t.Fatalf("Failed to connect to database: %v", err)
	}
	defer db.Close()

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

	// Test case 4: Empty request body
	req = httptest.NewRequest(http.MethodPost, "/login", nil)

	rr = httptest.NewRecorder()
	handlers.LoginHandler(db)(rr, req)

	if rr.Code != http.StatusBadRequest {
		t.Errorf("Handler returned wrong status code for empty request body: got %v want %v", rr.Code, http.StatusBadRequest)
	}

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
}

// func TestRegisterHandler(t *testing.T) {
// 	// Construct connection string
// 	connStr := fmt.Sprintf("host=%s port=%d user=%s password=%s dbname=%s sslmode=require", DBHost, DBPort, DBUser, DBPassword, DBName)

// 	// Open database connection
// 	db, err := sql.Open("postgres", connStr)
// 	if err != nil {
// 		t.Fatalf("Failed to connect to database: %v", err)
// 	}
// 	defer db.Close()

// 	// Create a sample register payload
// 	register := middleware.Register{
// 		Email:       "test@example.com",
// 		Password:    "password123",
// 		Role:        "student",
// 		FirstName:   "Joe",
// 		LastName:    "Feete",
// 		DateOfBirth: "07/12/1989",
// 		Expertise:   []string{"mathematics", "computer science", "literature"},
// 		Experience:  "senior",
// 		Description: "I am the greatest tutor to ever exist.",
// 		Degrees:     []string{"high school diploma", "proserve(expired)", "plat 4 in league of legends"},
// 		Grade:       12,
// 	}
// 	registerJSON, err := json.Marshal(register)
// 	if err != nil {
// 		t.Fatalf("Failed to marshal register JSON: %v", err)
// 	}

// 	// Create a new HTTP request with the register payload
// 	req := httptest.NewRequest(http.MethodPost, "/register", bytes.NewBuffer(registerJSON))
// 	req.Header.Set("Content-Type", "application/json")

// 	// Create a new recorder to record the response
// 	rr := httptest.NewRecorder()

// 	// Call the handler function with the recorder, request, and real DB
// 	handlers.RegisterHandler(db)(rr, req)

// 	// Check the response status code
// 	if rr.Code != http.StatusOK {
// 		t.Errorf("Handler returned wrong status code: got %v want %v", rr.Code, http.StatusOK)
// 	}

// 	// Print the response body (results)
// 	fmt.Println("Registration Results:")
// 	fmt.Println(rr.Body.String())
// }

// // TestAddTutorAvailability_ValidRequest tests adding tutor availability with a valid request.
// func TestAddTutorAvailability_ValidRequest(t *testing.T) {

// 		// Construct connection string
// 		connStr := fmt.Sprintf("host=%s port=%d user=%s password=%s dbname=%s sslmode=require", DBHost, DBPort, DBUser, DBPassword, DBName)

// 	// Open database connection
// 	db, err := sql.Open("postgres", connStr)
// 	if err != nil {
// 		t.Fatalf("Failed to connect to database: %v", err)
// 	}
// 	defer db.Close()

// 	// Define the tutor availability for testing
// 	tutor := util.TutorAvailability{
// 		ID:   1,
// 		Date: "2024-03-04",
// 	}

// 	// Call the function being tested
// 	exists, err := util.PeekAvailabilityDate(db, tutor)
// 	if err != nil {
// 		t.Fatalf("unexpected error: %v", err)
// 	}

// 	// Check if the result is as expected
// 	if !exists {
// 		t.Errorf("expected availability to exist, but it does not")
// 	}
// }
