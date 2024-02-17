package com.example.tutorapp395project.model

/*
    Purpose: data class to hold the login request to be sent to the server
    Parameters:
        - username: String
        - password: String
        - role: String
    Returns: Unit
 */
<<<<<<< HEAD
class LoginRequest(
=======
data class LoginRequest(
>>>>>>> fddd448e1c9c85c4e4966bce1170441f9d0360ce
    val username: String,
    val password: String,
    val role: String
)

/*
    Purpose: data class to hold the login response from the server
    Parameters:
        - token: String
    Returns: Unit
 */
<<<<<<< HEAD
class LoginResponse(
    val token: String
=======
data class LoginStudentResponse(
    val token: String,
    val id: String,
    val first_name: String,
    val last_name: String,
    val role: String,
    val email: String,
    val password: String,
    val date_of_birth: String,
    val grade: Int,
    val school: String
)

data class LoginTutorResponse(
    val token: String,
    val id: String,
    val first_name: String,
    val last_name: String,
    val role: String,
    val email: String,
    val password: String,
    val date_of_birth: String,
    val expertise: Array<String>,
    val experience: String,
    val description: String,
    val degrees: Array<String>
    // val picturePate: String
    >>>>>>> fddd448e1c9c85c4e4966bce1170441f9d0360ce
