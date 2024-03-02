module github.com/macewanCS/w24MacroHard/server/handlers

go 1.21.6

replace github.com/macewanCS/w24MacroHard/server/util => ../util

require (
	github.com/go-chi/jwtauth v1.2.0
	github.com/lib/pq v1.10.9
	github.com/macewanCS/w24MacroHard/server/util v0.0.0-00010101000000-000000000000
)

require (
	github.com/goccy/go-json v0.10.2 // indirect
	github.com/golang-jwt/jwt/v5 v5.2.0 // indirect
	github.com/lestrrat-go/backoff/v2 v2.0.7 // indirect
	github.com/lestrrat-go/httpcc v1.0.1 // indirect
	github.com/lestrrat-go/iter v1.0.2 // indirect
	github.com/lestrrat-go/jwx v1.1.0 // indirect
	github.com/lestrrat-go/option v1.0.1 // indirect
	github.com/pkg/errors v0.9.1 // indirect
	github.com/stretchr/testify v1.8.4 // indirect
	golang.org/x/crypto v0.18.0 // indirect
)
