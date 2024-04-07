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
	_ "github.com/mattn/go-sqlite3" // SQLite driver
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

// func matchQuery(query, sql string, args []driver.Value) bool {
// 	// Check if the SQL query matches the expected query
// 	if query != expectedQuery {
// 		return false
// 	}

// 	// Check if the arguments match the expected arguments
// 	expectedArgs := []interface{}{1, "2024-04-06", 1}
// 	if !reflect.DeepEqual(args, expectedArgs) {
// 		return false
// 	}

// 	return true
// }

// func TestEditTutorSession(t *testing.T) {
// 	// Create mock database and mock
// 	db, mock, err := mock.NewMockDB()
// 	if err != nil {
// 		t.Fatalf("Error creating mock database: %v", err)
// 	}
// 	defer db.Close()

// 	// Create mock DB client
// 	dbClient, err := NewDBClient(DBHost, DBPort, DBUser, DBPassword, DBName)
// 	if err != nil {
// 		t.Fatalf("Error creating DB client: %v", err)
// 	}
// 	defer dbClient.db.Close()

// 	// Mock expectations for GetTutorFromSession
// 	mock.ExpectQuery("^SELECT tutor_id FROM tutoring_session WHERE tutor_session_id = ?").
// 		WithArgs(1).
// 		WillReturnRows(sqlmock.NewRows([]string{"tutor_id"}).AddRow(1))
// 	// Mock expectations for GetTimeBlockIdListFromSession
// 	mock.ExpectQuery("^SELECT time_block_id_list FROM tutoring_session WHERE tutor_session_id = ?").
// 		WithArgs(1).
// 		WillReturnRows(sqlmock.NewRows([]string{"time_block_id_list"}).AddRow(pq.Array([]int{1, 2})))
// 	// Parse date string to time.Time
// 	dateStr := "2024-04-05"
// 	parsedDate, err := time.Parse("2006-01-02", dateStr)
// 	if err != nil {
// 		t.Fatalf("Error parsing date string: %v", err)
// 	}
// 	// Mock expectations for GetDateFromSession
// 	mock.ExpectQuery("^SELECT date FROM tutoring_session WHERE tutor_session_id = ?").
// 		WithArgs(1).
// 		WillReturnRows(sqlmock.NewRows([]string{"date"}).AddRow(parsedDate))

// 	// // Mock expectations for PeekTimeSlot
// 	// mock.ExpectQuery("^SELECT EXISTS\\(SELECT 1 FROM tutor_availability WHERE tutor_id = \\? AND date = \\? AND time_block_id = \\?\\)").
// 	// 	WithArgs(1, "2024-04-06", 1).
// 	// 	WillReturnRows(sqlmock.NewRows([]string{"exists"}).AddRow(true))

// 	mock.ExpectQuery(`SELECT EXISTS\(SELECT 1 FROM tutor_availability WHERE tutor_id = \$1 AND date = \$2 AND time_block_id = \$3\)`).
// 		WithArgs(1, sqlmock.AnyArg(), 1).
// 		WillReturnRows(sqlmock.NewRows([]string{"exists"}).AddRow(true))

// 	// Mock expectations for UpdateTutorSessionDateAndTimeBlockList
// 	mock.ExpectExec("^UPDATE tutoring_session SET date = \\?, time_block_id_list = \\? WHERE tutor_session_id = \\?").
// 		WithArgs("2024-04-06", pq.Array([]int{1, 2}), 1).
// 		WillReturnResult(sqlmock.NewResult(0, 1))

// 	// Mock expectations for DeleteSomeTutorAvailability
// 	mock.ExpectExec("^DELETE FROM tutor_availability WHERE tutor_id = \\? AND time_block_id = \\?").
// 		WithArgs(1, 1).
// 		WillReturnResult(sqlmock.NewResult(0, 1))

// 	// Mock expectations for InsertTutorAvailability
// 	mock.ExpectExec("^INSERT INTO tutor_availability").
// 		WithArgs(1, "2024-04-05", 1).
// 		WillReturnResult(sqlmock.NewResult(0, 1))

// 	// Create a new HTTP request
// 	reqBody := []byte(`{
// 		"tutor_session_id": 1,
// 		"date": "2024-04-06",
// 		"time_block_id_list": [1, 2]
// 	}`)
// 	req, err := http.NewRequest("POST", "/edit_tutoring_session", bytes.NewBuffer(reqBody))
// 	if err != nil {
// 		t.Fatalf("Error creating request: %v", err)
// 	}

// 	// Create a new HTTP recorder
// 	w := httptest.NewRecorder()

// 	// Call the handler function with the mock DB client
// 	handler := handlers.EditTutorSession(db)
// 	handler.ServeHTTP(w, req)

// 	fmt.Println("")
// 	// Check the response status code
// 	if w.Code != http.StatusOK {
// 		t.Errorf("Expected status code %d, but got %d", http.StatusOK, w.Code)
// 	}
// 	fmt.Println("")

// 	// Decode the response body
// 	var response struct {
// 		TutorSessionID     int
// 		NewDate            string
// 		NewTimeBlockIDList []int
// 	}
// 	err = json.Unmarshal(w.Body.Bytes(), &response)
// 	if err != nil {
// 		t.Fatalf("Error decoding JSON response: %v\nResponse Body: %s", err, w.Body.String())
// 	}

// 	// Validate the response
// 	if response.TutorSessionID != 1 {
// 		t.Errorf("Expected tutor_session_id to be %d, but got %d", 1, response.TutorSessionID)
// 	}
// 	if response.NewDate != "2024-04-06" {
// 		t.Errorf("Expected new_date to be %s, but got %s", "2024-04-06", response.NewDate)
// 	}
// 	if !reflect.DeepEqual(response.NewTimeBlockIDList, []int{1, 2}) {
// 		t.Errorf("Expected new_time_block_id_list to be %v, but got %v", []int{1, 2}, response.NewTimeBlockIDList)
// 	}

// 	// Check if all mock expectations were met
// 	if err := mock.ExpectationsWereMet(); err != nil {
// 		t.Errorf("Unfulfilled expectations: %s", err)
// 	}
// }

//

// The functions used:
// DecodeJSONRequestBody
// GetTutorFromSession
// GetTimeBlockIdListFromSession
// GetDateFromSession
// PeekTimeSlot
// UpdateTutorSessionDateAndTimeBlockList
// DeleteSomeTutorAvailability
// TimeBlockToString
// GetTutorEmailByID
// GetStudentEmailBySessionID
// SendEmail
func TestEditTutorSession(t *testing.T) {
	// Create a new SQLite in-memory database
	db, err := sql.Open("sqlite3", ":memory:")
	if err != nil {
		t.Fatalf("Failed to connect to SQLite database: %v", err)
	}
	defer db.Close()

	// Copy the tables from the database schema
	_, err = db.Exec(`
		CREATE TABLE IF NOT EXISTS tutor (
			tutor_id INTEGER PRIMARY KEY AUTOINCREMENT,
			name VARCHAR(50) NOT NULL,
			email VARCHAR(50) UNIQUE NOT NULL,
			password VARCHAR(60) NOT NULL,
			expertise TEXT NOT NULL,
			experience TEXT NOT NULL,
			verified_status BOOLEAN NOT NULL DEFAULT FALSE,
			description TEXT,
			degree TEXT,
			date_of_birth DATE
		);
		CREATE TABLE IF NOT EXISTS student (
			student_id INTEGER PRIMARY KEY AUTOINCREMENT,
			name VARCHAR(50) NOT NULL,
			date_of_birth DATE,
			grade_level VARCHAR(3) NOT NULL,
			school VARCHAR(50),
			email VARCHAR(50) UNIQUE NOT NULL,
			password VARCHAR(60) NOT NULL
		);
		CREATE TABLE IF NOT EXISTS tutoring_session (
			tutor_session_id INTEGER PRIMARY KEY AUTOINCREMENT,
			tutor_id INTEGER NOT NULL,
			student_id INTEGER NOT NULL,
			name VARCHAR(50) NOT NULL,
			description TEXT,
			subject TEXT NOT NULL,
			grade VARCHAR(3) NOT NULL,
			date DATE NOT NULL,
			time_block_id_list TEXT NOT NULL,
			status TEXT NOT NULL DEFAULT 'scheduled',
			FOREIGN KEY(tutor_id) REFERENCES tutor(tutor_id),
			FOREIGN KEY(student_id) REFERENCES student(student_id)
		);
		CREATE TABLE IF NOT EXISTS tutor_availability (
			availability_id INTEGER PRIMARY KEY AUTOINCREMENT,
			tutor_id INTEGER,
			date DATE NOT NULL,
			time_block_id INTEGER NOT NULL,
			UNIQUE(tutor_id, date, time_block_id)
		);
		CREATE TABLE IF NOT EXISTS time_block (
			time_block_id INTEGER PRIMARY KEY AUTOINCREMENT,
			start_time TIME NOT NULL,
			end_time TIME NOT NULL
		);
	`)
	if err != nil {
		t.Fatalf("Failed to create tables: %v", err)
	}

	// Insert mock tutor data
	_, err = db.Exec(`INSERT INTO tutor (name, email, password, expertise, experience) VALUES (?, ?, ?, ?, ?)`,
		"John Doe", "john@example.com", "password123", "mathematics,physics", "junior")
	if err != nil {
		t.Fatalf("Failed to insert mock tutor data: %v", err)
	}

	// Insert mock student data
	_, err = db.Exec(`INSERT INTO student (name, date_of_birth, grade_level, school, email, password) VALUES (?, ?, ?, ?, ?, ?)`,
		"Alice", "2005-01-01", "10", "High School", "alice@example.com", "password456")
	if err != nil {
		t.Fatalf("Failed to insert mock student data: %v", err)
	}

	// Insert mock tutoring_session data
	_, err = db.Exec(`INSERT INTO tutoring_session (tutor_id, student_id, name, description, subject, grade, date, time_block_id_list) VALUES (?, ?, ?, ?, ?, ?, ?, ?)`,
		1, 1, "Math Tutorial", "Basic Math Concepts", "mathematics", "10", "2024-04-05", "1,2")
	if err != nil {
		t.Fatalf("Failed to insert mock tutoring_session data: %v", err)
	}

	// Insert mock tutor availability data for time block 1
	_, err = db.Exec(`INSERT INTO tutor_availability (tutor_id, date, time_block_id) VALUES (?, ?, ?)`,
		1, "2024-04-06", 1)
	if err != nil {
		t.Fatalf("Failed to insert mock tutor availability data for time block 1: %v", err)
	}

	// Insert mock tutor availability data for time block 2
	_, err = db.Exec(`INSERT INTO tutor_availability (tutor_id, date, time_block_id) VALUES (?, ?, ?)`,
		1, "2024-04-06", 2)
	if err != nil {
		t.Fatalf("Failed to insert mock tutor availability data for time block 2: %v", err)
	}

	// Create a new HTTP request
	reqBody := []byte(`{
        "tutor_session_id": 1,
        "date": "2024-04-06",
        "time_block_id_list": [1, 2]
    }`)
	req, err := http.NewRequest("POST", "/edit_tutoring_session", bytes.NewBuffer(reqBody))
	if err != nil {
		t.Fatalf("Error creating request: %v", err)
	}

	// Create a new HTTP recorder
	w := httptest.NewRecorder()

	// Mock the handler function with the SQLite DB
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
		t.Fatalf("Error decoding JSON response: %v\nResponse Body: %s", err, w.Body.String())
	}

	// Validate the response
	if response.TutorSessionID != 1 {
		t.Errorf("Expected tutor_session_id to be %d, but got %d", 1, response.TutorSessionID)
	}
	if response.NewDate != "2024-04-06" {
		t.Errorf("Expected new_date to be %s, but got %s", "2024-04-06", response.NewDate)
	}
	if !IntSlicesEqual(response.NewTimeBlockIDList, []int{1, 2}) {
		t.Errorf("Expected new_time_block_id_list to be %v, but got %v", []int{1, 2}, response.NewTimeBlockIDList)
	}
}

// IntSlicesEqual checks if two slices of integers are equal
func IntSlicesEqual(slice1, slice2 []int) bool {
	if len(slice1) != len(slice2) {
		return false
	}
	for i := range slice1 {
		if slice1[i] != slice2[i] {
			return false
		}
	}
	return true
}
