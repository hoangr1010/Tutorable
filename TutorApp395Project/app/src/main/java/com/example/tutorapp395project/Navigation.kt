package com.example.tutorapp395project

import androidx.compose.runtime.Composable
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
    }
}

/*
Ignore this for now this was a failed attempt but kept in case needed for later use
        composable("settings") {
            val viewModel = it.sharedViewModel<TutorAppViewModel>(navController)
        }
        navigation(
            startDestination = "LandingPage",
            route = "login"
        ) {
            composable("LoginPage") {
                val viewModel = it.sharedViewModel<TutorAppViewModel>(navController)
            }
            composable("RegisterPage") {
                val viewModel = it.sharedViewModel<TutorAppViewModel>(navController)
            }
        }
        navigation(
            startDestination = "StudentSchedule",
            route = "student"
        ) {
        composable("StudentSchedule") {
            val viewModel = it.sharedViewModel<TutorAppViewModel>(navController)
        }
        composable("StudentProfile") {
            val viewModel = it.sharedViewModel<TutorAppViewModel>(navController)
        }
    }
        navigation(
            startDestination = "TutorSchedule",
            route = "tutor"
        ) {
            composable("TutorSchedule") {
                val viewModel = it.sharedViewModel<TutorAppViewModel>(navController)
            }
            composable("TutorProfile") {
                val viewModel = it.sharedViewModel<TutorAppViewModel>(navController)
            }
        }
    }
}

@Composable
inline fun <reified T : ViewModel> NavBackStackEntry.sharedViewModel(navController: NavController): T {
    val navGraphRoute = destination.parent?.route ?: return viewModel()
    val parentEntry = remember(this) {
        navController.getBackStackEntry(navGraphRoute)
    }
    return viewModel(parentEntry)
}
 */
