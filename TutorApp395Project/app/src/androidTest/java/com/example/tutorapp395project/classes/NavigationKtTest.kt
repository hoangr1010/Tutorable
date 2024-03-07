package com.example.tutorapp395project.classes

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.platform.app.InstrumentationRegistry
import com.example.tutorapp395project.R
import org.junit.Assert
import org.junit.Rule
import org.junit.Test

class NavigationKtTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testNavigation() {
        // Create a TestNavHostController
        val navController = TestNavHostController(ApplicationProvider.getApplicationContext()).apply {
            navigatorProvider.addNavigator(ComposeNavigator())
        }


        InstrumentationRegistry.getInstrumentation().runOnMainSync {
            // Set the graph to the NavHostController
            navController.setGraph(R.navigation.mobile_navigation)
        }

        // Create a scenario with your NavHost
        composeTestRule.setContent {
            Navigation(navController)
        }

        // Perform navigation action
        navController.navigate(ScreenGraph.studentGraph.route)

        // Assert that the current destination is the student graph route
        Assert.assertEquals(ScreenGraph.studentGraph.route, navController.currentDestination?.route)
    }
}