package com.example.tutorapp395project.screen.view

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollTo
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

    /*
     * Purpose: Test if the registration page header is displayed correctly
     */
    @Test
    fun testRegistrationText() {
        composeTestRule.onNodeWithText("Choose Your Role").assertIsDisplayed()
    }

    /*
     * Purpose: Test if the role buttons are displayed correctly
     */
    @Test
    fun testRoleButton() {
        composeTestRule.onNodeWithText("Student").assertIsDisplayed()
        composeTestRule.onNodeWithText("Tutor").assertIsDisplayed()
    }

    /*
     * Purpose: Test if the First Name field for students is displayed correctly
     */
    @Test
    fun testRegistrationFields_StudentFirstName() {
        composeTestRule.onNodeWithText("Student").performClick()
        composeTestRule.onNodeWithText("First Name").assertIsDisplayed()
    }

    /*
     * Purpose: Test if the Last Name field for students is displayed correctly
     */
    @Test
    fun testRegistrationFields_StudentLastName() {
        composeTestRule.onNodeWithText("Tutor").performClick()
        composeTestRule.onNodeWithText("First Name").assertIsDisplayed()
    }

    /*
     * Purpose: Test if the Date of Birth for students field is displayed correctly
     */
    @Test
    fun testRegistrationFields_StudentDOB() {
        composeTestRule.onNodeWithText("Student").performClick()
        composeTestRule.onNodeWithText("Date of Birth (MM/DD/YYYY)").assertIsDisplayed()
    }

    /*
     * Purpose: Test if the Email field for students is displayed correctly
     */
    @Test
    fun testRegistrationFields_StudentEmail() {
        composeTestRule.onNodeWithText("Student").performClick()
        composeTestRule.onNodeWithText("Email").assertIsDisplayed()
    }

    /*
     * Purpose: Test if the Password field for students is displayed correctly
     */
    @Test
    fun testRegistrationFields_StudentPassword() {
        composeTestRule.onNodeWithText("Student").performClick()
        composeTestRule.onNodeWithText("Password").assertIsDisplayed()
    }

    /*
     * Purpose: Test if the Grade field for Students is displayed correctly
     */
    @Test
    fun testRegistrationFields_StudentGrade() {
        composeTestRule.onNodeWithText("Student").performClick()
        composeTestRule.onNodeWithText("Grade").assertIsDisplayed()
    }

    /*
     * Purpose: Test if the School field for students is displayed correctly
     */
    @Test
    fun testRegistrationFields_StudentSchool() {
        composeTestRule.onNodeWithText("Student").performClick()
        composeTestRule.onNodeWithText("School").performScrollTo()
        composeTestRule.onNodeWithText("School").assertIsDisplayed()
    }


    /*
     * Purpose: Test if the registration button is displayed correctly for students
     */
    @Test
    fun testRegistrationButton_Student() {
        composeTestRule.onNodeWithText("Student").performClick()
        composeTestRule.onNodeWithText("Register").assertIsDisplayed()
    }

    /*
     * Purpose: Test if the First Name field for tutors is displayed correctly
     */
    @Test
    fun testRegistrationFields_TutorFirstName() {
        composeTestRule.onNodeWithText("Tutor").performClick()
        composeTestRule.onNodeWithText("First Name").assertIsDisplayed()
    }

    /*
     * Purpose: Test if the Last Name field for tutors is displayed correctly
     */
    @Test
    fun testRegistrationFields_TutorLastName() {
        composeTestRule.onNodeWithText("Tutor").performClick()
        composeTestRule.onNodeWithText("Last Name").assertIsDisplayed()
    }

    /*
     * Purpose: Test if the Date of Birth for tutors field is displayed correctly
     */
    @Test
    fun testRegistrationFields_TutorDOB() {
        composeTestRule.onNodeWithText("Tutor").performClick()
        composeTestRule.onNodeWithText("Date of Birth (MM/DD/YYYY)").assertIsDisplayed()
    }

    /*
     * Purpose: Test if the Email field for tutors is displayed correctly
     */
    @Test
    fun testRegistrationFields_TutorEmail() {
        composeTestRule.onNodeWithText("Tutor").performClick()
        composeTestRule.onNodeWithText("Email").assertIsDisplayed()
    }

    /*
     * Purpose: Test if the Password field for tutors is displayed correctly
     */
    @Test
    fun testRegistrationFields_TutorPassword() {
        composeTestRule.onNodeWithText("Tutor").performClick()
        composeTestRule.onNodeWithText("Password").assertIsDisplayed()
    }

    /*
     * Purpose: Test if the Experience field for tutors is displayed correctly
     */
    @Test
    fun testRegistrationFields_TutorExperience() {
        composeTestRule.onNodeWithText("Tutor").performClick()
        composeTestRule.onNodeWithText("Experience: Fresher").assertIsDisplayed()
    }

    /*
     * Purpose: Test if the Description field for tutors is displayed correctly
     */
    @Test
    fun testRegistrationFields_TutorDescription() {
        composeTestRule.onNodeWithText("Tutor").performClick()
        composeTestRule.onNodeWithText("Description").assertIsDisplayed()
    }

    /*
     * Purpose: Test if the Degree field for tutors is displayed correctly
     */
    @Test
    fun testRegistrationFields_TutorDegree() {
        composeTestRule.onNodeWithText("Tutor").performClick()
        composeTestRule.onNodeWithText("Degree").performScrollTo()
        composeTestRule.onNodeWithText("Degree").assertIsDisplayed()
    }

    /*
     * Purpose: Test if the Expertise field for tutors is displayed correctly
     */
    @Test
    fun testRegistrationFields_TutorExpertise() {
        composeTestRule.onNodeWithText("Tutor").performClick()
        composeTestRule.onNodeWithText("Expertise: Mathematics").performScrollTo()
        composeTestRule.onNodeWithText("Expertise: Mathematics").assertIsDisplayed()
    }

    /*
     * Purpose: Test if the registration button is displayed correctly for tutors
     */
    @Test
    fun testRegistrationButton_Tutor() {
        composeTestRule.onNodeWithText("Tutor").performClick()
        composeTestRule.onNodeWithText("Register").assertIsDisplayed()
    }
}