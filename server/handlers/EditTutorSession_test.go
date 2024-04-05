package handlers_test

import (
	"bytes"
	"database/sql"
	"encoding/json"
	"fmt"
	"net/http"
	"net/http/httptest"
	"reflect"
	"testing"

	"github.com/DATA-DOG/go-sqlmock"
	"github.com/lib/pq"
	"github.com/macewanCS/w24MacroHard/server/handlers"
	"github.com/macewanCS/w24MacroHard/server/mock"
)

// RUNNING TESTS:
// 1. use "go test -run TestEditTutorSession -coverprofile="EditTutorSession.out" | this will run the test and output an out file of results
// 2. use "go tool cover -html="EditTutorSession.out" -o ./reports/EditTutorSession.html"
// Step 2 will convert the out file to a detailed coverage report in html format the name of file is important
// because if you run the same command again then it will replace the old test reports (we want them as test logs)
// so naming scheme should follow: ie. "2024-03-04-handlers.out" and "2024-03-04-handlers.html"

const (
	DBHost     = "macrohard-onlytutor.cj0646k6g181.us-east-2.rds.amazonaws.com"
	DBPort     = 5432
	DBUser     = "MacroHard"
	DBPassword = "chopperiscute"
	DBName     = "postgres"
	Key        = "codingiscool"
)

type DBClient struct {
	db *sql.DB
}

// NewDBClient creates a new instance of DBClient with a connection to the real database
func NewDBClient(host string, port int, user, password, dbname string) (*DBClient, error) {
	// Construct connection string
	connStr := fmt.Sprintf("host=%s port=%d user=%s password=%s dbname=%s sslmode=require", host, port, user, password, dbname)

	// Open database connection
	db, err := sql.Open("postgres", connStr)
	if err != nil {
		return nil, fmt.Errorf("failed to connect to database: %v", err)
	}

	return &DBClient{db: db}, nil
}
func TestEditTutorSession(t *testing.T) {
	// Create mock database and mock
	db, mock, err := mock.NewMockDB()
	if err != nil {
		t.Fatalf("Error creating mock database: %v", err)
	}
	defer db.Close()

	// Create mock DB client
	dbClient, err := NewDBClient(DBHost, DBPort, DBUser, DBPassword, DBName)
	if err != nil {
		t.Fatalf("Error creating DB client: %v", err)
	}
	defer dbClient.db.Close()

	// Mock expectations for GetTutorFromSession
	mock.ExpectQuery("^SELECT tutor_id FROM tutoring_session WHERE tutor_session_id = ?").
		WithArgs(1).
		WillReturnRows(sqlmock.NewRows([]string{"tutor_id"}).AddRow(1))

	// Mock expectations for GetTimeBlockIdListFromSession
	mock.ExpectQuery("^SELECT time_block_id FROM tutoring_session_time_blocks WHERE tutor_session_id = ?").
		WithArgs(1).
		WillReturnRows(sqlmock.NewRows([]string{"time_block_id"}).AddRow(1).AddRow(2).AddRow(3))

	// Mock expectations for GetDateFromSession
	mock.ExpectQuery("^SELECT date FROM tutoring_session WHERE tutor_session_id = ?").
		WithArgs(1).
		WillReturnRows(sqlmock.NewRows([]string{"date"}).AddRow("2024-04-05"))

	// Mock expectations for PeekTimeSlot
	mock.ExpectQuery("^SELECT EXISTS\\(SELECT 1 FROM tutor_availability WHERE tutor_id = ? AND time_block_id = ?\\)").
		WithArgs(1, 1).
		WillReturnRows(sqlmock.NewRows([]string{"exists"}).AddRow(true))

	// Mock expectations for UpdateTutorSessionDateAndTimeBlockList
	mock.ExpectExec("^UPDATE tutoring_session SET date = \\?, time_block_id_list = \\? WHERE tutor_session_id = \\?").
		WithArgs("2024-04-06", pq.Array([]int{1, 2, 3}), 1).
		WillReturnResult(sqlmock.NewResult(0, 1))

	// Mock expectations for DeleteSomeTutorAvailability
	mock.ExpectExec("^DELETE FROM tutor_availability WHERE tutor_id = \\? AND time_block_id = \\?").
		WithArgs(1, 1).
		WillReturnResult(sqlmock.NewResult(0, 1))

	// Mock expectations for InsertTutorAvailability
	mock.ExpectExec("^INSERT INTO tutor_availability").
		WithArgs(1, "2024-04-05", 1).
		WillReturnResult(sqlmock.NewResult(0, 1))

	// Create a new HTTP request
	reqBody := []byte(`{
		"tutor_session_id": 1,
		"date": "2024-04-06",
		"time_block_id_list": [1, 2, 3]
	}`)
	req, err := http.NewRequest("POST", "/edit_tutoring_session", bytes.NewBuffer(reqBody))
	if err != nil {
		t.Fatalf("Error creating request: %v", err)
	}

	// Create a new HTTP recorder
	w := httptest.NewRecorder()

	// Call the handler function with the mock DB client
	handler := handlers.EditTutorSession(db)
	handler.ServeHTTP(w, req)

	// Check the response status code
	if w.Code != http.StatusOK {
		t.Errorf("Expected status code %d, but got %d", http.StatusOK, w.Code)
	}

	// Decode the response body
	var response struct {
		TutorSessionID     int
		NewDate            string
		NewTimeBlockIDList []int
	}
	err = json.Unmarshal(w.Body.Bytes(), &response)
	if err != nil {
		t.Fatalf("Error decoding JSON response: %v", err)
	}

	// Validate the response
	if response.TutorSessionID != 1 {
		t.Errorf("Expected tutor_session_id to be %d, but got %d", 1, response.TutorSessionID)
	}
	if response.NewDate != "2024-04-06" {
		t.Errorf("Expected new_date to be %s, but got %s", "2024-04-06", response.NewDate)
	}
	if !reflect.DeepEqual(response.NewTimeBlockIDList, []int{1, 2, 3}) {
		t.Errorf("Expected new_time_block_id_list to be %v, but got %v", []int{1, 2, 3}, response.NewTimeBlockIDList)
	}

	// Check if all mock expectations were met
	if err := mock.ExpectationsWereMet(); err != nil {
		t.Errorf("Unfulfilled expectations: %s", err)
	}
}
