package com.example.tutorapp395project.screen.view

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.navigation.compose.rememberNavController
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import com.example.tutorapp395project.viewModel.AuthViewModel
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class RegistrationPageKtTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var navController: TestNavHostController
    private lateinit var authViewModel: AuthViewModel

    @Before
    fun setUp() {
        navController = TestNavHostController(ApplicationProvider.getApplicationContext())
        authViewModel = AuthViewModel()

        //navController.setGraph(R.navigation.nav_graph)

        composeTestRule.setContent {
            val navController = rememberNavController()
            RegistrationPage(navController, authViewModel)
        }
    }

    @Test
    fun testRegistrationText() {
        composeTestRule.onNodeWithText("Choose Your Role").assertIsDisplayed()
    }

    @Test
    fun testRoleButton() {
        composeTestRule.onNodeWithText("Student").assertIsDisplayed()
        composeTestRule.onNodeWithText("Tutor").assertIsDisplayed()
    }

    @Test
    fun testRegistrationFields() {
        navController.navigate(ScreenGraph.studentGraph.route)
        composeTestRule.onNodeWithText("First Name").assertIsDisplayed()
    }

    @Test
    fun testRegistrationPage() {
        composeTestRule.onNodeWithText("Register").assertIsDisplayed()
    }
}