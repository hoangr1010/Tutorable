// github.com/macewanCS/w24MacroHard/server/middleware/util.go
package util

import (
	"bytes"
	"database/sql"
	"encoding/json"
	"fmt"
	"io/ioutil"
	"net/http"

	"os"
	"time"

	"github.com/golang-jwt/jwt/v5"
	"github.com/lib/pq"
	"golang.org/x/crypto/bcrypt"
)

type Login struct {
	Email    string `json:"email"`
	Password string `json:"password"`
	Role     string `json:"role"`
}

type Register struct {
	Email       string         `json:"email"`
	Password    string         `json:"password"`
	Role        string         `json:"role"`
	FirstName   string         `json:"first_name"`
	LastName    string         `json:"last_name"`
	DateOfBirth string         `json:"date_of_birth"`
	Expertise   pq.StringArray `json:"expertise"`
	Experience  string         `json:"experience"`
	Description string         `json:"description"`
	Degrees     pq.StringArray `json:"degrees"`
	Grade       int            `json:"grade"`
	School      string         `json:"school"`
}

type RegisterResponse struct {
	Result bool `json:"result"`
}

type User struct {
	ID             int            `json:"id"`
	Email          string         `json:"email"`
	Role           string         `json:"role"`
	FirstName      string         `json:"first_name"`
	LastName       string         `json:"last_name"`
	DateOfBirth    time.Time      `json:"date_of_birth"`
	Expertise      pq.StringArray `json:"expertise"`
	VerifiedStatus bool           `json:"verified_status"`
	Experience     string         `json:"experience"`
	Description    string         `json:"description"`
	Degrees        pq.StringArray `json:"degrees"`
	Grade          int            `json:"grade"`
	School         string         `json:"school"`
}

type LoginResponse struct {
	Token string `json:"token"`
	User  User   `json:"user"`
}

type UserInfo struct {
	Role  string
	Email string
	ID    int
}

type TutorAvailability struct {
	ID              int    `json:"id"`
	Date            string `json:"date"`
	TimeBlockIdList []int  `json:"time_block_id_list"`
}

type TutoringSession struct {
	TutoringSessionID int    `json:"tutor_session_id"`
	TutorID           int    `json:"tutor_id"`
	TutorName         string `json:"tutor_name"`
	StudentID         int    `json:"student_id"`
	StudentName       string `json:"student_name"`
	Name              string `json:"name"`
	Description       string `json:"description"`
	Subject           string `json:"subject"`
	Grade             int    `json:"grade"`
	Status            string `json:"tutoring_session_status"`
	Date              string `json:"date"`
	TimeBlockIDList   []int  `json:"time_block_id_list"`
}

/*
In the token body we want
role,
email,
tableID
*/
type LoginClaim struct {
	jwt.RegisteredClaims
	TokenType string
	UserInfo
}

// Peeks into tutor availability table and returns true if tutor has timeslots on that date
func PeekAvailabilityDate(db *sql.DB, tutor TutorAvailability) (bool, error) {
	var exists bool

	err := db.QueryRow("SELECT EXISTS(SELECT 1 FROM tutor_availability WHERE tutor_id = $1 AND date = $2)", tutor.ID, tutor.Date).Scan(&exists)
	if err != nil {
		fmt.Println("Error checking tutor_availability: ", err)
		return false, err
	}

	// Default returns false
	return exists, nil
}

// Peeks into tutor availability table and checks if tutor's timeslot is taken
func PeekTimeSlot(db *sql.DB, session TutoringSession, timeBlockId int) (bool, error) {
	var exists bool
	err := db.QueryRow("SELECT EXISTS(SELECT 1 FROM tutor_availability WHERE tutor_idtime_block_id = $1)", timeBlockId).Scan(&exists)
	if err != nil {
		fmt.Println("Error checking tutor_availability: ", err)
		return false, err
	}
	return exists, nil
}

// Legacy
func SearchTutorAvailability(db *sql.DB, date string, timeBlockIDList []int) (tutors []User, err error) {
	var tempTutorArray []User
	//var tutorTag = make(map[int]int)
	// Make a query for each timeblock id
	for _, id := range timeBlockIDList {
		// Make a query
		query := `
			SELECT tutor_id 
			FROM tutor_availability
			WHERE date = $1 AND time_block_id = $2
		`
		rows, err := db.Query(query, date, id)
		if err != nil {
			fmt.Println("Error querying tutor availability: ", err)
			return nil, err
		}
		defer rows.Close()
		// Iterate over the rows
		for rows.Next() {
			var tutor User
			// Scan each row's time block ID into the variable
			if err := rows.Scan(&tutor); err != nil {
				fmt.Println("Error scanning time block ID: ", err)
				return nil, err
			}
			// Append the time block ID to the slice
			tempTutorArray = append(tempTutorArray, tutor)
			//tut
		}
		// Clean up tutors so that only tutors will all timeblocks are present
	}
	return tempTutorArray, nil
}

// GetAvailability retrieves tutor availability from the database for a given tutor ID and date.
func GetAvailability(db *sql.DB, tutorID int, date string) ([]int, error) {
	query := `
        SELECT time_block_id
        FROM tutor_availability
        WHERE tutor_id = $1 AND date = $2
    `

	rows, err := db.Query(query, tutorID, date)
	if err != nil {
		fmt.Println("Error querying tutor availability: ", err)
		return nil, err
	}
	defer rows.Close()

	// Slice to store time block IDs
	var timeBlockIDs []int

	// Iterate over the rows
	for rows.Next() {
		var timeBlockID int
		// Scan each row's time block ID into the variable
		if err := rows.Scan(&timeBlockID); err != nil {
			fmt.Println("Error scanning time block ID: ", err)
			return nil, err
		}
		// Append the time block ID to the slice
		timeBlockIDs = append(timeBlockIDs, timeBlockID)
	}

	// Check for errors in the rows
	if err := rows.Err(); err != nil {
		fmt.Println("Error iterating over rows:", err)
		return nil, err
	}

	// Return the slice of time block IDs
	return timeBlockIDs, nil
}

// DeleteTutorAvailability deletes all available time blocks for a tutor on a specific date.
func DeleteTutorAvailability(db *sql.DB, tutorID int, date time.Time) error {
	query := `
	DELETE FROM tutor_availability WHERE tutor_id = $1 AND date = $2
	`

	_, err := db.Exec(query, tutorID, date.Format("2006-01-02"))
	if err != nil {
		fmt.Printf("Error deleting tutor availability: %v\n", err)
		return err
	}

	return nil
}

/*
func gernerateHMAC(message []byte) string {
	// Convert string to byte array
	key := []byte(os.Getenv("KEY"))
	h := hmac.New(sha256.New, key)
	h.Write(message)
	return hex.EncodeToString(h.Sum(nil))
}
*/

// Add tutoring session
func AddTutoringSession(db *sql.DB, session TutoringSession) error {

	return nil
}

// Returns all of the sessions that student or tutor is in
func GetTutoringSessionList(db *sql.DB, user User) (tutoringSessions []TutoringSession, err error) {
	//var query string

	if user.Role == "student" {
		// Make query for student
		query := `
		SELECT
		tutor_session_id,
		tutor_id,
		name,
		description,
		subject,
		grade,
		status,
		date,
		time_block_id_list
		FROM tutoring_session
		WHERE student_id = $1
		`
		// Execute the SQL query
		rows, err := db.Query(query, user.ID)
		// Scan the results into a struct
		if err != nil {
			fmt.Println("Error scanning tutoring_session: ", err)
		}
		for rows.Next() {
			var session TutoringSession
			var int64Array pq.Int64Array
			err := rows.Scan(
				&session.TutoringSessionID,
				&session.TutorID,
				&session.Name,
				&session.Description,
				&session.Subject,
				&session.Grade,
				&session.Status,
				&session.Date,
				(&int64Array))
			if err != nil {
				fmt.Println("Error scanning tutoring_session: ", err)
			}

			// Convert int64 array into int slices
			for _, i := range int64Array {
				session.TimeBlockIDList = append(session.TimeBlockIDList, int(i))
			}
			var student User
			// Make query for student name
			studentQuery := `
				SELECT
				first_name,
				last_name
				FROM student
				WHERE student_id = $1
			`
			// Execute the SQL query for student
			studentRow := db.QueryRow(studentQuery, user.ID)

			// Scan student first name and student last name
			err = studentRow.Scan(
				&student.FirstName,
				&student.LastName)

			if err != nil {
				fmt.Println("Error scanning student: ", err)
			}
			// Concatenate first name and last name into one string
			studentName := student.FirstName + " " + student.LastName

			// Add studentName into session
			session.StudentName = studentName

			var tutor User
			// Make query for tutor name
			tutorQuery := `
				SELECT
				first_name,
				last_name
				FROM tutor
				WHERE tutor_id = $1
			`
			// Execute the SQL query for tutor
			tutorRow := db.QueryRow(tutorQuery, session.TutorID)

			// Scan tutor first name and tutor last name
			err = tutorRow.Scan(
				&tutor.FirstName,
				&tutor.LastName)

			if err != nil {
				fmt.Println("Error scanning tutor: ", err)
			}
			// Concatenate first name and last name into one string
			tutorName := tutor.FirstName + " " + tutor.LastName

			// Add tutor name to session
			session.TutorName = tutorName

			// Add student ID to session
			session.StudentID = user.ID

			// Append session to list
			tutoringSessions = append(tutoringSessions, session)
		}

		return tutoringSessions, err
	} else if user.Role == "tutor" {
		// Make query for tutor
		query := `
		SELECT
		tutor_session_id,
		student_id,
		name,
		description,
		subject,
		grade,
		status,
		date,
		time_block_id_list
		FROM tutoring_session
		WHERE tutor_id = $1
		`
		// Execute the SQL query
		rows, err := db.Query(query, user.ID)
		// Scan the results into a struct
		if err != nil {
			fmt.Println("Error scanning tutoring_session: ", err)
		}
		for rows.Next() {
			var session TutoringSession
			var int64Array pq.Int64Array
			err := rows.Scan(
				&session.TutoringSessionID,
				&session.StudentID,
				&session.Name,
				&session.Description,
				&session.Subject,
				&session.Grade,
				&session.Status,
				&session.Date,
				(&int64Array))
			if err != nil {
				fmt.Println("Error scanning tutoring_session: ", err)
			}
			// Convert int64 array into int slices
			for _, i := range int64Array {
				session.TimeBlockIDList = append(session.TimeBlockIDList, int(i))
			}

			var student User
			// Make query for student name
			studentQuery := `
				SELECT
				first_name,
				last_name
				FROM student
				WHERE student_id = $1
			`
			// Execute the SQL query for student
			studentRow := db.QueryRow(studentQuery, session.StudentID)

			// Scan student first name and student last name
			err = studentRow.Scan(
				&student.FirstName,
				&student.LastName)

			if err != nil {
				fmt.Println("Error scanning student: ", err)
			}
			// Concatenate first name and last name into one string
			studentName := student.FirstName + " " + student.LastName

			// Add studentName into session
			session.StudentName = studentName

			var tutor User
			// Make query for tutor name
			tutorQuery := `
				SELECT
				first_name,
				last_name
				FROM tutor
				WHERE tutor_id = $1
			`
			// Execute the SQL query for student
			tutorRow := db.QueryRow(tutorQuery, user.ID)

			// Scan tutor first name and tutor last name
			err = tutorRow.Scan(
				&tutor.FirstName,
				&tutor.LastName)

			if err != nil {
				fmt.Println("Error scanning tutor: ", err)
			}
			// Concatenate first name and last name into one string
			tutorName := tutor.FirstName + " " + tutor.LastName

			// Add tutor name to session
			session.TutorName = tutorName

			// Add tutor id to session
			session.TutorID = user.ID

			// Append session to list
			tutoringSessions = append(tutoringSessions, session)
		}
		return tutoringSessions, err
	}
	return tutoringSessions, nil
}

func GetTutorUser(db *sql.DB, email string) (User User, err error) {
	// Prepare the SQL query
	query := `
		SELECT 
		tutor_id, 
		email, 
		expertise, 
		experience, 
		verified_status, 
		description, 
		degree, 
		date_of_birth, 
		first_name, 
		last_name
		FROM tutor
		WHERE email = $1
	`
	// Execute the SQL query
	row := db.QueryRow(query, email)
	// Scan the result into the User struct
	err = row.Scan(
		&User.ID,
		&User.Email,
		&User.Expertise,
		&User.Experience,
		&User.VerifiedStatus,
		&User.Description,
		&User.Degrees,
		&User.DateOfBirth,
		&User.FirstName,
		&User.LastName,
	)
	if err != nil {
		fmt.Println("Error scanning tutor: ", err)
	}
	User.Role = "tutor"
	return User, err
}

func GetStudentUser(db *sql.DB, email string) (User User, err error) {
	// Prepare the SQL query
	query := `
		SELECT 
		student_id, 
		email, 
		grade,
		school,   
		date_of_birth, 
		first_name, 
		last_name
		FROM student
		WHERE email = $1
	`
	// Execute the SQL query
	row := db.QueryRow(query, email)
	// Scan the result into the User struct
	err = row.Scan(
		&User.ID,
		&User.Email,
		&User.Grade,
		&User.School,
		&User.DateOfBirth,
		&User.FirstName,
		&User.LastName,
	)
	if err != nil {
		fmt.Println("Error scanning student: ", err)
	}
	User.Role = "student"
	return User, err
}

func HashPassword(password *string) error {
	// Generate a salted hash for the password
	hashedBytes, err := bcrypt.GenerateFromPassword([]byte(*password), bcrypt.DefaultCost)
	if err != nil {
		return err
	}
	*password = string(hashedBytes)
	return nil
}

func DecodeJSONRequestBody(r *http.Request, data interface{}) error {
	// Read the request body
	body, err := ioutil.ReadAll(r.Body)
	if err != nil {
		fmt.Printf("Error reading request body: %s\n", err)
		return err
	}
	defer r.Body.Close()

	//fmt.Println("Request Body:", string(body))

	// Use the read body to create a new reader for the json decoder
	reader := bytes.NewReader(body)

	// Create a new json decoder with the reader
	decoder := json.NewDecoder(reader)

	// Decode JSON into the provided data structure
	if err := decoder.Decode(data); err != nil {
		fmt.Printf("Error decoding JSON: %s\n", err)
		return err
	}

	return nil
}

// Insert into student table
func InsertStudent(db *sql.DB, student Register) error {
	query := `
	INSERT INTO student (email, password, date_of_birth, school, grade, first_name, last_name) 
	VALUES($1, $2, $3, $4, $5, $6, $7)
	`
	_, err := db.Exec(query,
		student.Email,
		student.Password,
		student.DateOfBirth,
		student.School,
		student.Grade,
		student.FirstName,
		student.LastName)
	if err != nil {
		return err
	}
	return nil
}

// Insert into tutor table
func InsertTutor(db *sql.DB, tutor Register) error {
	query := `
	INSERT INTO tutor (
		email, 
		password, 
		date_of_birth, 
		experience, 
		expertise, 
		description, 
		degree, 
		first_name, 
		last_name) 
	VALUES($1, $2, $3, $4, $5, $6, $7, $8, $9)
	`
	_, err := db.Exec(query,
		tutor.Email,
		tutor.Password,
		tutor.DateOfBirth,
		tutor.Experience,
		tutor.Expertise,
		tutor.Description,
		tutor.Degrees,
		tutor.FirstName,
		tutor.LastName)
	if err != nil {
		return err
	}
	return nil
}

func InsertTutorAvailability(db *sql.DB, tutorAvailability TutorAvailability, timeBlockId int) error {

	// Prepare SQL query to insert tutor availability
	query := `
	INSERT INTO tutor_availability (tutor_id, date, time_block_id) 
	VALUES($1, $2, $3)
	`
	_, err := db.Exec(query,
		tutorAvailability.ID,
		tutorAvailability.Date,
		timeBlockId)

	if err != nil {
		return err
	}
	return nil
}

// Verifies password for login attempt and database
func CheckLoginStudent(db *sql.DB, student Login) (bool, error) {

	// Prepare SQL query to pull password of login email
	query := `SELECT password FROM student WHERE email = $1`
	var databasePassword string
	err := db.QueryRow(query, student.Email).Scan(&databasePassword)
	if err != nil {
		return false, err
	}

	// Compare password from login attempt to password in database
	//fmt.Printf("Login password: %s\n Database password: %s\n", student.Password, databasePassword)
	err = bcrypt.CompareHashAndPassword([]byte(databasePassword), []byte(student.Password))
	if err == nil {
		// Passwords match, authentication sucessful
		return true, err
		// Passwords don't match
	} else if err == bcrypt.ErrMismatchedHashAndPassword {
		return false, err
	}
	return false, err
}

// Verifies password for login attempt and database
func CheckLoginTutor(db *sql.DB, tutor Login) (bool, error) {

	// Prepare SQL query to pull password of login email
	query := `SELECT password FROM tutor WHERE email = $1`
	var databasePassword string
	err := db.QueryRow(query, tutor.Email).Scan(&databasePassword)
	if err != nil {
		return false, err
	}

	// Compare password from login attempt to password in database
	//fmt.Printf("Login password: %s\n Database password: %s\n", tutor.Password, databasePassword)
	err = bcrypt.CompareHashAndPassword([]byte(databasePassword), []byte(tutor.Password))
	if err == nil {
		// Passwords match, authentication sucessful
		return true, err
		// Passwords don't match
	} else if err == bcrypt.ErrMismatchedHashAndPassword {
		return false, err
	}
	return false, err
}

func CreateToken(userInfo UserInfo) (string, error) {
	token := jwt.NewWithClaims(jwt.SigningMethodHS256, jwt.MapClaims{
		"user": userInfo,
		"exp":  time.Now().Add(time.Hour * 24).Unix(), // Expiration time: 1 day
	})
	// Create token string
	signKey := []byte(os.Getenv("KEY"))
	return token.SignedString(signKey)
}
