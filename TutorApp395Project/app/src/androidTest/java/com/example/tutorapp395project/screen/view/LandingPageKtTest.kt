package com.example.tutorapp395project.screen.view

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import org.junit.Rule
import org.junit.Test

class LandingPageKtTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun landingPage() {
        val navController = TestNavHostController(ApplicationProvider.getApplicationContext())
        composeTestRule.setContent {
            LandingPage(navController)
        }
        composeTestRule.onNodeWithText("LOGIN").assertExists()
    }

    @Test
    fun loginButton() {
        val navController = TestNavHostController(ApplicationProvider.getApplicationContext())
        composeTestRule.setContent {
            LoginButton(navController, Screen.LoginPage.route)
        }
       composeTestRule.onNodeWithText("LOGIN").assertExists()
    }

    @Test
    fun buttonLayout() {
        val navController = TestNavHostController(ApplicationProvider.getApplicationContext())
        composeTestRule.setContent {
            ButtonLayout(navController)
        }
        composeTestRule.onNodeWithText("LOGIN").assertExists()
    }
}