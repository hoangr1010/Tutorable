package com.example.tutorapp395project

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.*
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun Navigation (
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = Screen.LandingPage.route
    ) {
        composable(Screen.LandingPage.route) {
            LandingPage()
        }
    }
}