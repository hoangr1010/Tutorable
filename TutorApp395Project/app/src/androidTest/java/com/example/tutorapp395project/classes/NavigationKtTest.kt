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

    /*
    * Purpose: Test navigation to the student graph
     */
    @Test
    fun testNavigation_studentGraph() {
        // Create a TestNavHostController
        val navController = TestNavHostController(ApplicationProvider.getApplicationContext()).apply {
            navigatorProvider.addNavigator(ComposeNavigator())
        }

        InstrumentationRegistry.getInstrumentation().runOnMainSync {
            navController.setGraph(R.navigation.mobile_navigation)
        }
        composeTestRule.setContent {
            Navigation(navController)
        }

        navController.navigate(ScreenGraph.studentGraph.route)

        Assert.assertEquals(ScreenGraph.studentGraph.route, navController.currentDestination?.route)
    }

    /*
    * Purpose: Test navigation to the tutor graph
     */
    @Test
    fun testNavigation_tutorGraph() {
        // Create a TestNavHostController
        val navController = TestNavHostController(ApplicationProvider.getApplicationContext()).apply {
            navigatorProvider.addNavigator(ComposeNavigator())
        }

        InstrumentationRegistry.getInstrumentation().runOnMainSync {
            navController.setGraph(R.navigation.mobile_navigation)
        }
        composeTestRule.setContent {
            Navigation(navController)
        }

        navController.navigate(ScreenGraph.tutorGraph.route)

        Assert.assertEquals(ScreenGraph.tutorGraph.route, navController.currentDestination?.route)
    }
}