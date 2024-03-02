package com.example.tutorapp395project.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Timestamp
import java.time.LocalTime
import java.util.Date

//@Entity(tableName = "tutor")
//data class tutorTable (
//    @PrimaryKey(autoGenerate = true)
//    val tutor_id: Int,
//    val name: String,
//    val email: String,
//    val password: String,
//    val expertise: Array<String>,
//    val experience: TutorLevel,
//    val verified_status: Boolean,
//    val description: String,
//    val degree: Array<String>,
//    //val date_of_birth:
//)
//
//@Entity(tableName = "student")
//data class studentTable (
//    @PrimaryKey(autoGenerate = true)
//    val student_id: Int,
//    val name: String,
//    //val date_of_birth:
//    val grade_level: Grade,
//    val school: String,
//    val email: String,
//    val password: String
//)

// User: Database
@Entity(tableName = "user")
data class userTable (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val first_name: String,
    val last_name: String,
    val date_of_birth: Date,
    val grade_level: Int,
    val education: String,
    val email: String,
    val password: String,
    val role: String, // To check if student or tutor
    val expertise: Array<String>,
    val experience: Array<String>,
    val description: String,
    val school: String,
)

@Entity(tableName = "session")
data class tutor_session (
    @PrimaryKey(autoGenerate = true)
    val session_id: Int,
    val tutor_id: Int,
    val student_id: Int,
    val name: String,
    val description: String,
    val subject: String,
    val grade: Int,
    val timeBlocks: Array<Int>,
    val status: String
)

@Entity(tableName = "availability")
data class tutor_availability (
    @PrimaryKey(autoGenerate = true)
    val availability_id: Int,
    val tutor_id: Int,
    val start_time: Timestamp,
    val stop_time: Timestamp
)

@Entity(tableName = "Timestamp")
data class Timestamp (
    @PrimaryKey(autoGenerate = true)
    val timestamp_id: Int,
    val startTime: LocalTime,
    val stopTime: LocalTime,
)
