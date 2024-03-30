package mock

import (
	"database/sql"
	"testing"

	"github.com/DATA-DOG/go-sqlmock"
	"github.com/stretchr/testify/assert"
)

func GetData(db *sql.DB, mock sqlmock.Sqlmock) ([]string, error) {
	var data []string

	// Create a new mock row
	rows := sqlmock.NewRows([]string{"name"}).
		AddRow("John").
		AddRow("Doe")

	// Query the mock database
	mock.ExpectQuery("SELECT name FROM users").WillReturnRows(rows)

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

func TestGetData(t *testing.T) {
	// Initialize mock DB
	db, mock, err := mock.NewMockDB()
	if err != nil {
		t.Fatalf("Failed to create mock: %v", err)
	}
	defer mock.ExpectationsWereMet()

	data, err := GetData(db, mock)
	if err != nil {
		t.Fatalf("Error getting data: %v", err)
	}

	assert.Equal(t, []string{"John", "Doe"}, data)
}
