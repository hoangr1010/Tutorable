package com.example.tutorapp395project.classes

sealed class Screen(val route: String) {
    object LandingPage: Screen("LandingPage")
    object LoginPage: Screen("LoginPage")
    object RegistrationPage: Screen("RegistrationPage")
    object StudentRegistration: Screen("StudentRegistration")
    object TutorRegistration: Screen("TutorRegistration")
    object StudentSchedule: Screen("StudentSchedule")
    object TutorSchedule: Screen("TutorSchedule")
    object StudentProfile: Screen("StudentProfile")
    object TutorProfile: Screen("TutorProfile")
    object Settings: Screen("Settings")

    fun withArgs(vararg args: String): String {
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }
}

