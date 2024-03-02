package com.example.tutorapp395project

import android.content.Context
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.test.core.app.ApplicationProvider
import com.example.tutorapp395project.screens.LandingPage
import org.junit.Rule
import org.junit.Test

class ScreenNavigationTest {

    @get:Rule
    val rule = createComposeRule()

    @Test
    fun button_click_navigates_to_next_screen() {
        // Set a flag to track if the button was clicked
        var buttonClicked = false

        // Create a mock NavController
        class MockNavController(context: Context) : NavHostController(context) {
            override fun navigate(
                resId: Int,
                args: android.os.Bundle?,
                navOptions: androidx.navigation.NavOptions?,
                navigatorExtras: androidx.navigation.Navigator.Extras?
            ) {
                // No action needed for testing
            }
        }

        // Obtain a context for testing
        val context = ApplicationProvider.getApplicationContext<Context>()

        // Set content with LandingPage composable and pass a lambda to handle button click
        rule.setContent {
            LandingPage(navController = MockNavController(context)) {
                buttonClicked = true
            }
        }

        // Find and perform click on the LOGIN button
        rule.onNodeWithText("LOGIN").performClick()

        // Check if the button was clicked
        assert(buttonClicked) { "Button click did not work" }
    }
}
