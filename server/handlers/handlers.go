package handlers

import (
	"database/sql"
	"encoding/json"
	"fmt"
	"net/http"
	"time"

	//"github.com/go-chi/jwtauth"
	//"github.com/go-chi/jwtauth/v5"

	"github.com/lib/pq"
	"github.com/macewanCS/w24MacroHard/server/util"
)

// Add more routes as needed
func LoginHandler(db *sql.DB) http.HandlerFunc {
	return func(w http.ResponseWriter, r *http.Request) {
		// Read JSON payload
		var login util.Login
		err := util.DecodeJSONRequestBody(r, &login)
		if err != nil {
			fmt.Println("Invalid JSON:", err)
			http.Error(w, "Invalid JSON", http.StatusBadRequest)
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
		} else {
			http.Error(w, "Invalid role", http.StatusBadRequest)
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

		//fmt.Println("Hashed Password:", register.Password)

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

				// Write the response status code
				w.WriteHeader(http.StatusOK)

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

				// Write the response status code
				w.WriteHeader(http.StatusOK)

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
		//_, claims, _ := jwtauth.FromContext(r.Context())
		//w.Write([]byte(fmt.Sprintf("protected area. hi %v", claims["user"])))

		// Set response headers
		w.Header().Set("Content-Type", "application/json")

		// Write the response status code
		w.WriteHeader(http.StatusOK)

		// Read JSON payload
		var tutorAvailability util.TutorAvailability
		err := util.DecodeJSONRequestBody(r, &tutorAvailability)
		if err != nil {
			fmt.Println("Invalid JSON:", err)
			http.Error(w, "Inavlid JSON", http.StatusBadRequest)
			return
		}
		// Parse the date
		date, err := time.Parse("2006-01-02", tutorAvailability.Date)
		if err != nil {
			http.Error(w, "Invalid date format", http.StatusBadRequest)
			return
		}

		//dateStr := date.Format("2006-01-02")

		// Check if timeslots exist for that date, delete if exist
		exists, err := util.PeekAvailabilityDate(db, tutorAvailability)
		if err != nil {
			http.Error(w, "WHOOPS!", http.StatusInternalServerError)
		}
		// Delete existing entries of tutor on that date
		if exists {
			util.DeleteTutorAvailability(db, tutorAvailability.ID, date)
		}
		// Add timeslot to database
		//fmt.Println("Json body: ", TutorAvailability) // line for debug
		for _, id := range tutorAvailability.TimeBlockIdList {
			err := util.InsertTutorAvailability(db, tutorAvailability, id)
			if err != nil {
				fmt.Println("Insert failed: ", err)
				http.Error(w, "Inavlid JSON", http.StatusInternalServerError)
			}
		}
		fmt.Println("Insert complete.")
		// Insert complete -> Send response
		response := struct {
			Date            time.Time `json:"date"`
			TimeBlockIdList []int     `json:"time_block_id_list"`
		}{
			Date:            date,
			TimeBlockIdList: tutorAvailability.TimeBlockIdList,
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

// Get tutor availability
func GetTutorAvailability(db *sql.DB) http.HandlerFunc {
	return func(w http.ResponseWriter, r *http.Request) {
		// This will store the information from token
		//_, claims, _ := jwtauth.FromContext(r.Context())

		// Set response headers
		w.Header().Set("Content-Type", "application/json")

		// Write the response status code
		w.WriteHeader(http.StatusOK)

		// Write initial response content
		//w.Write([]byte(fmt.Sprintf("protected area. hi %v", claims["user"])))

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
			TimeBlockIDList []int `json:"time_block_id_list"`
			//TimeBlockDate   string `json:"date"`
		}{
			TimeBlockIDList: timeBlockIDs,
			//TimeBlockDate:   dateStr,
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

// fixed
// SearchTutorAvailability searches all tutor availability for particular time slots.
func SearchTutorAvailability(db *sql.DB) http.HandlerFunc {
	return func(w http.ResponseWriter, r *http.Request) {
		// Decode request body into TutorAvailability struct
		var tutorAvailability util.TutorAvailability
		err := util.DecodeJSONRequestBody(r, &tutorAvailability)
		if err != nil {
			fmt.Println("Invalid JSON:", err)
			http.Error(w, "Invalid JSON", http.StatusBadRequest)
			return
		}

		// Validate request data
		if tutorAvailability.Date == "" || len(tutorAvailability.TimeBlockIdList) == 0 {
			http.Error(w, "Date and time_block_id_list are required", http.StatusBadRequest)
			return
		}

		// Search for tutor availability based on date and time block IDs
		tutorIDs, err := util.SearchAvailability(db, tutorAvailability.Date, tutorAvailability.TimeBlockIdList)
		if err != nil {
			fmt.Println("Error searching tutor availability: ", err)
			http.Error(w, "Error searching table", http.StatusInternalServerError)
			return
		}

		// Fetch tutor details based on tutor IDs
		var tutors []util.User
		for _, tutorID := range tutorIDs {
			// Get the tutor's email
			tutorEmail, err := util.GetTutorEmailByID(db, tutorID)
			if err != nil {
				fmt.Println("Error fetching tutor email: ", err)
				http.Error(w, "Error fetching tutor email", http.StatusInternalServerError)
				return
			}

			// Fetch tutor details based on email
			tutor, err := util.GetTutorUser(db, tutorEmail)
			if err != nil {
				fmt.Println("Error fetching tutor details: ", err)
				http.Error(w, "Error fetching tutor details", http.StatusInternalServerError)
				return
			}
			tutors = append(tutors, tutor)
		}

		// If tutors is empty redeclare it
		if len(tutors) == 0 {
			tutors = []util.User{}
		}
		// Prepare response
		response := struct {
			TutorList []util.User `json:"tutor_list"`
		}{
			TutorList: tutors,
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
		//_, claims, _ := jwtauth.FromContext(r.Context())
		//w.Write([]byte(fmt.Sprintf("protected area. hi %v", claims["user"])))
		// Read JSON payload
		var session util.TutoringSession
		err := util.DecodeJSONRequestBody(r, &session)
		if err != nil {
			fmt.Println("Invalid JSON:", err)
			http.Error(w, "Error parsing JSON", http.StatusBadRequest)
			return
		}

		var tutor util.TutorAvailability
		tutor.ID = session.TutorID
		// Check if tutor has availability in timeslot
		for _, id := range session.TimeBlockIDList {
			exists, err := util.PeekTimeSlot(db, session, id)
			if err != nil {
				fmt.Println("Error checking tutor_availability: ", err)
				http.Error(w, "HEEEEEEELP", http.StatusBadRequest)
			}
			if !exists {
				http.Error(w, "Tutor is not available", http.StatusUnauthorized) // status code 401
				return
			}
		}
		// Create session if they are available
		err = util.InsertTutoringSession(db, session)
		if err != nil {
			http.Error(w, "Error inserting into tutoring session", http.StatusInternalServerError) // status code 500
			return
		}
		// Delete all availability shown in time_block_id_list
		for _, id := range session.TimeBlockIDList {
			err = util.DeleteSomeTutorAvailability(db, session, id)
			if err != nil {
				http.Error(w, "Error deleting tutor availability", http.StatusInternalServerError) // status code 500
				return
			}
		}
		// Email tutor and student
		subject := fmt.Sprintf("TutorMe %s", session.Date)
		body := fmt.Sprintf("A new session has been made for %s", session.Date) // Maybe add time
		tutorEmail, err := util.GetTutorEmailByID(db, session.TutorID)
		if err != nil {
			fmt.Println("Error querying for tutor email")
			return
		}
		studentEmail, err := util.GetStudentEmailByID(db, session.StudentID)
		if err != nil {
			fmt.Println("Error querying for student email")
			return
		}
		// Send tutor email
		recipients := []string{tutorEmail}
		err = util.SendEmail(recipients, subject, body)
		if err != nil {
			fmt.Println("Error sending email: ", err)
		}
		// Send student email
		recipients = []string{studentEmail}
		err = util.SendEmail(recipients, subject, body)
		if err != nil {
			fmt.Println("Error sending email: ", err)
		}

		// Prepare response
		response := struct {
			TimeBlockIDList []int `json:"time_block_id_list"`
		}{
			TimeBlockIDList: session.TimeBlockIDList,
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

// should work now
// Get tutoring session list
func GetTutoringSessionList(db *sql.DB) http.HandlerFunc {
	return func(w http.ResponseWriter, r *http.Request) {
		// This will store the information from token
		//_, claims, _ := jwtauth.FromContext(r.Context())
		//w.Write([]byte(fmt.Sprintf("protected area. hi %v", claims["user"])))

		// Pull role and id from JSON body
		var user util.User
		err := util.DecodeJSONRequestBody(r, &user)
		if err != nil {
			fmt.Println("Invalid JSON:", err)
			http.Error(w, "Invalid JSON", http.StatusBadRequest)
			return
		}

		tutoringSessions, err := util.GetTutoringSessionList(db, user)

		if err != nil {
			http.Error(w, "Whoopsie!", http.StatusInternalServerError)
			return
		}

		// If tutorinSessions is empty redeclare it
		if len(tutoringSessions) == 0 {
			tutoringSessions = []util.TutoringSession{}
		}
		// Prepare response
		response := struct {
			ID               int                    `json:"id"`
			Role             string                 `json:"role"`
			TutoringSessions []util.TutoringSession `json:"tutoring_session_list"`
		}{
			ID:               user.ID,
			Role:             user.Role,
			TutoringSessions: tutoringSessions,
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

// Deletes tutor session
func DeleteTutorSession(db *sql.DB) http.HandlerFunc {
	return func(w http.ResponseWriter, r *http.Request) {
		//_, claims, _ := jwtauth.FromContext(r.Context())
		//w.Write([]byte(fmt.Sprintf("protected area. hi %v", claims["user"])))
		// Read JSON payload
		type ID struct {
			ID int `json:"session_id"`
		}
		var sessionID ID
		err := util.DecodeJSONRequestBody(r, &sessionID)
		if err != nil {
			fmt.Println("Invalid JSON:", err)
			http.Error(w, "Error parsing JSON", http.StatusBadRequest) // status code 400
			return
		}

		// get tutor session
		var session util.TutoringSession
		session, err = util.GetTutorSession(db, sessionID.ID)
		if err != nil {
			http.Error(w, "Error fetching tutoring session", http.StatusUnauthorized) // status code 401
			return
		}

		// Delete Session session
		err = util.DeleteTutoringSession(db, sessionID.ID)
		if err != nil {
			http.Error(w, "Error deleting tutoring session", http.StatusUnauthorized) // status code 401
			return
		}

		var tutorAvailability util.TutorAvailability
		tutorAvailability.ID = session.TutorID
		tutorAvailability.Date = session.Date
		tutorAvailability.TimeBlockIdList = session.TimeBlockIDList
		// Return all availability shown in time_block_id_list
		for _, id := range tutorAvailability.TimeBlockIdList {
			err = util.InsertTutorAvailability(db, tutorAvailability, id)
			if err != nil {
				http.Error(w, "Error deleting tutor availability", http.StatusInternalServerError) // status code 500
				return
			}
		}

		// Email tutor and student
		subject := fmt.Sprintf("TutorMe: %s", session.Date)
		body := fmt.Sprintf("Session ID:%d has been deleted.", session.TutoringSessionID)
		tutorEmail, err := util.GetTutorEmailByID(db, session.TutorID)
		if err != nil {
			http.Error(w, "Error getting tutor email", http.StatusInternalServerError) // status code 500
			return
		}
		studentEmail, err := util.GetStudentEmailByID(db, session.StudentID)
		if err != nil {
			http.Error(w, "Error getting student email", http.StatusInternalServerError) // status code 500
			return
		}
		recipients := []string{tutorEmail, studentEmail}
		err = util.SendEmail(recipients, subject, body)
		if err != nil {
			fmt.Println("Error sending email: ", err)
		}

		// Prepare response
		response := struct {
			TimeBlockIDList  []int `json:"time_block_id_list"`
			SessionIDDeleted int   `json:"session_id_deleted"`
		}{
			TimeBlockIDList:  session.TimeBlockIDList,
			SessionIDDeleted: sessionID.ID,
		}

		// Marshal response to JSON
		jsonResponse, err := json.Marshal(response)
		if err != nil {
			fmt.Printf("Error encoding JSON response: %v\n", err)
			http.Error(w, "Error encoding JSON response", http.StatusInternalServerError) // status code 500
			return
		}

		// Write response
		w.Header().Set("Content-Type", "application/json")
		w.WriteHeader(http.StatusOK)
		w.Write(jsonResponse)
	}
}

// Updates the tutor session's date and time block id list
func EditTutorSession(db *sql.DB) http.HandlerFunc {
	return func(w http.ResponseWriter, r *http.Request) {
		//w.Write([]byte(fmt.Sprintf("protected area. hi %v", claims["user"])))
		// Read JSON payload
		var session util.TutoringSession
		err := util.DecodeJSONRequestBody(r, &session)
		if err != nil {
			fmt.Println("Invalid JSON:", err)
			http.Error(w, "Error parsing JSON", http.StatusBadRequest) // status code 400
			return
		}

		//get tutorID from session
		session.TutorID, err = util.GetTutorFromSession(db, session)
		if err != nil {
			http.Error(w, "Error searching tutoring_session for tutor_id", http.StatusInternalServerError) // status code 500
			return
		}

		//get time_block_id_list from session
		oldTimeBlockIdList, err := util.GetTimeBlockIdListFromSession(db, session)
		if err != nil {
			http.Error(w, "Error searching tutoring_session for time_block_id_list", http.StatusInternalServerError) // status code 500
			return
		}

		// get old date from session
		oldDate, err := util.GetDateFromSession(db, session)
		if err != nil {
			http.Error(w, "Error searching tutoring_session for date", http.StatusInternalServerError) // status code 500
			return
		}
		// Check if tutor is available on that date and time before updating
		for _, id := range session.TimeBlockIDList {
			exists, err := util.PeekTimeSlot(db, session, id)
			if err != nil {
				fmt.Println("Error checking tutor_availability: ", err)
				http.Error(w, "HEEEEEEELP", http.StatusInternalServerError) // Code 500
			}
			if !exists {
				http.Error(w, "Tutor is not available", http.StatusUnauthorized) // status code 401
				return
			}
		}

		// Update tutor session with new date and time_block_list
		err = util.UpdateTutorSessionDateAndTimeBlockList(db, session)
		if err != nil {
			http.Error(w, "Error updating tutoring_session", http.StatusInternalServerError) // status code 500
			return
		}

		// Delete all availability shown in time_block_id_list -- BUG HERE?
		for _, id := range session.TimeBlockIDList {
			err = util.DeleteSomeTutorAvailability(db, session, id)
			if err != nil {
				http.Error(w, "Error deleting tutor availability", http.StatusInternalServerError) // status code 500
				return
			}
		}

		// Add old availability shown in oldTimeBlockIdList -- BUG HERE??
		if !(oldDate.Before(time.Now())) {
			var tutorAvailability util.TutorAvailability
			tutorAvailability.ID = session.TutorID
			tutorAvailability.Date = oldDate.Format("2006-01-02 15:04:05")
			for _, id := range oldTimeBlockIdList {
				// requires old date and tutor id
				err = util.InsertTutorAvailability(db, tutorAvailability, id)
				if err != nil {
					http.Error(w, "Error adding tutor availability", http.StatusInternalServerError) // status code 500
					return
				}
			}
		}

		fmt.Println("Row updated successfully!")
		date, err := util.ParseDate(session.Date)
		if err != nil {
			fmt.Println("Error parsing date ;_; ", err)
			http.Error(w, "Unexpected error", http.StatusInternalServerError) // status code 500
			return
		}

		// Convert time block list to string
		timeString, err := util.TimeBlockToString(db, session)
		if err != nil {
			fmt.Println("Error formating time block to string: ", err)
		}
		// Email tutor and student involved
		subject := fmt.Sprintf("TutorMe: %s Session Change", session.Date)
		body := fmt.Sprintf("Session ID:%d's date and time have been moved to: %s from %s ", session.TutoringSessionID, session.Date, timeString)
		tutorEmail, err := util.GetTutorEmailByID(db, session.TutorID)
		if err != nil {
			fmt.Println("Error getting tutor email: ", err)
		}
		studentEmail, err := util.GetStudentEmailBySessionID(db, session)
		if err != nil {
			fmt.Println("Error getting student email: ", err)
		}
		// Send email
		recipient := []string{tutorEmail}
		err = util.SendEmail(recipient, subject, body)
		if err != nil {
			fmt.Println("Error sending email: ", err)
		}

		// Send email
		recipient = []string{studentEmail}
		err = util.SendEmail(recipient, subject, body)
		if err != nil {
			fmt.Println("Error sending email: ", err)
		}

		// Prepare response
		response := struct {
			TutorSessionID  int
			SessionDate     time.Time
			TimeBlockIDList []int `json:"time_block_id_list"`
		}{
			TutorSessionID:  session.TutoringSessionID,
			SessionDate:     date,
			TimeBlockIDList: session.TimeBlockIDList,
		}

		// Marshal response to JSON
		jsonResponse, err := json.Marshal(response)
		if err != nil {
			fmt.Printf("Error encoding JSON response: %v\n", err)
			http.Error(w, "Error encoding JSON response", http.StatusInternalServerError) // status code 500
			return
		}

		// Write response
		w.Header().Set("Content-Type", "application/json")
		w.WriteHeader(http.StatusOK)
		w.Write(jsonResponse)
	}
}

// Creates pdf file and converts it to bytes before emailing
func GetMasterSchedule(db *sql.DB) http.HandlerFunc {
	return func(w http.ResponseWriter, r *http.Request) {
		// Read JSON from payload
		var payload util.Login
		err := util.DecodeJSONRequestBody(r, &payload)
		if err != nil {
			fmt.Println("Invalid JSON:", err)
			http.Error(w, "Error parsing JSON", http.StatusBadRequest) // status code 400
			return
		}

		filename, err := util.CreateMasterSchedule(db)
		if err != nil {
			fmt.Println("Error creating buffer:", err)
			http.Error(w, "Error creating buffer", http.StatusBadRequest) // status code 400
			return
		}
		_ = filename

		recipient := []string{payload.Email}
		subject := "Macrohard Master Schedule"
		body := "Attached is the master schedule"
		err = util.SendEmailWithAttachment(recipient, subject, body, filename)
		if err != nil {
			fmt.Println("Error sending email:", err)
			http.Error(w, "Error sending email", http.StatusBadRequest) // status code 400
			return
		}

		w.WriteHeader(http.StatusOK)
		// Write the response body
		fmt.Fprintf(w, "Success! This is a 200 OK response.")
	}
}
