package com.example.tutorapp395project.classes

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.example.tutorapp395project.screen.view.LandingPage
import com.example.tutorapp395project.screen.view.LoginPage
import com.example.tutorapp395project.screen.MainStudent
import com.example.tutorapp395project.screen.MainTutor
import com.example.tutorapp395project.screen.view.RegistrationPage
import com.example.tutorapp395project.screen.view.ScreenGraph
import com.example.tutorapp395project.screen.view.StudentRegistration
import com.example.tutorapp395project.screen.view.TutorRegistration
import com.example.tutorapp395project.viewModel.AuthViewModel

/*
    Purpose: function to navigate between different screens
    Parameters:
        - navController: NavHostController
    Returns: Unit
 */
@Composable
fun Navigation (
    navController: NavHostController = rememberNavController(),
    authViewModel: AuthViewModel = viewModel()
) {
    NavHost(
        navController = navController,
        startDestination = ScreenGraph.authGraph.route
    ) {

        // AUTH GRAPH
        navigation(
            startDestination = Screen.LandingPage.route,
            route = ScreenGraph.authGraph.route
        ) {

            composable(route = Screen.LandingPage.route) {
                checkUserRoleAndNavigate(navController, authViewModel)
                LandingPage(navController = navController)
            }

            composable(route = Screen.LoginPage.route) {
                checkUserRoleAndNavigate(navController, authViewModel)
                LoginPage(
                    navController = navController,
                    authViewModel = authViewModel
                )
            }

            composable(route = Screen.RegistrationPage.route) {
                checkUserRoleAndNavigate(navController, authViewModel)
                RegistrationPage(navController = navController)
            }

            composable(route = Screen.StudentRegistration.route) {
                checkUserRoleAndNavigate(navController, authViewModel)
                StudentRegistration(navController = navController)
            }

            composable(route = Screen.TutorRegistration.route) {
                checkUserRoleAndNavigate(navController, authViewModel)
                TutorRegistration(navController = navController)
            }
        }

        // STUDENT GRAPH
        navigation(startDestination = "mainStudent", route = "student") {

                composable(route = "mainStudent") {
                    MainStudent(navController = navController, authViewModel = authViewModel)
                }
        }

        // TUTOR GRAPH
        navigation(startDestination = "mainTutor", route = "tutor") {

            composable(route = "mainTutor") {
                MainTutor(navController = navController, authViewModel = authViewModel)
            }

        }
    }
}

/**
 * This function checks the user role and navigates to the appropriate route.
 *
 * @param navController The NavController used to perform navigation actions.
 * @param authViewModel The ViewModel that holds the state of the user.
 *
 * If the user role is "student", the function navigates to the student graph route.
 * If the user role is "tutor", the function navigates to the tutor graph route.
 */
fun checkUserRoleAndNavigate(navController: NavController, authViewModel: AuthViewModel) {
    if (authViewModel.UserState.value.role == "student") {
        navController.navigate(ScreenGraph.studentGraph.route)
    } else if (authViewModel.UserState.value.role == "tutor") {
        navController.navigate(ScreenGraph.tutorGraph.route)
    }
}