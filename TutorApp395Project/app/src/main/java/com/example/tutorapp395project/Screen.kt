package com.example.tutorapp395project

sealed class Screen(val route: String) {
    object LandingPage: Screen("LandingPage")
    object LoginPage: Screen("LoginPage")
    object RegisterPage: Screen("RegisterPage")
}