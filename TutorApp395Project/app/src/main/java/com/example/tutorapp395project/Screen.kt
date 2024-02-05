package com.example.tutorapp395project

sealed class Screen(val route: String) {
    object LandingPage: Screen("LandingPage")
    object LoginPage: Screen("LoginPage")
    object RegistrationPage: Screen("RegistrationPage")
    object StudentRegistration: Screen("StudentRegistration")
    object TutorRegistration: Screen("TutorRegistration")
    object StudentProfile: Screen("StudentProfile")
    object TutorProfile: Screen("TutorProfile")
    object Settings: Screen("Settings")
}