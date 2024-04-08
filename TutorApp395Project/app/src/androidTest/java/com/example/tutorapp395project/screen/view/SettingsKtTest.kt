package com.example.tutorapp395project.screen.view

import androidx.compose.ui.Modifier
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import com.example.tutorapp395project.viewModel.AuthViewModel
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class SettingsKtTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    // Initialize ViewModels for testing purposes
    private lateinit var navController: TestNavHostController
    private lateinit var authViewModel: AuthViewModel

    /*
     * Purpose: Set up the SettingsColumn composable for testing
     */
    @Before
    fun setUp() {
        navController = TestNavHostController(ApplicationProvider.getApplicationContext())
        authViewModel = AuthViewModel()
        composeTestRule.setContent {
            SettingsColumn(navController, authViewModel, Modifier)
        }
    }

    /*
     * Purpose: Test if the logout button exists
     */
    @Test
    fun settingsColumn_logoutButtonExists() {
        composeTestRule.onNodeWithText("Logout").assertIsDisplayed()
    }
}