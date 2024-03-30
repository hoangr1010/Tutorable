package mock

import (
  "os"
)

type DatabaseConfig struct {
    Host      string
    Port      string
    User      string
    Password  string
    Name      string
    UseMock   bool
}

type MockDBConfig struct {
  UseMock bool
}

func LoadDatabseConfig() *DatabaseConfig {
  dbConfig := &DatabaseConfig{
        Host:     os.Getenv("DB_HOST"),
        Port:     os.Getenv("DB_PORT"),
        User:     os.Getenv("DB_USER"),
        Password: os.Getenv("DB_PASSWORD"),
        Name:     os.Getenv("DB_NAME"),
        UseMock:  false, // Default to false
  }

  if os.Getenv("USE_MOCK_DB") == "true" {
       dbConfig.UseMock = true
  }

  return dbConfig
   
}

func LoadMockDBConfig() *MockDBConfig {
  useMock := os.Getenv("USE_MOCK_DB") == "true"

  return &MockDBConfig{
    UseMock: useMock,
  }
}

