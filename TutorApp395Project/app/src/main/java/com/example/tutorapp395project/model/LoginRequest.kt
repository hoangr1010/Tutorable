package com.example.tutorapp395project.model

/*
    Purpose: data class to hold the login request to be sent to the server
    Parameters:
        - username: String
        - password: String
        - role: String
    Returns: Unit
 */
class LoginRequest(
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
class LoginResponse(
    val token: String
)