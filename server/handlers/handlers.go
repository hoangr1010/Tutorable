// github.com/macewanCS/w24MacroHard/server/routes
package handlers

import (
	"database/sql"
	"encoding/json"
	"fmt"
	"net/http"

	"github.com/lib/pq"
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

		// Check the role
		if login.Role == "student" {
			// Check if student password is match
			//fmt.Println("Student")
			result, err := middleware.CheckLoginStudent(db, login)
			if err != nil {
				fmt.Println("Error validating login:", err)
				// Set the HTTP status code to 401 (Bad Request)
				w.WriteHeader(http.StatusUnauthorized)
				// Send a response message
				w.Write([]byte("Bad Login - Invalid credentials"))
			}
			if result {
				fmt.Println("Successful student login!")
				// Store student content into JSON object
				user, err := middleware.GetStudentUser(db, login.Email)
				if err != nil {
					fmt.Println("Error searching student table", err)
				}
				//fmt.Println(user)
				// Create Token
				var response middleware.LoginResponse
				response.User = user
				token, err := middleware.CreateToken()
				if err != nil {
					fmt.Println("Error creating token", err)
				}
				//fmt.Println("Token: ", token)
				response.Token = token
				// Convert response to JSON
				Jsonresponse, err := json.Marshal(response)
				if err != nil {
					http.Error(w, "Error encoding JSON", http.StatusInternalServerError)
					return
				}
				// Set the HTTP status code to 201 (OK)
				w.WriteHeader(http.StatusOK)
				// Set the Content-Type header to application/json
				w.Header().Set("Content-Type", "application/json")
				// Send Json
				w.Write(Jsonresponse)

			}
		} else if login.Role == "tutor" {
			// Check if tutor password is match
			//fmt.Println("Tutor")
			result, err := middleware.CheckLoginTutor(db, login)
			if err != nil {
				fmt.Println("Error validating login:", err)
				// Set the HTTP status code to 401 (Bad Request)
				w.WriteHeader(http.StatusUnauthorized)
				// Send a response message
				w.Write([]byte("Bad Login - Invalid credentials"))
			}
			if result {
				fmt.Println("Successful tutor login!")
				// Store student content into JSON object
				user, err := middleware.GetTutorUser(db, login.Email)
				if err != nil {
					fmt.Println("Error searching student table", err)
				}
				//fmt.Println(user)
				// Create Token
				var response middleware.LoginResponse
				response.User = user
				token, err := middleware.CreateToken()
				if err != nil {
					fmt.Println("Error creating token", err)
				}
				//fmt.Println("Token: ", token)
				response.Token = token
				// Convert response to JSON
				Jsonresponse, err := json.Marshal(response)
				if err != nil {
					http.Error(w, "Error encoding JSON", http.StatusInternalServerError)
					return
				}
				// Set the HTTP status code to 201 (OK)
				w.WriteHeader(http.StatusOK)
				// Set the Content-Type header to application/json
				w.Header().Set("Content-Type", "application/json")
				// Send Json
				w.Write(Jsonresponse)
			}
		} else if login.Role == "administrator" {
			//fmt.Println("Administrator")
		}

		//fmt.Printf("%+v\n", login)
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

		// Hash the password
		err = middleware.HashPassword(&register.Password)
		if err != nil {
			fmt.Println("Error hashing password:", err)
			return
		}

		fmt.Println("Hashed Password:", register.Password)

		// Check the role
		if register.Role == "student" {
			// Fetch student insert query
			fmt.Println("Student")
			err := middleware.InsertStudent(db, register)
			// Error in register
			if err != nil {
				// Check if the error is due to a unique constraint violation
				pqErr, ok := err.(*pq.Error)
				if ok && pqErr.Code == "23505" { // PostgreSQL error code for unique violation
					// Send response code 400 to front end
					fmt.Println("Insert failed: Unique constraint violation")
					// Set the HTTP status code to 400 (Bad Request)
					w.WriteHeader(http.StatusBadRequest)
					// Send a response message
					w.Write([]byte("Bad Request - Existing email"))
				} else {
					// Handle other types of errors
					http.Error(w, "Error inserting into database", http.StatusInternalServerError)

				}
			} else {
				// Successful register
				response := middleware.RegisterResponse{
					Result: true,
				}

				// Convert response to JSON
				jsonResponse, err := json.Marshal(response)
				if err != nil {
					http.Error(w, "Error encoding JSON", http.StatusInternalServerError)
					return
				}

				// Set the Content-Type header to application/json
				w.Header().Set("Content-Type", "application/json")

				// Write the JSON response to the client
				w.Write(jsonResponse)
			}
		} else if register.Role == "tutor" {
			// Insert into student rable
			fmt.Println("Tutor")
			err := middleware.InsertTutor(db, register)
			// Error in register
			if err != nil {
				// Check if the error is due to a unique constraint violation
				pqErr, ok := err.(*pq.Error)
				if ok && pqErr.Code == "23505" { // PostgreSQL error code for unique violation
					// Send response code 400 to front end
					fmt.Println("Insert failed: Unique constraint violation")
					// Set the HTTP status code to 400 (Bad Request)
					w.WriteHeader(http.StatusBadRequest)
					// Send a response message
					w.Write([]byte("Bad Request - Existing email"))
				} else {
					// Handle other types of errors
					fmt.Printf("error: %s\n", err)
					http.Error(w, "Error inserting into database", http.StatusInternalServerError)
				}
			} else {
				// Successful register
				response := middleware.RegisterResponse{
					Result: true,
				}

				// Convert response to JSON
				jsonResponse, err := json.Marshal(response)
				if err != nil {
					http.Error(w, "Error encoding JSON", http.StatusInternalServerError)
					return
				}

				// Set the Content-Type header to application/json
				w.Header().Set("Content-Type", "application/json")

				// Write the JSON response to the client
				w.Write(jsonResponse)
			}
		} else if register.Role == "administrator" {
			fmt.Println("Administrator")
		}

		fmt.Printf("%+v\n", register)
	}
}
