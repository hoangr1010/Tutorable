package com.example.tutorapp395project.screens

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.tutorapp395project.classes.Screen

import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
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

