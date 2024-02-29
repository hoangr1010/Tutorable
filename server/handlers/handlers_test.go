package handlers_test

import (
	"net/http"
	"net/http/httptest"
	"testing"

	"github.com/macewanCS/w24MacroHard/server/handlers"
)

// Testing the HelloHandler
// This is a test to see if handlers.go test would work, but pretty much useless in our situation
func TestHelloHandler(t *testing.T) {
	req, err := http.NewRequest("GET", "/", nil)

	if err != nil {
		t.Fatal(err)
	}

	rr := httptest.NewRecorder()
	handler := http.HandlerFunc(handlers.HelloHandler)

	handler.ServeHTTP(rr, req)

	if status := rr.Code; status != http.StatusOK {
		t.Errorf("handler returned wrong status code: got %v want %v",
			status, http.StatusOK)
	}

	expected := "Hello, Chi!"
	if rr.Body.String() != expected {
		t.Errorf("handler returned unexpected body: got %v want %v",
			rr.Body.String(), expected)
	}
}


