package com.example.tutorapp395project.classes

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.tutorapp395project.view.LandingPage
import com.example.tutorapp395project.view.LoginPage
import com.example.tutorapp395project.view.RegistrationPage
import com.example.tutorapp395project.view.SettingsPage
import com.example.tutorapp395project.view.StudentProfile
import com.example.tutorapp395project.view.StudentRegistration
import com.example.tutorapp395project.view.StudentSchedule
import com.example.tutorapp395project.view.TutorProfile
import com.example.tutorapp395project.view.TutorRegistration
import com.example.tutorapp395project.view.TutorSchedule

/*
    Purpose: function to navigate between different screens
    Parameters:
        - navController: NavHostController
    Returns: Unit
 */
@Composable
fun Navigation (
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = Screen.LandingPage.route
    ) {
        composable(route = Screen.LandingPage.route) {
            LandingPage(navController = navController, onClick = {})
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
            SettingsPage(navController = navController, route = "student")
        }
    }
}

