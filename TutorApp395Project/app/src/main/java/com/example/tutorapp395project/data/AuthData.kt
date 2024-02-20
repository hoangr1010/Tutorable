package com.example.tutorapp395project.data

import com.example.tutorapp395project.utils.stringToDate
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


data class LoginData(
    var email: String = "",
    var password: String = "",
    var role: String = "student"
)

data class LoginResponse(
    val token: String,
    val user: User
)

data class User(
    var id: String = "",
    var email: String = "",
    var role: String = "",
    var first_name: String = "",
    var last_name: String = "",
    var date_of_birth: String = "",
    var expertise: List<String>? = emptyList(),
    var verified_status: String = "",
    var experience: String  = "",
    var description: String = "",
    var degrees: List<String>? = emptyList(),
    var grade: Int = 0,
    var school: String = "",
)

data class Student(
    val id: String,
    val first_name: String,
    val last_name: String,
    val date_of_birth: Date?,
    val grade: Int,
    val school: String?,
    val email: String
)

data class Tutor(
    val id: String,
    val first_name: String,
    val last_name: String,
    val email: String,
    val expertise: String?,
    val experience: String,
    val verified_status: String,
    val description: String?,
    val degrees: String?,
    val date_of_birth: Date?
)

fun toStudent(user: User): Student {
    return Student(
        id = user.id,
        first_name = user.first_name,
        last_name = user.last_name,
        date_of_birth = stringToDate(user.date_of_birth),
        grade = user.grade,
        school = user.school,
        email = user.email
    )
}

fun toTutor(user: User): Tutor {
    return Tutor(
        id = user.id,
        first_name = user.first_name,
        last_name = user.last_name,
        email = user.email,
        expertise = user.expertise?.joinToString(","),
        experience = user.experience,
        verified_status = user.verified_status,
        description = user.description,
        degrees = user.degrees?.joinToString(","),
        date_of_birth = stringToDate(user.date_of_birth)
    )
}

// FOR REGISTRATION

data class RegisterData (
    val first_name: String = "",
    val last_name: String = "",
    val role: String = "tutor",
    val date_of_birth: String = "",
    val email: String = "",
    val password: String = "",
    val expertise: List<String>? = emptyList(),
    val experience: String = "junior",
    val description: String? = null,
    val degrees: List<String>? = emptyList(),
    val grade: Int = 0,
    val school: String = ""
)

data class RegisterDataTutor(
    val first_name: String = "",
    val last_name: String = "",
    val role: String = "tutor",
    val date_of_birth: String = "",
    val email: String = "",
    val password: String = "",
    val expertise: List<String>? = emptyList(),
    val experience: String = "junior",
    val description: String? = null,
    val degrees: List<String>? = emptyList()
)

data class RegisterDataStudent(
    val first_name: String = "",
    val last_name: String = "",
    val role: String = "student",
    val email: String = "",
    val password: String = "",
    val date_of_birth: String = "",
    val grade: Int = 0,
    val school: String = ""
)

// For development only
val dummyUser = User(
    id = "20",
    email = "huyhoangr1010@gmail.com",
    role = "student",
    first_name = "Hoang",
    last_name = "Daddy",
    date_of_birth = "2002-02-02T00:00:00Z",
    expertise = null,
    verified_status=false.toString(),
    degrees=null,
    grade = 11,
    school = "High School XYZ"
)

val dummyToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9"
