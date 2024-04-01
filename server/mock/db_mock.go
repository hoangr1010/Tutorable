package mock

import (
	"database/sql"
	"fmt"

	"github.com/DATA-DOG/go-sqlmock"
	"github.com/lib/pq"
)

// Create a go structure to store the mock
type MockDB struct {
	sqlmock sqlmock.Sqlmock
}

// Use the MockDB structure to make the real mock
func NewMockDB() (*sql.DB, sqlmock.Sqlmock, error) {
	db, mock, err := sqlmock.New()
	if err != nil {
		return nil, nil, err
	}
	return db, mock, nil
}

// set Expectations for query and returns the query expected as an object
func (m *MockDB) ExpectQuery(query string) *sqlmock.ExpectedQuery {
	return m.sqlmock.ExpectQuery(query)
}

// Expect Execution
func (m *MockDB) ExpectExec(query string) *sqlmock.ExpectedExec {
	return m.sqlmock.ExpectExec(query)
}

// Expectations for New Tables
// Expectation for the 'tutor' table
func (m *MockDB) ExpectTutorInsert() *sqlmock.ExpectedExec {
	return m.sqlmock.ExpectExec("INSERT INTO tutor").
		WithArgs(sqlmock.AnyArg(), sqlmock.AnyArg(), sqlmock.AnyArg(), sqlmock.AnyArg(), sqlmock.AnyArg(), sqlmock.AnyArg(), sqlmock.AnyArg(), sqlmock.AnyArg(), sqlmock.AnyArg())
}

// Expectation for the 'student' table
func (m *MockDB) ExpectStudentInsert() *sqlmock.ExpectedExec {
	return m.sqlmock.ExpectExec("INSERT INTO student").
		WithArgs(sqlmock.AnyArg(), sqlmock.AnyArg(), sqlmock.AnyArg(), sqlmock.AnyArg(), sqlmock.AnyArg(), sqlmock.AnyArg(), sqlmock.AnyArg())
}

// Expectation for the 'tutoring_session' table
func (m *MockDB) ExpectTutoringSessionInsert() *sqlmock.ExpectedExec {
	return m.sqlmock.ExpectExec("INSERT INTO tutoring_session").
		WithArgs(sqlmock.AnyArg(), sqlmock.AnyArg(), sqlmock.AnyArg(), sqlmock.AnyArg(), sqlmock.AnyArg(), sqlmock.AnyArg(), sqlmock.AnyArg(), sqlmock.AnyArg())
}

// Expectation for the 'tutor_availability' table
func (m *MockDB) ExpectTutorAvailabilityInsert() *sqlmock.ExpectedExec {
	return m.sqlmock.ExpectExec("INSERT INTO tutor_availability").
		WithArgs(sqlmock.AnyArg(), sqlmock.AnyArg(), sqlmock.AnyArg())
}

// Expectation for the 'time_block' table
func (m *MockDB) ExpectTimeBlockInsert() *sqlmock.ExpectedExec {
	return m.sqlmock.ExpectExec("INSERT INTO time_block").
		WithArgs(sqlmock.AnyArg(), sqlmock.AnyArg(), sqlmock.AnyArg())
}

// Expectation for the 'administrator' table
func (m *MockDB) ExpectAdministratorInsert() *sqlmock.ExpectedExec {
	return m.sqlmock.ExpectExec("INSERT INTO administrator").
		WithArgs(sqlmock.AnyArg(), sqlmock.AnyArg(), sqlmock.AnyArg(), sqlmock.AnyArg(), sqlmock.AnyArg())
}

// Expectations with Constraints
// Expectation for the check_time_block_id_list_constraint
func (m *MockDB) ExpectCheckTimeBlockIdListConstraint() *sqlmock.ExpectedExec {
	return m.sqlmock.ExpectExec("CHECK time_block_id_list").
		WillReturnError(fmt.Errorf("constraint violation: time_block_id_list should be from 1 to 24"))
}

// Expectation for the check_time_block_id_list_length_constraint
func (m *MockDB) ExpectCheckTimeBlockIdListLengthConstraint() *sqlmock.ExpectedExec {
	return m.sqlmock.ExpectExec("CHECK ARRAY_LENGTH(time_block_id_list, 1) <= 2").
		WillReturnError(fmt.Errorf("constraint violation: length of array must be 1 or 2"))
}

// Expectation for the unique_availability constraint
func (m *MockDB) ExpectUniqueAvailabilityConstraint() *sqlmock.ExpectedExec {
	return m.sqlmock.ExpectExec("UNIQUE (tutor_id, date, time_block_id)").
		WillReturnError(fmt.Errorf("constraint violation: duplicate availability for tutor_id, date, time_block_id"))
}

// Set up the expectation for UpdateTutorSessionDateAndTimeBlockList
func (m *MockDB) ExpectUpdateTutorSessionDateAndTimeBlockList(session TutoringSession) *sqlmock.ExpectedExec {
	return m.sqlmock.ExpectExec("UPDATE tutoring_session SET date = \\?, time_block_id_list = \\? WHERE tutor_session_id = \\?").
		WithArgs(session.Date, pq.Array(session.TimeBlockIDList), session.TutorSessionID).
		WillReturnResult(sqlmock.NewResult(1, 1)) // Assuming one row affected
}

