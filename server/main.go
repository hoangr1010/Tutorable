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
	handlers "github.com/macewanCS/w24MacroHard/server/handlers"
)

// Change to env also .gitignore

var db *sql.DB

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
}

func main() {
	// Initialize database connection
	initDB()

	r := chi.NewRouter()

	// Define routes
	r.Post("/auth/login", handlers.LoginHandler(db))
	r.Post("/auth/register", handlers.RegisterHandler(db))

	// Start the server
	http.ListenAndServe(":8080", r)
}
