// github.com/macewanCS/w24MacroHard/server/middleware/middleware.go
package middleware

import (
	"bytes"
	"database/sql"
	"encoding/json"
	"fmt"
	"io/ioutil"
	"net/http"

	_ "github.com/lib/pq"
)

type Login struct {
	Email    string `json:"email"`
	Password string `json:"password"`
	Role     string `json:"role"`
}

type Register struct {
	Email       string   `json:"email"`
	Password    string   `json:"password"`
	Role        string   `json:"role"`
	FirstName   string   `json:"first_name"`
	LastName    string   `json:"last_name"`
	DateOfBirth string   `json:"date_of_birth"`
	Expertise   []string `json:"expertise"`
	Experience  string   `json:"experience"`
	Description string   `json:"description"`
	Degrees     []string `json:"degrees"`
	Grade       int      `json:"grade"`
	School      string   `json:"school"`
}

type RegisterResponse struct {
	Result bool `json:"result"`
}

func DecodeJSONRequestBody(r *http.Request, data interface{}) error {
	// Read the request body
	body, err := ioutil.ReadAll(r.Body)
	if err != nil {
		fmt.Printf("Error reading request body: %s\n", err)
		return err
	}
	defer r.Body.Close()

	fmt.Println("Request Body:", string(body))

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

func InsertStudent(db *sql.DB, student Register) {
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
		panic(err)
	}
	return
}

func ExistsStudent() {
	return
}

func UpdateStudent() {
	return
}

func ItemExists(w http.ResponseWriter, db *sql.DB, table string, col string, sqlInput string) (exists bool) {
	// Perform a SELECT query to check if the item exists
	query := fmt.Sprintf("SELECT EXISTS(SELECT 1 FROM %s WHERE %s = $1)", table, col)
	err := db.QueryRow(query, sqlInput).Scan(&exists)
	if err != nil {
		fmt.Println("Error:", err)
		http.Error(w, err.Error(), http.StatusInternalServerError)
		return
	}
	return exists
}

func InsertItem(w http.ResponseWriter, db *sql.DB, table string, col string, sqlInput string) {

	if ItemExists(w, db, table, col, sqlInput) {
		fmt.Fprintf(w, "%s already exists in database!\n", col)
		return
	} else {
		// INSERT into table
		query := fmt.Sprintf("INSERT INTO %s (%s) VALUES ($1)", table, col)
		_, err := db.Exec(query, sqlInput)
		if err != nil {
			http.Error(w, err.Error(), http.StatusInternalServerError)
			return
		}
		// Display success message
		fmt.Printf("%s successfully submitted to the database!\n", col)
	}
	return
}

func UpdateItem(w http.ResponseWriter, db *sql.DB, table string, col string, sqlInput string, id int64) {
	// Execute UPDATE Statement
	query := fmt.Sprintf("UPDATE %s SET %s = '%s' WHERE id = %d", table, col, sqlInput, id)
	_, err := db.Exec(query)
	if err != nil {
		http.Error(w, err.Error(), http.StatusInternalServerError)
		return
	}
	// Display success message
	fmt.Printf("Successfully updated row %s!\n", col)
	return
}
