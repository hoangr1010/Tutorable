// github.com/macewanCS/w24MacroHard/server/middleware/util.go
package util

import (
	"bytes"
	"database/sql"
	"encoding/json"
	"fmt"
	"io/ioutil"
	"net/http"
	"net/smtp"
	"sort"

	"time"

	"github.com/jung-kurt/gofpdf"
	"github.com/lib/pq"
	"golang.org/x/crypto/bcrypt"
	"gopkg.in/gomail.v2"
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
	StudentEmail      string `json:"student_email"`
	TutorEmail        string `json:"tutor_email"`
}

/*
In the token body we want
role,
email,
tableID
*/
/*
type LoginClaim struct {
	jwt.RegisteredClaims
	TokenType string
	UserInfo
}
*/

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

	query := "SELECT EXISTS(SELECT 1 FROM tutor_availability WHERE tutor_id =$1 AND date = $2 AND time_block_id = $3)"
	fmt.Println("")
	fmt.Println("")
	fmt.Printf("Executing query: %s with args: %d, %s, %d\n", query, session.TutorID, session.Date, timeBlockId)
	fmt.Println("")
	fmt.Println("")

	err := db.QueryRow("SELECT EXISTS(SELECT 1 FROM tutor_availability WHERE tutor_id =$1 AND date = $2 AND time_block_id = $3)", session.TutorID, session.Date, timeBlockId).Scan(&exists)
	if err != nil {
		fmt.Println("Error checking tutor_availability: ", err)
		return false, err
	}
	return exists, nil
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

// Delete only specified time slots from tutor availabilty
func DeleteSomeTutorAvailability(db *sql.DB, session TutoringSession, timeBlockID int) error {
	query := `
	DELETE FROM tutor_availability WHERE tutor_id = $1 AND date = $2 AND time_block_id = $3
	`
	_, err := db.Exec(query, session.TutorID, session.Date, timeBlockID)
	if err != nil {
		fmt.Printf("Error deleting tutor availability: %v\n", err)
		return err
	}
	return nil
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

			date, err := ParseDate(session.Date)

			if err != nil {
				fmt.Println("Error parsing date: ", err)
				return tutoringSessions, err
			}
			// If date is not in the future continue to next row
			if !date.After(time.Now()) {
				continue
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

			// Add tutor email to session
			tutorEmail, err := GetTutorEmailByID(db, session.TutorID)
			if err != nil {
				fmt.Println("Error scanning for tutor email: ", err)
			}
			session.TutorEmail = tutorEmail
			// Add student email to session
			studentEmail, err := GetStudentEmailByID(db, session.StudentID)
			if err != nil {
				fmt.Println("Error scanning for student email: ", err)
			}
			session.StudentEmail = studentEmail
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

			date, err := ParseDate(session.Date)

			if err != nil {
				fmt.Println("Error parsing date: ", err)
				return tutoringSessions, err
			}
			// If date is not in the future continue to next row
			if !date.After(time.Now()) {
				continue
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

			// Add tutor email to session
			tutorEmail, err := GetTutorEmailByID(db, session.TutorID)
			if err != nil {
				fmt.Println("Error scanning for tutor email: ", err)
			}
			session.TutorEmail = tutorEmail
			// Add student email to session
			studentEmail, err := GetStudentEmailByID(db, session.StudentID)
			if err != nil {
				fmt.Println("Error scanning for student email: ", err)
			}
			session.StudentEmail = studentEmail

			// Append session to list
			tutoringSessions = append(tutoringSessions, session)
		}
		return tutoringSessions, err
	}
	return tutoringSessions, nil
}

// SearchTutorsAvailability fetches tutors' availability based on the provided date and time block IDs.
func SearchAvailability(db *sql.DB, date string, timeBlockIDs []int) ([]int, error) {
	// Construct placeholders for the time block IDs
	var placeholders string
	for i := range timeBlockIDs {
		if i > 0 {
			placeholders += ", "
		}
		placeholders += fmt.Sprintf("$%d", i+2)
	}

	fmt.Println(placeholders)
	// Your SQL query to fetch tutor IDs based on date and time block IDs
	query := fmt.Sprintf(`
		SELECT DISTINCT tutor_id FROM tutor_availability
		WHERE date = $1 AND time_block_id IN (%s)
	`, placeholders)

	// Makes a new query for in there are more placeholders
	if len(timeBlockIDs) > 1 {
		query = fmt.Sprintf(`
		SELECT DISTINCT tutor_id FROM tutor_availability
		WHERE date = $1 AND time_block_id IN (%s)
		GROUP BY tutor_id
		HAVING COUNT(DISTINCT time_block_id) = %d;
		`, placeholders, len(timeBlockIDs))
	}
	// Construct arguments for the query
	args := make([]interface{}, len(timeBlockIDs)+1)
	args[0] = date
	for i, id := range timeBlockIDs {
		args[i+1] = id
	}

	// Execute the query
	rows, err := db.Query(query, args...)
	if err != nil {
		return nil, err
	}
	defer rows.Close()

	var tutorIDs []int
	for rows.Next() {
		var tutorID int
		err := rows.Scan(&tutorID)
		if err != nil {
			return nil, err
		}
		tutorIDs = append(tutorIDs, tutorID)
	}
	if err := rows.Err(); err != nil {
		return nil, err
	}

	return tutorIDs, nil
}

// GetTutorEmailByID fetches the email of the tutor with the given ID.
func GetTutorEmailByID(db *sql.DB, tutorID int) (string, error) {
	// Prepare the SQL query
	query := `
		SELECT email FROM tutor WHERE tutor_id = $1
	`
	// Execute the SQL query
	row := db.QueryRow(query, tutorID)
	// Scan the result into a string
	var email string
	err := row.Scan(&email)
	if err != nil {
		fmt.Println("Error scanning tutor email: ", err)
		return "", err
	}
	return email, nil
}

// GetStudentEmailByID fetches the email of the student with the given ID.
func GetStudentEmailByID(db *sql.DB, tutorID int) (string, error) {
	// Prepare the SQL query
	query := `
		SELECT email FROM student WHERE student_id = $1
	`
	// Execute the SQL query
	row := db.QueryRow(query, tutorID)
	// Scan the result into a string
	var email string
	err := row.Scan(&email)
	if err != nil {
		fmt.Println("Error scanning student email: ", err)
		return "", err
	}
	return email, nil
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

// Insert tutoring session
func InsertTutoringSession(db *sql.DB, session TutoringSession) error {
	query := `
	INSERT INTO 
	tutoring_session 
	(tutor_id, student_id, name, description, subject, grade, date, time_block_id_list) 
	VALUES
	($1, $2, $3, $4, $5, $6, $7, $8)
	`
	_, err := db.Exec(query,
		session.TutorID,
		session.StudentID,
		session.Name,
		session.Description,
		session.Subject,
		session.Grade,
		session.Date,
		pq.Array(session.TimeBlockIDList))

	if err != nil {
		fmt.Println("Error inserting into tutoring session: ", err)
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

/*
func CreateToken(userInfo UserInfo) (string, error) {
	token := jwt.NewWithClaims(jwt.SigningMethodHS256, jwt.MapClaims{
		"user": userInfo,
		"exp":  time.Now().Add(time.Hour * 24).Unix(), // Expiration time: 1 day
	})
	// Create token string
	signKey := []byte(os.Getenv("KEY"))
	return token.SignedString(signKey)
}
*/

// parseDate takes a date string in the format "yyyy-mm-dd" and returns a time.Time object
func ParseDate(dateString string) (time.Time, error) {
	// Layouts to try
	layouts := []string{
		"2006-01-02", // Reference layout for date only
		time.RFC3339, // Reference layout for date and time with timezone
	}

	var parsedTime time.Time
	var err error
	for _, layout := range layouts {
		parsedTime, err = time.Parse(layout, dateString)
		if err == nil {
			return parsedTime, nil // Successfully parsed
		}
	}
	return time.Time{}, fmt.Errorf("unable to parse date: %v", dateString)
}

func SendEmail(recipient []string, subject string, body string) error {
	// Set up authentication credentials
	smtpUsername := "macrohard2024@gmail.com"
	smtpPassword := "tvpo evlv syou zllf"
	smtpServer := "smtp.gmail.com"
	smtpPort := "587"

	// Set up the email
	from := "macrohard2024@gmail.com"

	// Set up the SMTP client
	auth := smtp.PlainAuth("", smtpUsername, smtpPassword, smtpServer)
	smtpAddr := smtpServer + ":" + smtpPort

	// Compose the email message
	message := []byte("To: " + recipient[0] + "\r\n" +
		"Subject: " + subject + "\r\n" +
		"\r\n" +
		body + "\r\n")

	// Send the email
	err := smtp.SendMail(smtpAddr, auth, from, recipient, message)
	if err != nil {
		fmt.Println("Error sending email: ", err)
		return err
	}
	fmt.Println("Email sent successfully!")
	return err
}

// Insert tutoring session
func DeleteTutoringSession(db *sql.DB, sessionID int) error {
	query := `
	DELETE FROM tutoring_session WHERE tutor_session_id = $1
	`
	_, err := db.Exec(query, sessionID)
	if err != nil {
		fmt.Printf("Error deleting tutor session: %v\n", err)
		return err
	}

	return nil
}

// Get tutor session
func GetTutorSession(db *sql.DB, sessionID int) (session TutoringSession, err error) {
	query := `
		SELECT
		student_id,
		tutor_id,
		name,
		description,
		subject,
		grade,
		status,
		date,
		time_block_id_list
		FROM tutoring_session
		WHERE tutor_session_id = $1
		`
	// Execute the SQL query
	row := db.QueryRow(query, sessionID)
	// Scan the results into a struct

	var int64Array pq.Int64Array
	err = row.Scan(
		&session.StudentID,
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
		return session, err
	}

	// Convert int64 array into int slices
	for _, i := range int64Array {
		session.TimeBlockIDList = append(session.TimeBlockIDList, int(i))
	}
	return session, err
}

// Get tutorID from session ID
func GetTutorFromSession(db *sql.DB, session TutoringSession) (tutorID int, err error) {
	// Define SQL query
	query := `
	SELECT tutor_id
	FROM tutoring_session
	WHERE tutor_session_id = $1
	`
	// Execute sql query
	row := db.QueryRow(query, session.TutoringSessionID)
	err = row.Scan(&tutorID)
	if err != nil {
		fmt.Println("Error searching tutoring session for tutor_id: ", err)
		return tutorID, err
	}

	return tutorID, err
}

// Get time_block_id_list from session ID
func GetTimeBlockIdListFromSession(db *sql.DB, session TutoringSession) (time_block_id_list []int, err error) {
	// Define SQL query
	query := `
	SELECT time_block_id_list
	FROM tutoring_session
	WHERE tutor_session_id = $1
	`
	// Execute sql query
	row := db.QueryRow(query, session.TutoringSessionID)
	var int64Array pq.Int64Array
	err = row.Scan(&int64Array)
	if err != nil {
		fmt.Println("Error searching tutoring session for time_block_id_list: ", err)
		return time_block_id_list, err
	}

	// Convert int64 array into int slices
	for _, i := range int64Array {
		time_block_id_list = append(time_block_id_list, int(i))
	}
	return time_block_id_list, err
}

// Get date from session_id
func GetDateFromSession(db *sql.DB, session TutoringSession) (date time.Time, err error) {
	// Define SQL query
	query := `
	SELECT date
	FROM tutoring_session
	WHERE tutor_session_id = $1
	`
	// Execute sql query
	row := db.QueryRow(query, session.TutoringSessionID)
	err = row.Scan(&date)
	if err != nil {
		fmt.Println("Error searching tutoring session for date: ", err)
		return date, err
	}

	return date, err
}

// Updates the Tutoring Session Date and Time Block List
func UpdateTutorSessionDateAndTimeBlockList(db *sql.DB, session TutoringSession) error {
	// Define the SQL query to update a row
	query := `
	UPDATE tutoring_session
	SET date = $1, time_block_id_list = $2 
	WHERE tutor_session_id = $3   
`
	// Execute the update query
	_, err := db.Exec(query, session.Date, pq.Array(session.TimeBlockIDList), session.TutoringSessionID)
	if err != nil {
		fmt.Println("Error updating tutoring_session Date and timeblock", err)
		return err
	}

	return nil
}

// Converts TimeBlockIDList into a string of smallest and largest time
func TimeBlockToString(db *sql.DB, session TutoringSession) (timeString string, err error) {
	// Create a slice of time
	var timeList []time.Time
	query := `
	SELECT
	start_time,
	end_time
	FROM time_block
	WHERE time_block_id = $1 
	`
	for _, i := range session.TimeBlockIDList {
		// Execute the SQL query
		var startTime time.Time
		var endTime time.Time
		row := db.QueryRow(query, i)
		err = row.Scan(&startTime, &endTime)
		if err != nil {
			fmt.Println("Error scanning timeblock: ", err)
			return timeString, err
		}
		timeList = append(timeList, startTime, endTime)
	}

	// Sort time values
	sort.Slice(timeList, func(i, j int) bool {
		return timeList[i].Before(timeList[j])
	})

	// Convert the smallest and largest time values into strings
	smallestTime := timeList[0].Format("15:04")
	largestTime := timeList[len(timeList)-1].Format("15:04")

	// eg. 15:00 - 17:00
	timeString = fmt.Sprintf("%s - %s", smallestTime, largestTime)
	return timeString, err
}

// GetStudentEmailBySessionID fetches the email of the student with the given ID.
func GetStudentEmailBySessionID(db *sql.DB, session TutoringSession) (string, error) {
	// Prepare the SQL query
	query := `
		SELECT email FROM tutoring_session WHERE tutor_session_id = $1
	`
	// Execute the SQL query
	row := db.QueryRow(query, session.TutoringSessionID)
	// Scan the result into a string
	var email string
	err := row.Scan(&email)
	if err != nil {
		fmt.Println("Error scanning student email: ", err)
		return "", err
	}
	return email, nil
}

func CreateMasterSchedule(db *sql.DB) (filename string, err error) {
	// Query the database to retrieve the data
	query := `
		SELECT *
		FROM tutoring_session
		WHERE date >= DATE_TRUNC('month', CURRENT_DATE) 
		AND date < DATE_TRUNC('month', CURRENT_DATE) + INTERVAL '2 months'
	`
	rows, err := db.Query(query)
	if err != nil {
		fmt.Println("Error with tutoring_session query")
		return filename, err
	}
	// Create a new PDF document
	pdf := gofpdf.New("P", "mm", "A4", "")

	// Add a page
	pdf.AddPage()

	// Set font for the table
	pdf.SetFont("Arial", "", 6)

	// Get the current month
	currentMonth := time.Now().Month()
	// Get the next month
	nextMonth := time.Now().AddDate(0, 1, 0).Month()
	// Combine currentMonth and nextMonth
	title := fmt.Sprintf("Master SChedule for %s - %s", currentMonth, nextMonth)
	pdf.CellFormat(0, 10, title, "", 1, "C", false, 0, "")

	// Define column widths
	// sessionID, Tutor Name, Student Name, Title, subject, grade, status, date, time
	colWidths := []float64{15, 20, 20, 30, 30, 20, 15, 20, 20, 15}

	//Define column headers
	headers := []string{"Session", "Tutor Name", "Student Name", "Title", "subject", "grade", "status", "date", "time"}
	// Create table header
	for i, header := range headers {
		pdf.CellFormat(colWidths[i], 10, header, "1", 0, "C", false, 0, "")
	}
	pdf.Ln(10)

	// Iterate over the rows and write data to the buffer
	var count int = 0
	for rows.Next() {
		count += 1
		var session TutoringSession
		var int64Array pq.Int64Array
		err := rows.Scan(
			&session.TutoringSessionID,
			&session.TutorID,
			&session.StudentID,
			&session.Name,
			&session.Description,
			&session.Subject,
			&session.Grade,
			&session.Status,
			&session.Date,
			&int64Array)
		if err != nil {
			fmt.Println("Error scanning tutoring_session: ", err)
			return filename, err
		}
		//fmt.Println("Row: ", session)
		// Convert int64 array into int slices
		for _, i := range int64Array {
			session.TimeBlockIDList = append(session.TimeBlockIDList, int(i))
		}
		//fmt.Println("Row: ", session)

		timeBlockIDListString, err := TimeBlockToString(db, session)
		if err != nil {
			fmt.Println("Error with time_block query")
			return filename, err

		}

		// Parse the date string into a time.Time object
		date, err := time.Parse(time.RFC3339, session.Date)
		if err != nil {
			fmt.Println("Error parsing date:", err)
			return filename, err
		}

		// Format the date into a human-readable layout
		formattedDate := date.Format("January 2, 2006") // Example format: "March 2, 2024

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

		pdf.CellFormat(colWidths[0], 10, fmt.Sprintf("%d", count), "1", 0, "C", false, 0, "")
		pdf.CellFormat(colWidths[1], 10, tutorName, "1", 0, "C", false, 0, "")
		pdf.CellFormat(colWidths[2], 10, studentName, "1", 0, "C", false, 0, "")
		pdf.CellFormat(colWidths[3], 10, session.Name, "1", 0, "C", false, 0, "")
		pdf.CellFormat(colWidths[4], 10, session.Subject, "1", 0, "C", false, 0, "")
		pdf.CellFormat(colWidths[5], 10, fmt.Sprintf("%d", session.Grade), "1", 0, "C", false, 0, "")
		pdf.CellFormat(colWidths[6], 10, session.Status, "1", 0, "C", false, 0, "")
		pdf.CellFormat(colWidths[7], 10, formattedDate, "1", 0, "C", false, 0, "")
		pdf.CellFormat(colWidths[8], 10, timeBlockIDListString, "1", 0, "C", false, 0, "")
		pdf.Ln(10)
	}

	// Get the current date and time for creating a unique filename
	currentTime := time.Now().Format("2006-01-02_15-04-05")
	filename = fmt.Sprintf("tutoring_session_export_%s.pdf", currentTime)
	// Save the PDF content to the file
	if err := pdf.OutputFileAndClose(filename); err != nil {
		return filename, fmt.Errorf("error saving PDF content to file: %v", err)
	}
	return filename, err
}

func SendEmailWithAttachment(recipient []string, subject string, body string, filename string) error {
	// Set up authentication credentials
	smtpUsername := "macrohard2024@gmail.com"
	smtpPassword := "tvpo evlv syou zllf"
	smtpServer := "smtp.gmail.com"
	smtpPort := 587

	// Compose the email message using gomail
	message := gomail.NewMessage()
	message.SetHeader("From", smtpUsername)
	message.SetHeader("To", recipient...)
	message.SetHeader("Subject", subject)
	message.SetBody("text/plain", body)
	message.Attach(filename)
	// Set up the SMTP client
	d := gomail.NewDialer(smtpServer, smtpPort, smtpUsername, smtpPassword)

	// Send the email
	if err := d.DialAndSend(message); err != nil {
		fmt.Println("Error sending email: ", err)
		return err
	}

	fmt.Println("Email sent successfully!")
	return nil
}
