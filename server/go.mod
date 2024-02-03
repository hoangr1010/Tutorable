module github.com/macewanCS/w24MacroHard/server

go 1.21.6

replace github.com/macewanCS/w24MacroHard/server/handlers => ./handlers

require github.com/go-chi/chi/v5 v5.0.11

require github.com/joho/godotenv v1.5.1 // indirect
