package com.example.tutorapp395project.screen.tutorView

import androidx.compose.ui.Modifier
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.example.tutorapp395project.R
import com.example.tutorapp395project.data.User
import com.example.tutorapp395project.screen.studentView.StudentProfileColumn
import com.example.tutorapp395project.viewModel.AuthViewModel
import io.mockk.every
import io.mockk.mockk
import org.junit.Before
import org.junit.Rule
import org.junit.jupiter.api.Test

class TutorProfileKtTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    // Initalize ViewModel and User for testing
    private lateinit var authViewModel: AuthViewModel
    private lateinit var user: User

    /*
     * Set up the ViewModel and User for testing
     */
    @Before
    fun setUp() {
        authViewModel = mockk(relaxed = true)
        user = User("1000", first_name = "Jane", last_name = "Doe",
            date_of_birth = "2006-05-02T00:00:00Z", email = "janedoe@gmail.com");
        every { authViewModel.UserState.value } returns user
    }

    /*
     * Purpose: Check if the tutor's profile is displayed correctly
     */
    @Test
    fun tutorProfileColumn() {
        composeTestRule.setContent {
            StudentProfileColumn(image = R.drawable.tutor_headshot, authViewModel = authViewModel,
                modifier = Modifier
            )
        }
        composeTestRule.onNodeWithText("Jane Doe", useUnmergedTree = true).assertExists()
        composeTestRule.onNodeWithText("janedoe@gmail.com", useUnmergedTree = true).assertExists()
    }

    /*
     * Purpose: Check if the tutor's expertise is displayed correctly
     */
    @Test
    fun tutorProfileColumn_displaysCorrectExpertise() {
        val expectedExpertise = listOf("Mathematics")
        val expertiseString = "Mathematics"
        user = User("1000", first_name = "Jane", last_name = "Doe",
                    date_of_birth = "2006-05-02T00:00:00Z", email = "janedoe@gmail.com",
                    expertise = expectedExpertise)
        every { authViewModel.UserState.value } returns user

        composeTestRule.setContent {
            TutorProfileColumn(image = R.drawable.tutor_headshot, authViewModel = authViewModel,
                modifier = Modifier
            )
        }
        composeTestRule.onNodeWithText(expertiseString, useUnmergedTree = true).assertExists()
    }

    /*
     * Purpose: Check if the tutor's experience is displayed correctly
     */
    @Test
    fun tutorProfileColumn_displaysCorrectExperience() {
        val expectedExperience = "5 years"
        user = User("1000", first_name = "Jane", last_name = "Doe",
            date_of_birth = "2006-05-02T00:00:00Z", email = "janedoe@gmail.com", experience = expectedExperience)
        every { authViewModel.UserState.value } returns user

        composeTestRule.setContent {
            TutorProfileColumn(image = R.drawable.tutor_headshot, authViewModel = authViewModel,
                modifier = Modifier
            )
        }
        composeTestRule.onNodeWithText(expectedExperience, useUnmergedTree = true).assertExists()
    }

    /*
     * Purpose: Check if the tutor's degrees are displayed correctly
     */
    @Test
    fun tutorProfileColumn_displaysCorrectDegrees() {
        val expectedDegrees = listOf("Bachelors Of Science")
        val degreesString = "Bachelors Of Science"
        user = User("1000", first_name = "Jane", last_name = "Doe",
            date_of_birth = "2006-05-02T00:00:00Z", email = "janedoe@gmail.com", degrees = expectedDegrees)
        every { authViewModel.UserState.value } returns user

        composeTestRule.setContent {
            TutorProfileColumn(image = R.drawable.tutor_headshot, authViewModel = authViewModel,
                modifier = Modifier
            )
        }
        composeTestRule.onNodeWithText(degreesString, useUnmergedTree = true).assertExists()
    }

    /*
     * Purpose: Check if the tutor's verified status is displayed correctly
     */
    @Test
    fun tutorProfileColumn_displaysCorrectVerifiedStatus() {
        val expectedVerifiedStatus = "true"
        user = User("1000", first_name = "Jane", last_name = "Doe",
            date_of_birth = "2006-05-02T00:00:00Z", email = "janedoe@gmail.com", verified_status = expectedVerifiedStatus)
        every { authViewModel.UserState.value } returns user

        composeTestRule.setContent {
            TutorProfileColumn(image = R.drawable.tutor_headshot, authViewModel = authViewModel,
                modifier = Modifier
            )
        }
        composeTestRule.onNodeWithText(expectedVerifiedStatus, useUnmergedTree = true).assertExists()
    }

    /*
     * Purpose: Check if the tutor's description is displayed correctly
     */
    @Test
    fun tutorProfileColumn_displaysCorrectDescription() {
        val expectedDescription = "Experienced tutor with a passion for teaching Mathematics."
        user = User(
            "1000",
            first_name = "Jane",
            last_name = "Doe",
            date_of_birth = "2006-05-02T00:00:00Z",
            email = "janedoe@gmail.com",
            description = expectedDescription
        )
        every { authViewModel.UserState.value } returns user

        composeTestRule.setContent {
            TutorProfileColumn(
                image = R.drawable.tutor_headshot, authViewModel = authViewModel,
                modifier = Modifier
            )
        }
        composeTestRule.onNodeWithText(expectedDescription, useUnmergedTree = true).assertExists()
    }
}