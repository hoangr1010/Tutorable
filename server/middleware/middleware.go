package middleware

/*
import (
	"fmt"
	"net/http"
	"os"

	"github.com/golang-jwt/jwt/v5"
	"github.com/golang-jwt/jwt/v5/request"
	"github.com/macewanCS/w24MacroHard/server/util"
)

var key = []byte(os.Getenv("KEY"))
var loginClaim util.LoginClaim

// only accessible with a valid token
func AuthorizeToken(next http.Handler) http.Handler {
	return http.HandlerFunc(func(w http.ResponseWriter, r *http.Request) {

		// Get token from request
		token, err := request.ParseFromRequest(r, request.OAuth2Extractor, func(token *jwt.Token) (interface{}, error) {
			// since we only use the one private key to sign the tokens,
			// we also only use its public counter part to verify
			return key, nil
		}, request.WithClaims(&loginClaim))

		// If the token is missing or invalid, return error
		if err != nil {
			w.WriteHeader(http.StatusUnauthorized)
			fmt.Fprintln(w, "Invalid token:", err)
			return
		}

		// Token is valid
		fmt.Fprintln(w, "Welcome,", token.Claims.(util.LoginClaim).UserInfo.Email)
		next.ServeHTTP(w, r)
	})
}
*/
