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
        // Go to the landing page
        composable(route = Screen.LandingPage.route) {
            LandingPage(navController = navController)
        }
        // Go to the login page
        composable(route = Screen.LoginPage.route) {
            LoginPage(navController = navController)
        }
        // Go to the registration page
        composable(route = Screen.RegistrationPage.route) {
            RegistrationPage(navController = navController)
        }
        // Go to the student registration page
        composable(route = Screen.StudentRegistration.route) {
            StudentRegistration(navController = navController)
        }
        // Go to the tutor registration page
        composable(route = Screen.TutorRegistration.route) {
            TutorRegistration(navController = navController)
        }
        // Go to the student schedule page
        composable(route = Screen.StudentSchedule.route) {
            StudentSchedule(navController = navController)
        }
        // Go to the tutor schedule page
        composable(route = Screen.TutorSchedule.route) {
            TutorSchedule(navController = navController)
        }
        // Go to the student profile page
        composable(route = Screen.StudentProfile.route) {
            StudentProfile(navController = navController)
        }
        // Go to the tutor profile page
        composable(route = Screen.TutorProfile.route) {
            TutorProfile(navController = navController)
        }
        // Go to the settings page
        composable(route = Screen.Settings.route) {
            SettingsPage(navController = navController, route = "student")
        }
    }
}

