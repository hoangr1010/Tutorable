// github.com/macewanCS/w24MacroHard/server/routes
package handlers

import (
	"database/sql"
	"fmt"
	"net/http"

	"github.com/macewanCS/w24MacroHard/server/middleware"
)

// HelloHandler is a sample HTTP handler
func HelloHandler(w http.ResponseWriter, r *http.Request) {
	fmt.Fprint(w, "Hello, Chi!")
}

// Add more routes as needed
func LoginHandler(db *sql.DB) http.HandlerFunc {
	return func(w http.ResponseWriter, r *http.Request) {
		// Read JSON payload
		var login middleware.Login
		err := middleware.DecodeJSONRequestBody(r, &login)
		if err != nil {
			fmt.Println("Invalid JSON:", err)
			return
		}
		fmt.Printf("%+v\n", login)
	}
}

func RegisterHandler(db *sql.DB) http.HandlerFunc {
	return func(w http.ResponseWriter, r *http.Request) {
		// Read JSON payload
		var register middleware.Register
		err := middleware.DecodeJSONRequestBody(r, &register)
		if err != nil {
			fmt.Println("Invalid JSON:", err)
			return
		}

		// Check the role
		if register.Role == "student" {
			// Fetch student insert query
			fmt.Println("Student")
			middleware.InsertStudent(db, register)
		} else if register.Role == "tutor" {
			fmt.Println("Tutor")
		} else if register.Role == "administrator" {
			fmt.Println("Administrator")
		}

		fmt.Printf("%+v\n", register)
	}
}

// Get data from the form
//username := r.FormValue("username")
//email := r.FormValue("email")

/*
	// Insert username
	insertItem(w, db, "username", username)

	// Obtain desired ID to insert items
	id, err := getCurrentID(db, "users_id_seq")
	if err != nil {
		panic(err)
	}
	fmt.Printf("ID grabbed: %d\n", id)

	// Insert email
	updateItem(w, db, "email", email, id)

	// Print Success message to window
	fmt.Fprintf(w, "Data submitted!")
	// Query data from the table
	rows, err := db.Query("SELECT * FROM users")
	if err != nil {
		panic(err)
	}
	defer rows.Close()

	// Process the query results
	for rows.Next() {
		var id int
		var username string
		var email string
		// Scan the columns into variables
		err := rows.Scan(&id, &username, &email)
		if err != nil {
			panic(err)
		}
		fmt.Printf("ID: %d, Username: %s, Email: %s\n", id, username, email)
	}
*/
//}
