package com.example.tutorapp395project.classes

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.rememberNavController
import com.example.tutorapp395project.viewModel.AuthViewModel
import com.example.tutorapp395project.viewModel.HomeViewModel
import com.example.tutorapp395project.viewModel.StudentViewModel
import com.example.tutorapp395project.viewModel.TutorViewModel
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class NavigationKtTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    // Initialize ViewModels to be used for testing purposes
    private lateinit var authViewModel: AuthViewModel
    private lateinit var studentViewModel: StudentViewModel
    private lateinit var tutorViewModel: TutorViewModel
    private lateinit var homeViewModel: HomeViewModel

    @Before
    fun setUp() {
        authViewModel = AuthViewModel()
        studentViewModel = StudentViewModel()
        tutorViewModel = TutorViewModel()
        homeViewModel = HomeViewModel()


    }

    /*
     * Purpose: Test navigation to the student graph
     */
    @Test
    fun navigateToStudentProfile() {
        composeTestRule.setContent {
            val navController = rememberNavController()
            Navigation(
                navController = navController,
                authViewModel = authViewModel
            )
        }
        composeTestRule.onNodeWithText("date_of_birth").assertExists()
    }

    /*
     * Purpose: Test navigation to the student schedule
     */
    @Test
    fun navigateToStudentSchedule() {
        composeTestRule.setContent {
            val navController = rememberNavController()
            Navigation(
                navController = navController,
                authViewModel = authViewModel
            )
        }
        composeTestRule.onNodeWithText("schedule").performClick()
        composeTestRule.onNodeWithText("session", ignoreCase = true).assertExists()
    }

    /*
     * Purpose: Test navigation to the settings page
     */
    @Test
    fun navigateToSettings() {
        composeTestRule.setContent {
            val navController = rememberNavController()
            Navigation(
                navController = navController,
                authViewModel = authViewModel
            )
        }
        composeTestRule.onNodeWithText("setting").performClick()
        composeTestRule.onNodeWithText("Logout", ignoreCase = true).assertExists()
    }

    /*
     * Purpose: Test navigation to logout of account
     */
    @Test
    fun navigateToLogout() {
        composeTestRule.setContent {
            val navController = rememberNavController()
            Navigation(
                navController = navController,
                authViewModel = authViewModel
            )
        }
        composeTestRule.onNodeWithText("setting").performClick()
        composeTestRule.onNodeWithText("Logout", ignoreCase = true).performClick()
        composeTestRule.onNodeWithText("Login", ignoreCase = true).assertExists()
    }

    @Test
    fun navigateToRegister() {
        composeTestRule.setContent {
            val navController = rememberNavController()
            Navigation(
                navController = navController,
                authViewModel = authViewModel
            )
        }
        composeTestRule.onNodeWithText("setting").performClick()
        composeTestRule.onNodeWithText("Logout", ignoreCase = true).performClick()
        composeTestRule.onNodeWithText("Login", ignoreCase = true).performClick()
        composeTestRule.onNodeWithText("Don't have an Account? Register Here!").performClick()
        composeTestRule.onNodeWithText("Choose Your Role", ignoreCase = true).assertExists()
    }

    @Test
    fun navigateToTutorRegister() {
        composeTestRule.setContent {
            val navController = rememberNavController()
            Navigation(
                navController = navController,
                authViewModel = authViewModel
            )
        }
        composeTestRule.onNodeWithText("setting").performClick()
        composeTestRule.onNodeWithText("Logout", ignoreCase = true).performClick()
        composeTestRule.onNodeWithText("Login", ignoreCase = true).performClick()
        composeTestRule.onNodeWithText("Don't have an Account? Register Here!").performClick()
        composeTestRule.onNodeWithText("Tutor", ignoreCase = true).performClick()
        composeTestRule.onNodeWithText("First Name", ignoreCase = true).assertExists()
    }

    @Test
    fun navigateToStudentRegister() {
        composeTestRule.setContent {
            val navController = rememberNavController()
            Navigation(
                navController = navController,
                authViewModel = authViewModel
            )
        }
        composeTestRule.onNodeWithText("setting").performClick()
        composeTestRule.onNodeWithText("Logout", ignoreCase = true).performClick()
        composeTestRule.onNodeWithText("Login", ignoreCase = true).performClick()
        composeTestRule.onNodeWithText("Don't have an Account? Register Here!").performClick()
        composeTestRule.onNodeWithText("Student", ignoreCase = true).performClick()
        composeTestRule.onNodeWithText("First Name", ignoreCase = true).assertExists()
    }
}