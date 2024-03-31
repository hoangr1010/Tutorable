module github.com/macewanCS/w24MacroHard/server

go 1.21.6

replace github.com/macewanCS/w24MacroHard/server/handlers => ./handlers

replace github.com/macewanCS/w24MacroHard/server/middleware => ./middleware

replace github.com/macewanCS/w24MacroHard/server/mock => ./mock


require github.com/go-chi/chi/v5 v5.0.11

require (
	github.com/DATA-DOG/go-sqlmock v1.5.2 // indirect
	github.com/decred/dcrd/dcrec/secp256k1/v4 v4.2.0 // indirect
	github.com/go-chi/jwtauth/v5 v5.3.0 // indirect
	github.com/goccy/go-json v0.10.2 // indirect
	github.com/jinzhu/gorm v1.9.16 // indirect
	github.com/jinzhu/inflection v1.0.0 // indirect
	github.com/jinzhu/now v1.1.5 // indirect
	github.com/joho/godotenv v1.5.1 // indirect
	github.com/lestrrat-go/blackmagic v1.0.2 // indirect
	github.com/lestrrat-go/httpcc v1.0.1 // indirect
	github.com/lestrrat-go/httprc v1.0.4 // indirect
	github.com/lestrrat-go/iter v1.0.2 // indirect
	github.com/lestrrat-go/jwx/v2 v2.0.17 // indirect
	github.com/lestrrat-go/option v1.0.1 // indirect
	github.com/segmentio/asm v1.2.0 // indirect
	golang.org/x/crypto v0.15.0 // indirect
	golang.org/x/sys v0.14.0 // indirect
	gorm.io/gorm v1.25.9 // indirect
)
