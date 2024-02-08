package com.example.tutorapp395project.model

class LoginRequest(
    val username: String,
    val password: String,
    val role: String
)

class LoginResponse(
    val token: String,
    val role: String
)