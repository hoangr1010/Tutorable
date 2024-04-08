package com.example.tutorapp395project.screen.studentView

import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.example.tutorapp395project.R
import com.example.tutorapp395project.viewModel.AuthViewModel
import com.example.tutorapp395project.viewModel.StudentViewModel
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class TutorListKtTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    // Initalize ViewModels to be used for testing
    private lateinit var authViewModel: AuthViewModel
    private lateinit var studentViewModel: StudentViewModel

    /*
     * Purpose: Set up the ViewModel for testing and set up the tutor card to be tested
     */
    @Before
    fun setUp() {
        authViewModel = AuthViewModel()
        studentViewModel = StudentViewModel(authViewModel = authViewModel)

        composeTestRule.setContent {
            TutorList(modifier = Modifier, studentViewModel = studentViewModel)

            val name = "Roronoa Zoro"
            val subject = "Geography"
            TutorCard(
                painter = painterResource(id = R.drawable.image2),
                name = name,
                subject = subject,
                modifier = Modifier,
                onClick = {}
            )
        }
    }

    /*
     * Purpose: Test the tutor card to ensure that the name and subject are displayed
     */
    @Test
    fun testTutorCard() {
        composeTestRule.onNodeWithText("Roronoa Zoro", useUnmergedTree = false).assertIsDisplayed()
        composeTestRule.onNodeWithText("Geography", useUnmergedTree = false).assertIsDisplayed()
    }

    /*
     * Purpose: Test the tutor card to ensure that it is displayed properly
     */
    @Test
    fun testTutorCardDisplayedCorrectly() {
        val name = "Roronoa Zoro"
        val subject = "Geography"

        composeTestRule.onNodeWithText(name).assertIsDisplayed()
        composeTestRule.onNodeWithText(subject).assertIsDisplayed()
    }

    /*
     * Purpose: Test the tutor card to not be displayed when tutorCard is empty
     */
    @Test
    fun testTutorCard_NotDisplayedWhenNameIsEmpty() {
        val name = ""
        composeTestRule.onNodeWithText(name).assertDoesNotExist()
    }
}
