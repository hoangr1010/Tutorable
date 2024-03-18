package util_test

import (
	//"bytes"
	"database/sql"
	//"encoding/json"
	"fmt"
	"testing"

	//"time"

	//"github.com/golang-jwt/jwt/v5"
	//"github.com/lib/pq"
	"github.com/macewanCS/w24MacroHard/server/util"
	//"github.com/stretchr/testify/assert"
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

func TestPeekAvail(t *testing.T) {
	// Connect to database
	connStr := fmt.Sprintf("host=%s port=%d user=%s password=%s dbname=%s sslmode=disable", DBHost, DBPort, DBUser, DBPassword, DBName)

	// Open the database connection
	db, err := sql.Open("postgres", connStr)
	if err != nil {
		t.Fatalf("Failed to connect to database: %v", err)
	}
	defer db.Close()

	fmt.Println("")
	fmt.Println("Testing Peek Availability Date")
	fmt.Println("")

	// Test case 1:
	tutor := util.TutorAvailability{
		ID:   1,
		Date: "2024-03-17",
	}
	exists, err := util.PeekAvailabilityDate(db, tutor)
	if err != nil {
		t.Errorf("Error in test case 1: %v", err)
	}
	if !exists {
		t.Errorf("Test case 1 failed: expected tutor to be available, but it is not")
	} else {
		fmt.Println("Test case 1 passed: Tutor is available on the specified date")
	}
}

func TestSendEmail(t *testing.T) {
	recipient := []string{"j.foote777@gmail.com"}
	subject := "Test Subject"
	body := "This is a test email body."

	err := util.SendEmail(recipient, subject, body)
	if err != nil {
		t.Errorf("Error sending email: %v", err)
	}
}
