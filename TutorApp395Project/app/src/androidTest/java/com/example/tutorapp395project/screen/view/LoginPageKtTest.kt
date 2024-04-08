package com.example.tutorapp395project.screen.view

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.rememberNavController
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LoginPageKtTest {

    @get:Rule
    val composeTestRule = createComposeRule()
    //var activityRule: ActivityScenarioRule<MainActivity> =
    //    ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun setUp() {
        composeTestRule.setContent {
            val navController = rememberNavController()
            LoginPage(navController = navController)
        }
    }
    /*
     * Purpose: Test if the LoginPage composable is displayed correctly
     */
    @Test
    fun testLoginPageDisplayed() {
        composeTestRule.onNodeWithText("Login").assertExists()
    }

    /*
     * Purpose: Test if the fields are displayed correctly
     */
    @Test
    fun testFieldsDisplayed() {
        composeTestRule.onNodeWithText("Email").assertExists()
        composeTestRule.onNodeWithText("Password").assertExists()
    }
    /*
     * Purpose: Test if the login button is displayed correctly
     */
    @Test
    fun testLoginButtonDisplayed() {
        composeTestRule.onNodeWithText("Login").assertExists()
    }

    /*
     * Purpose: Test if the register text is displayed correctly
     */
    @Test
    fun testRegisterTextDisplayed() {
        composeTestRule.onNodeWithText("Don't have an Account? Register Here!").assertExists()
    }

    /*
     * Purpose: Test if the email label is displayed correctly
     */
    @Test
    fun testEmailLabelDisplayed() {
        composeTestRule.onNodeWithText("Email").assertExists()
    }

    /*
     * Purpose: Test if the password label is displayed correctly
     */
    @Test
    fun testPasswordLabelDisplayed() {
        composeTestRule.onNodeWithText("Password").assertExists()
    }

    /*
     * Purpose: Test if the role options are displayed correctly
     */
    @Test
    fun testRoleOptionsDisplayed() {
        composeTestRule.onNodeWithText("student").performClick()
        composeTestRule.onNodeWithText("tutor").assertExists()
    }

    /*
     * Purpose: Test if the logo image is displayed correctly
     */
    @Test
    fun testLogoImageDisplayed() {
        composeTestRule.onNodeWithContentDescription("tutorapple-logo").assertExists()
    }
}