package com.example.tutorapp395project.screen.view

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.jupiter.api.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
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
    fun background() {
        composeTestRule.setContent {
            Background()
        }
        composeTestRule.onNodeWithText("Tutor Apple").assertExists()
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