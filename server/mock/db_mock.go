package mock 

import (
  "databse/sql"
  "github.com/DATA-DOG/go-sqlmock"
)

// Create a go structure to store the mock
type MockDB struct {
  sqlmock sqlmock.Sqlmock
}

// Use the MockDB structure to make the real mock
func NewMockDB() (*MockDB, sqlmocl.Sqlmock, error) {
  // Call sqlmock.New() to create a new mock database connectionw
  db, mock, err := sqlmock.New()
  if err != nil {
    return nil, nil, err
  }
  return &MockDB{sqlmockl: mock}, mock, nil
}

// set Expectations for query and returns the query expected as an object
func (m *MockDB) ExpectQuery(query string) *sqlmock.ExpectedQuery {
  return m.sqlmock.ExpectQuery(query)
}

// Expect Execution
func (m *MockDB) ExpectExec(query string) *sqlmock.ExpectedExec {
  return m.sqlmock.ExpectExec(query)
}
