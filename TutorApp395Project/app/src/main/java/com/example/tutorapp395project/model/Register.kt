package com.example.tutorapp395project.model

data class TutorRegister (
    val firstName: String = "",
    val lastName: String = "",
    val role: String = "tutor",
    val dateOfBirth: String = "",
    val email: String = "",
    val password: String = "",
    val expertise: String = "",
    val experience: String = "",
    val description: String = "",
    val degrees: String = "",
) {
}

data class StudentRegister (
    val firstName: String = "",
    val lastName: String = "",
    val role: String = "student",
    val email: String = "",
    val password: String = "",
    val dateOfBirth: String = "",
    val grade: Int = 0,
    val subjects: String = "",
) {
}