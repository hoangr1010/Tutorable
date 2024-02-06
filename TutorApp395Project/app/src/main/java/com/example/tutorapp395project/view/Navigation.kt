package com.example.tutorapp395project.view

import androidx.compose.runtime.Composable
import androidx.navigation.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun Navigation (
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = Screen.LandingPage.route
    ) {
        composable(route = Screen.LandingPage.route) {
            LandingPage(navController = navController)
        }
        composable(route = Screen.LoginPage.route) {
            LoginPage(navController = navController)
        }
        composable(route = Screen.RegistrationPage.route) {
            RegistrationPage(navController = navController)
        }
        composable(route = Screen.StudentRegistration.route) {
            StudentRegistration(navController = navController)
        }
        composable(route = Screen.TutorRegistration.route) {
            TutorRegistration(navController = navController)
        }
        composable(route = Screen.StudentSchedule.route) {
            StudentSchedule(navController = navController)
        }
        composable(route = Screen.TutorSchedule.route) {
            TutorSchedule(navController = navController)
        }
        composable(route = Screen.StudentProfile.route) {
            StudentProfile(navController = navController)
        }
        composable(route = Screen.TutorProfile.route) {
            TutorProfile(navController = navController)
        }
        composable(route = Screen.Settings.route) {
            SettingsPage(navController = navController)
        }
    }
}

