package handlers

import (
	"bytes"
	//"database/sql"
	"encoding/json"
	//"fmt"
	"net/http"
	"net/http/httptest"
	"testing"

	"time"

	"github.com/DATA-DOG/go-sqlmock"
	"github.com/macewanCS/w24MacroHard/server/mock"
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

//
// WARNING: RUNNING THESE TESTS WILL GIVE YOU SO MUCH MEANINGFUL OR MEANINGLESS ERRORS (however you see it fit),
// I PUT MY TRUST IN THE COVERAGE REPORTS AND IF THEY ARE NOT ACCURATE AND THE CODE IS BROKEN THEN MY BAD G HAVE A NICE DAY!
//

// RUNNING TESTS:
// 1. use "go test -coverprofile="register_handler.out" ./..." | this will run the test and output an out file of results
// 2. use "go tool cover -html="register_handler.out" -o ./reports/register_handler.html"
// Step 2 will convert the out file to a detailed coverage report in html format the name of file is important
// because if you run the same command again then it will replace the old test reports (we want them as test logs)
// so naming scheme should follow: ie. "2024-03-04-handlers.out" and "2024-03-04-handlers.html"

// These are separation bars, not penises
//
// --------------------------------------------------------------------------------------------------------------------
//

// TestRegisterHandlerWithMock tests the register handler with a mock database
// func TestRegisterHandlerWithMock(t *testing.T) {
// 	// Initialize mock DB
// 	db, mock, err := NewMockDB()
// 	if err != nil {
// 		t.Fatalf("Failed to initialize mock database: %v", err)
// 	}
// 	defer db.Close()

// 	// Create a sample register payload
// 	register := util.Register{
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

// 	// Define mock expectations
// 	mock.ExpectExec("INSERT INTO student").WithArgs(
// 		sqlmock.AnyArg(),
// 		sqlmock.AnyArg(),
// 		sqlmock.AnyArg(),
// 		sqlmock.AnyArg(),
// 		sqlmock.AnyArg(),
// 		sqlmock.AnyArg(),
// 		sqlmock.AnyArg(),
// 	).WillReturnResult(sqlmock.NewResult(1, 1)) // Assuming one row affected

// 	// Create a new HTTP request with the register payload
// 	req := httptest.NewRequest(http.MethodPost, "/register", bytes.NewBuffer(registerJSON))
// 	req.Header.Set("Content-Type", "application/json")

// 	// Create a new recorder to record the response
// 	rr := httptest.NewRecorder()

// 	// Call the handler function with the recorder and mock DB
// 	handlers.RegisterHandler(db)(rr, req)

// 	// Check the response status code
// 	if rr.Code != http.StatusOK {
// 		t.Errorf("Handler returned wrong status code: got %v want %v", rr.Code, http.StatusOK)
// 	}

// 	// Print the response body (results)
// 	fmt.Println("Registration Results:")
// 	fmt.Println(rr.Body.String())
// }

// go test -coverprofile="register_handler.out" ./...
// go tool cover -html="register_handler.out" -o ./reports/register_handler.html
// func TestRegisterHandlerWithMockDB(t *testing.T) {
// 	// Create a new mock DB
// 	db, mock, err := mock.NewMockDB()
// 	if err != nil {
// 		t.Fatalf("Failed to create mock DB: %v", err)
// 	}

// 	// Set the expectations
// 	mock.ExpectExec("INSERT INTO student").
// 		WithArgs("test@example.com", sqlmock.AnyArg(), "07/12/1989", sqlmock.AnyArg(), 12, "Joe", "Feete").
// 		WillReturnResult(sqlmock.NewResult(1, 1))

// 	// Defer the cleaning of the mock
// 	defer func() {
// 		if err := mock.ExpectationsWereMet(); err != nil {
// 			t.Errorf("There were unfulfilled expectations: %s", err)
// 		}
// 	}()

// 	// Create a sample register payload
// 	register := util.Register{
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

// 	// Call the handler function with the recorder and mock DB
// 	RegisterHandler(db)(rr, req)

// 	// Check the response status code
// 	if rr.Code != http.StatusOK {
// 		t.Errorf("Handler returned wrong status code: got %v want %v", rr.Code, http.StatusOK)
// 	}

// 	// Print the response body (results)
// 	fmt.Println("Registration Results:")
// 	fmt.Println(rr.Body.String())
// }

//
// --------------------------------------------------------------------------------------------------------------------
//

// go test -coverprofile="login_handler.out" ./...
// go tool cover -html="login_handler.out" -o ./reports/login_handler.html
// func TestLoginHandlerWithMockDB(t *testing.T) {
// 	fmt.Println("")
// 	fmt.Println("Testing Login Handler")
// 	fmt.Println("")
// 	// Create a new mock DB
// 	db, mock, err := mock.NewMockDB()
// 	if err != nil {
// 		t.Fatalf("Failed to create mock DB: %v", err)
// 	}

// 	// Create a sample login request
// 	login := util.Login{
// 		Email:    "test@example.com",
// 		Password: "password123",
// 		Role:     "student",
// 	}
// 	loginJSON, err := json.Marshal(login)
// 	if err != nil {
// 		t.Fatalf("Failed to marshal login JSON: %v", err)
// 	}

// 	// Set the expectations
// 	mock.ExpectQuery("SELECT password FROM student WHERE email = ?").WithArgs(login.Email).WillReturnRows(sqlmock.NewRows([]string{"password"}).AddRow("$2a$10$EgGGduE/PADAhHbimFdad.CG017C8wpcB4tNPjWQntwHP2v376OwC"))
// 	mock.ExpectExec("SELECT").WithArgs(login.Email, login.Password).WillReturnResult(sqlmock.NewResult(1, 1))

// 	// Defer the cleaning of the mock
// 	defer func() {
// 		if err := mock.ExpectationsWereMet(); err != nil {
// 			t.Errorf("There were unfulfilled expectations: %s", err)
// 		}
// 	}()

// 	// Create a new request
// 	req, err := http.NewRequest("POST", "/login", bytes.NewBuffer(loginJSON))
// 	if err != nil {
// 		t.Fatalf("Failed to create request: %v", err)
// 	}
// 	req.Header.Set("Content-Type", "application/json")

// 	// Create a new recorder
// 	rr := httptest.NewRecorder()

// 	// Call the handler
// 	LoginHandler(db)(rr, req)

// 	// Check the response status code
// 	if rr.Code != http.StatusOK {
// 		t.Errorf("Handler returned wrong status code: got %v want %v", rr.Code, http.StatusOK)
// 	}

// 	// Check the response body
// 	expectedResponse := `{"User":{"Email":"test@example.com","Role":"student","ID":1},"Token":"sample_token"}`
// 	if rr.Body.String() != expectedResponse {
// 		t.Errorf("Handler returned unexpected body: got %v want %v", rr.Body.String(), expectedResponse)
// 	}
// }

//
// ------------------------------------------------------------------------------------------------------------------------------------------------------------------
//

// go test -coverprofile="addavail_handler.out" ./...
// go tool cover -html="addavail_handler.out" -o ./reports/addavail_handler.html
func TestAddTutorAvailability(t *testing.T) {
	// Create mock DB
	mockDB, mock, err := mock.NewMockDB()
	if err != nil {
		t.Fatalf("Failed to create mock DB: %v", err)
	}
	defer mockDB.Close()

	// Create a test handler
	handler := AddTutorAvailability(mockDB)

	// Mock the request body
	tutorAvailability := util.TutorAvailability{
		Date:            "2024-04-01",
		TimeBlockIdList: []int{1, 2, 3},
	}
	body, err := json.Marshal(tutorAvailability)
	if err != nil {
		t.Fatalf("Failed to marshal JSON: %v", err)
	}

	// Set up expectations
	mock.ExpectExec("DELETE FROM tutor_availability").
		WithArgs(sqlmock.AnyArg(), sqlmock.AnyArg()).
		WillReturnResult(sqlmock.NewResult(1, 1))
	mock.ExpectExec("INSERT INTO tutor_availability").
		WithArgs(sqlmock.AnyArg(), sqlmock.AnyArg(), sqlmock.AnyArg()).
		WillReturnResult(sqlmock.NewResult(1, 1))

	// Create a test request
	req, err := http.NewRequest(http.MethodPost, "/add-tutor-availability", bytes.NewBuffer(body))
	if err != nil {
		t.Fatalf("Failed to create request: %v", err)
	}

	// Create a test recorder
	rr := httptest.NewRecorder()

	// Call the handler
	handler.ServeHTTP(rr, req)

	// Log the response body
	t.Logf("Response Body: %s", rr.Body.String())

	// Check the response status code
	assert.Equal(t, http.StatusOK, rr.Code)

	// Decode the response
	var response struct {
		Date            time.Time `json:"date"`
		TimeBlockIdList []int     `json:"time_block_id_list"`
	}
	err = json.Unmarshal(rr.Body.Bytes(), &response)
	if err != nil {
		t.Fatalf("Failed to unmarshal JSON: %v", err)
	}

	// Verify the response
	expectedDate, err := time.Parse("2006-01-02", tutorAvailability.Date)
	if err != nil {
		t.Fatalf("Failed to parse date: %v", err)
	}
	assert.Equal(t, expectedDate.Format("2006-01-02"), response.Date.Format("2006-01-02"))
	assert.Equal(t, tutorAvailability.TimeBlockIdList, response.TimeBlockIdList)

	// Verify mock expectations
	if err := mock.ExpectationsWereMet(); err != nil {
		t.Errorf("Unfulfilled expectations: %s", err)
	}
}
