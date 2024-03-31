package mock

import (
	"database/sql"
	"fmt"
	"testing"

	"github.com/DATA-DOG/go-sqlmock"
	"github.com/stretchr/testify/assert"
)

func TestGetData(t *testing.T) {
	// Initialize mock DB
	mockDB, mock, err := NewMockDB()
	if err != nil {
		t.Fatalf("Failed to create mock: %v", err)
	}
	defer func() {
		if err := mock.ExpectationsWereMet(); err != nil {
			t.Errorf("Unfulfilled expectations: %s", err)
		}
	}()

	// Set up mock expectations
	rows := mock.NewRows([]string{"name"}).
		AddRow("John").
		AddRow("Doe")

	mock.ExpectQuery("SELECT name FROM users").WillReturnRows(rows)

	// Call GetData function
	data, err := GetData(mockDB, mock)
	if err != nil {
		t.Fatalf("Error getting data: %v", err)
	}

	// Print the returned data
	fmt.Println("Retrieved data:", data)

	// Check the returned data
	expectedData := []string{"John", "Doe"}
	assert.Equal(t, expectedData, data)
}

func GetData(db *sql.DB, mock sqlmock.Sqlmock) ([]string, error) {
	var data []string

	rowsResult, err := db.Query("SELECT name FROM users")
	if err != nil {
		return nil, err
	}
	defer rowsResult.Close()

	for rowsResult.Next() {
		var name string
		if err := rowsResult.Scan(&name); err != nil {
			return nil, err
		}
		data = append(data, name)
	}

	return data, nil
}
