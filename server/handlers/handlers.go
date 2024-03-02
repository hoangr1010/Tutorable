// github.com/macewanCS/w24MacroHard/server/routes
package handlers

import (
	"database/sql"
	"encoding/json"
	"fmt"
	"net/http"
	"time"

	"github.com/go-chi/jwtauth/v5"
	"github.com/lib/pq"
	"github.com/macewanCS/w24MacroHard/server/util"
)

// HelloHandler is a sample HTTP handler
func HelloHandler(w http.ResponseWriter, r *http.Request) {
	fmt.Fprint(w, "Hello, Chi!")
}

// Add more routes as needed
func LoginHandler(db *sql.DB) http.HandlerFunc {
	return func(w http.ResponseWriter, r *http.Request) {
		// Read JSON payload
		var login util.Login
		err := util.DecodeJSONRequestBody(r, &login)
		if err != nil {
			fmt.Println("Invalid JSON:", err)
			return
		}

		// Check the role
		if login.Role == "student" {
			// Check if student password is match
			//fmt.Println("Student")
			result, err := util.CheckLoginStudent(db, login)
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
				user, err := util.GetStudentUser(db, login.Email)
				if err != nil {
					fmt.Println("Error searching student table", err)
				}
				//fmt.Println(user)
				// Create Token
				var response util.LoginResponse
				response.User = user

				var userInfo util.UserInfo
				userInfo.Role = "student"
				userInfo.Email = user.Email
				userInfo.ID = user.ID
				token, err := util.CreateToken(userInfo)
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
			result, err := util.CheckLoginTutor(db, login)
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
				user, err := util.GetTutorUser(db, login.Email)
				if err != nil {
					fmt.Println("Error searching student table", err)
				}
				//fmt.Println(user)
				// Store user fields
				var response util.LoginResponse
				response.User = user

				// Create userinfo for token
				var userInfo util.UserInfo
				userInfo.Role = "tutor"
				userInfo.Email = user.Email
				userInfo.ID = user.ID
				// Create token
				token, err := util.CreateToken(userInfo)
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
			fmt.Println("Administrator")
		}

		//fmt.Printf("%+v\n", login)
	}
}

func RegisterHandler(db *sql.DB) http.HandlerFunc {
	return func(w http.ResponseWriter, r *http.Request) {
		// Read JSON payload
		var register util.Register
		err := util.DecodeJSONRequestBody(r, &register)
		if err != nil {
			fmt.Println("Invalid JSON:", err)
			return
		}

		// Hash the password
		err = util.HashPassword(&register.Password)
		if err != nil {
			fmt.Println("Error hashing password:", err)
			return
		}

		fmt.Println("Hashed Password:", register.Password)

		// Check the role
		if register.Role == "student" {
			// Fetch student insert query
			fmt.Println("Student")
			err := util.InsertStudent(db, register)
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
					// http.Error(w, "Error inserting into database", http.StatusInternalServerError)
					// Handle other types of errors
					errorMessage := fmt.Sprintf("Error inserting into database: %v", err) // This is more specific because it shows postgres errors, you can remove it if you want.
					http.Error(w, errorMessage, http.StatusInternalServerError)
				}
			} else {
				// Successful register
				response := util.RegisterResponse{
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
			err := util.InsertTutor(db, register)
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
				response := util.RegisterResponse{
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

/*
Request Body (JSON):
{
  “id”: “string” (required)
  ‘’date”: “YYYY-MM-DD” (required)
  “time_block_id_list”: [unique <time_block_id<Int>>]
}
*/
// Add tutor availability
func AddTutorAvailability(db *sql.DB) http.HandlerFunc {
	return func(w http.ResponseWriter, r *http.Request) {
		_, claims, _ := jwtauth.FromContext(r.Context())
		w.Write([]byte(fmt.Sprintf("protected area. hi %v", claims["user"])))
		// Read JSON payload
		var TutorAvailability util.TutorAvailability
		err := util.DecodeJSONRequestBody(r, &TutorAvailability)
		if err != nil {
			fmt.Println("Invalid JSON:", err)
			return
		}
		// Add timeslot to database
		fmt.Println("Json body: ", TutorAvailability)
		var tutorAvailabilityIdList []int = TutorAvailability.TimeBlockId
		for _, id := range tutorAvailabilityIdList {
			err := util.InsertTutorAvailability(db, TutorAvailability, id)
			if err != nil {
				fmt.Println("Insert failed: ", err)
			}
		}
		fmt.Println("Insert complete.")
	}
}

// Get tutor availability
func GetTutorAvailability(db *sql.DB) http.HandlerFunc {
	return func(w http.ResponseWriter, r *http.Request) {
		// This will store the information from token
		_, claims, _ := jwtauth.FromContext(r.Context())

		// Set response headers
		w.Header().Set("Content-Type", "application/json")

		// Write the response status code
		w.WriteHeader(http.StatusOK)

		// Write initial response content
		w.Write([]byte(fmt.Sprintf("protected area. hi %v", claims["user"])))

		var tutorAvailability util.TutorAvailability
		err := util.DecodeJSONRequestBody(r, &tutorAvailability)
		if err != nil {
			fmt.Println("Invalid JSON:", err)
			http.Error(w, "Inavlid JSON", http.StatusBadRequest)
			return
		}

		if tutorAvailability.ID == 0 ||
			tutorAvailability.Date == "" {
			http.Error(w, "Required field/s are missing", http.StatusBadRequest)
			return
		}

		// Parse date string
		date, err := time.Parse("2006-01-02", tutorAvailability.Date)
		if err != nil {
			http.Error(w, "Invalid date format", http.StatusBadRequest)
			return
		}

		dateStr := date.Format("2006-01-02")

		// Fetch tutor availability from database
		timeBlockIDs, err := util.GetAvailability(db, tutorAvailability.ID, dateStr)
		if err != nil {
			fmt.Printf("Error fetching tutor availability: %v\n", err)
			http.Error(w, "Error fetching tutor availability", http.StatusInternalServerError)
			return
		}

		// Prepare response
		response := struct {
			TimeBlockIDList []int  `json:"time_block_id_list"`
			TimeBlockDate   string `json:"date"`
		}{
			TimeBlockIDList: timeBlockIDs,
			TimeBlockDate:   dateStr,
		}

		// Marshal response to JSON
		jsonResponse, err := json.Marshal(response)
		if err != nil {
			fmt.Printf("Error encoding JSON response: %v\n", err)
			http.Error(w, "Error encoding JSON response", http.StatusInternalServerError)
			return
		}

		// Write response
		w.Write(jsonResponse)
	}
}

// Get tutoring session list
func GetTutoringSessionList(db *sql.DB) http.HandlerFunc {
	return func(w http.ResponseWriter, r *http.Request) {
		// This will store the information from token
		_, claims, _ := jwtauth.FromContext(r.Context())
		w.Write([]byte(fmt.Sprintf("protected area. hi %v", claims["user"])))
		// Put code here :))

	}
}

// SearchTutorAvailability searches all tutor availability for particular time slots.
func SearchTutorAvailability(db *sql.DB) http.HandlerFunc {
	return func(w http.ResponseWriter, r *http.Request) {
		// This will store the information from token
		_, claims, _ := jwtauth.FromContext(r.Context())
		w.Write([]byte(fmt.Sprintf("protected area. hi %v", claims["user"])))
		// Put code here :))

		var tutorAvailability util.TutorAvailability
		err := util.DecodeJSONRequestBody(r, &tutorAvailability)
		if err != nil {
			fmt.Println("Invalid JSON:", err)
			http.Error(w, "Invalid JSON", http.StatusBadRequest)
			return
		}

		// Validate request data
		if tutorAvailability.Date == "" || len(tutorAvailability.TimeBlockId) == 0 {
			http.Error(w, "Date and time_block_id_list are required", http.StatusBadRequest)
			return
		}

		// Parse date string
		date, err := time.Parse("2006-01-02", tutorAvailability.Date)
		if err != nil {
			http.Error(w, "Invalid date format", http.StatusBadRequest)
			return
		}

		// Delete existing tutor availability for the specified date
		err = util.DeleteTutorAvailability(db, tutorAvailability.ID, date)
		if err != nil {
			fmt.Printf("Error deleting tutor availability: %v\n", err)
			http.Error(w, "Error deleting tutor availability", http.StatusInternalServerError)
			return
		}

		// Add new tutor availability for the specified time blocks
		for _, id := range tutorAvailability.TimeBlockId {
			err := util.InsertTutorAvailability(db, tutorAvailability, id)
			if err != nil {
				fmt.Printf("Error adding tutor availability: %v\n", err)
				http.Error(w, "Error adding tutor availability", http.StatusInternalServerError)
				return
			}
		}

		// Prepare response
		response := struct {
			Date            string `json:"date"`
			TimeBlockIDList []int  `json:"time_block_id_list"`
		}{
			Date:            tutorAvailability.Date,
			TimeBlockIDList: tutorAvailability.TimeBlockId,
		}

		// Marshal response to JSON
		jsonResponse, err := json.Marshal(response)
		if err != nil {
			fmt.Printf("Error encoding JSON response: %v\n", err)
			http.Error(w, "Error encoding JSON response", http.StatusInternalServerError)
			return
		}

		// Write response
		w.Header().Set("Content-Type", "application/json")
		w.WriteHeader(http.StatusOK)
		w.Write(jsonResponse)
	}
}

// Add tutoring session
func AddTutoringSession(db *sql.DB) http.HandlerFunc {
	return func(w http.ResponseWriter, r *http.Request) {
		_, claims, _ := jwtauth.FromContext(r.Context())
		w.Write([]byte(fmt.Sprintf("protected area. hi %v", claims["user"])))

		/*
			// Put code here
			// Read JSON payload
			var TutorAvailability util.TutorAvailability
			err := util.DecodeJSONRequestBody(r, &TutorAvailability)
			if err != nil {
				fmt.Println("Invalid JSON:", err)
				return
			}
			// Add timeslot to database
			fmt.Println("Json body: ", TutorAvailability)
			var tutorAvailabilityIdList []int = TutorAvailability.TimeBlockId
			for _, id := range tutorAvailabilityIdList {
				err := util.InsertTutorAvailability(db, TutorAvailability, id)
				if err != nil {
					fmt.Println("Insert failed: ", err)
				}
			}
			fmt.Println("Insert complete.")
		*/
	}
}
