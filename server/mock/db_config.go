package mock

import (
	"os"
)

// Enum types
type Grade string

const (
	Grade1  Grade = "1"
	Grade2  Grade = "2"
	Grade3  Grade = "3"
	Grade4  Grade = "4"
	Grade5  Grade = "5"
	Grade6  Grade = "6"
	Grade7  Grade = "7"
	Grade8  Grade = "8"
	Grade9  Grade = "9"
	Grade10 Grade = "10"
	Grade11 Grade = "11"
	Grade12 Grade = "12"
)

type TutorLevel string

const (
	Fresher TutorLevel = "fresher"
	Junior  TutorLevel = "junior"
	Senior  TutorLevel = "senior"
)

type TutoringSessionStatus string

const (
	Scheduled TutoringSessionStatus = "scheduled"
	Cancelled TutoringSessionStatus = "cancelled"
	Completed TutoringSessionStatus = "completed"
)

type TutoringSubject string

const (
	Mathematics     TutoringSubject = "mathematics"
	English         TutoringSubject = "english"
	Physics         TutoringSubject = "physics"
	Chemistry       TutoringSubject = "chemistry"
	Biology         TutoringSubject = "biology"
	History         TutoringSubject = "history"
	Geography       TutoringSubject = "geography"
	BusinessStudies TutoringSubject = "business studies"
	ComputerScience TutoringSubject = "computer science"
	Literature      TutoringSubject = "literature"
)

// Table structures
type Tutor struct {
	TutorID        int
	Name           string
	Email          string
	Password       string
	Expertise      []TutoringSubject
	Experience     TutorLevel
	VerifiedStatus bool
	Description    string
	Degree         []string
	DateOfBirth    string
}

type Student struct {
	StudentID   int
	Name        string
	DateOfBirth string
	GradeLevel  Grade
	School      string
	Email       string
	Password    string
}

type TutoringSession struct {
	TutorSessionID  int
	TutorID         int
	StudentID       int
	Name            string
	Description     string
	Subject         TutoringSubject
	Grade           Grade
	Date            string
	TimeBlockIDList []int
	Status          TutoringSessionStatus
}

type TutorAvailability struct {
	AvailabilityID int
	TutorID        int
	Date           string
	TimeBlockID    int
}

type TimeBlock struct {
	TimeBlockID int
	StartTime   string
	EndTime     string
}

type Administrator struct {
	AdminID  int
	Name     string
	Role     string
	Email    string
	Password string
}

type DatabaseConfig struct {
	Host     string
	Port     string
	User     string
	Password string
	Name     string
	UseMock  bool
}

type MockDBConfig struct {
	UseMock bool
}

func LoadDatabaseConfig() *DatabaseConfig {
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
