// main.go
package main

import (
	"database/sql"
	"fmt"
	"net/http"
	"os"
	"strconv"

	"github.com/joho/godotenv"

	"github.com/go-chi/chi/v5"
	"github.com/go-chi/jwtauth/v5"
	handlers "github.com/macewanCS/w24MacroHard/server/handlers"
)

// Change to env also .gitignore

var db *sql.DB

var tokenAuth *jwtauth.JWTAuth

func initDB() {
	// Connect to the PostgreSQL database
	godotenv.Load()
	DBHost := os.Getenv("DB_HOST")
	DBPort, _ := strconv.Atoi(os.Getenv("DB_PORT"))
	DBUser := os.Getenv("DB_USER")
	DBPassword := os.Getenv("DB_PASSWORD")
	DBName := os.Getenv("DB_NAME")

	connStr := fmt.Sprintf("host=%s port=%d user=%s password=%s dbname=%s sslmode=require",
		DBHost, DBPort, DBUser, DBPassword, DBName)
	var err error
	db, err = sql.Open("postgres", connStr)
	if err != nil {
		panic(err)
	}

	// Check the connection
	err = db.Ping()
	if err != nil {
		panic(err)
	}

	fmt.Println("Connected to the database")

	// Setup token authentication
	tokenAuth = jwtauth.New("HS256", []byte(os.Getenv("KEY")), nil)

	// For debugging
	_, tokenString, _ := tokenAuth.Encode(map[string]interface{}{"user_id": 123})
	fmt.Printf("DEBUG: a sample jwt is %s\n\n", tokenString)
}

func main() {
	addr := ":8080"
	fmt.Printf("Starting server on %v\n", addr)
	http.ListenAndServe(addr, router())
}

func router() http.Handler {
	// Initialize database connection
	initDB()
	r := chi.NewRouter()

	// Protected Routes
	r.Group(func(r chi.Router) {
		// Seek, verify, and validate JWT tokens
		r.Use(jwtauth.Verifier(tokenAuth))

		// Handle valid / invalid tokens
		// Replace with our own later
		r.Use(jwtauth.Authenticator(tokenAuth))

		r.Post("/add_tutor_availabilityp", handlers.AddTutorAvailability(db)) // Done
		r.Get("/get_tutor_availabilityp", handlers.GetTutorAvailability(db))  // This is for a tutor
		r.Get("/get_tutoring_session_listp", handlers.GetTutoringSessionList(db))
		r.Get("/search_tutor_availabilityp", handlers.SearchTutorAvailability(db)) // This is for many
		r.Post("/add_tutoring_sessionp", handlers.AddTutoringSession(db))

	})

	// Public Routes
	r.Group(func(r chi.Router) {
		r.Post("/auth/login", handlers.LoginHandler(db))
		r.Post("/auth/register", handlers.RegisterHandler(db))
		r.Post("/add_tutor_availability", handlers.AddTutorAvailability(db)) // Done
		r.Get("/get_tutor_availability", handlers.GetTutorAvailability(db))  // This is for a tutor
		r.Get("/get_tutoring_session_list", handlers.GetTutoringSessionList(db))
		r.Get("/search_tutor_availability", handlers.SearchTutorAvailability(db)) // This is for many
		r.Post("/add_tutoring_session", handlers.AddTutoringSession(db))

	})

	//r.Post("/add_tutor_availability",  handlers.AddTutorAvailability(db))
	return r
}
