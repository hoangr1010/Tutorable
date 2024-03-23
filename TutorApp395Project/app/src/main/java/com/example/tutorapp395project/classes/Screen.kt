package com.example.tutorapp395project.classes

sealed class Screen(val route: String) {
    object LandingPage: Screen("LandingPage")
    object LoginPage: Screen("LoginPage")
    object RegistrationPage: Screen("RegistrationPage")
}

sealed class ScreenGraph(val route: String) {
    object authGraph: ScreenGraph("authGraph")
    object studentGraph: ScreenGraph("studentGraph")
    object tutorGraph: ScreenGraph("tutorGraph")
}

