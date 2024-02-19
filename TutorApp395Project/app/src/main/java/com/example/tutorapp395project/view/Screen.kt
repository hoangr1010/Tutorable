package com.example.tutorapp395project.view

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

}

sealed class ScreenGraph(val route: String) {
    object authGraph: ScreenGraph("auth")
    object studentGraph: ScreenGraph("student")
    object tutorGraph: ScreenGraph("tutor")
}

