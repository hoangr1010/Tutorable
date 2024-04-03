package com.example.tutorapp395project.screen.studentView

import androidx.compose.ui.Modifier
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import com.example.tutorapp395project.R
import com.example.tutorapp395project.data.User
import com.example.tutorapp395project.viewModel.AuthViewModel
import io.mockk.every
import io.mockk.mockk
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class StudentProfileKtTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    // Initialize ViewModel and users
    private lateinit var authViewModel: AuthViewModel
    private lateinit var user: User

    /**
     * Set up the ViewModel and user before each test
     */
    @Before
    fun setUp() {
        authViewModel = mockk(relaxed = true)
        user = User("699", first_name = "John", last_name = "Doe",
            date_of_birth = "2002-02-02T00:00:00Z", grade = 12 , school = "High School",
            email = "johndoe@gmail.com");
        every { authViewModel.UserState.value } returns user
    }

    /**
     * Purpose: Test the StudentProfileColumn to see if it exists
     */
    @Test
    fun studentProfileColumn() {
        composeTestRule.setContent {
            StudentProfileColumn(image = R.drawable.student_id_photo, authViewModel = authViewModel,
                                 modifier = Modifier)
        }
        composeTestRule.onNodeWithText("John Doe", useUnmergedTree = true).assertExists()
        composeTestRule.onNodeWithText("12", useUnmergedTree = true).assertExists()
        composeTestRule.onNodeWithText("High School", useUnmergedTree = true).assertExists()
        composeTestRule.onNodeWithText("johndoe@gmail.com", useUnmergedTree = true).assertExists()
    }

    /**
     * Purpose: Test the ProfileName to see if it exists
     */
    @Test
    fun profileName() {
        composeTestRule.setContent {
            ProfileName(name = "John Doe")
        }
        composeTestRule.onNodeWithText("John Doe", useUnmergedTree = true).assertExists()
    }

    /*
     * Purpose: Test the ProfileField to see if it exists
     */
    @Test
    fun profileField() {
        composeTestRule.setContent {
            ProfileField("Email", "johndoe@gmail.com")
        }
        composeTestRule.onNodeWithText("Email", useUnmergedTree = true).assertExists()
        composeTestRule.onNodeWithText("johndoe@gmail.com", useUnmergedTree = true).assertExists()
    }

/*
     * Purpose: Test to see if the profilePic exists
     */
    @Test
    fun profilePic_exists() {
        composeTestRule.setContent {
            ProfilePic(image = R.drawable.student_id_photo)
        }
        composeTestRule.onNodeWithContentDescription("image description", useUnmergedTree = true).assertExists()
    }

    /*
     * Purpose: test to see profileField displays correct title and value
     */
    @Test
    fun profileField_displaysCorrectTitleAndValue() {
        val expectedTitle = "Test Title"
        val expectedValue = "Test Value"

        composeTestRule.setContent {
            ProfileField(title = expectedTitle, value = expectedValue)
        }

        composeTestRule.onNodeWithText(expectedTitle, useUnmergedTree = true).assertExists()
        composeTestRule.onNodeWithText(expectedValue, useUnmergedTree = true).assertExists()
    }

    /*
     * Purpose: test to see if the profileData is all displayed correctly
     */
    @Test
    fun studentProfileColumn_displaysCorrectData() {
        val expectedFirstName = "John"
        val expectedLastName = "Doe"
        val expectedGrade = "12"
        val expectedSchool = "High School"
        val expectedEmail = "johndoe@gmail.com"

        composeTestRule.setContent {
            StudentProfileColumn(image = R.drawable.student_id_photo, authViewModel = authViewModel, modifier = Modifier)
        }

        composeTestRule.onNodeWithText(expectedFirstName, useUnmergedTree = true).assertExists()
        composeTestRule.onNodeWithText(expectedLastName, useUnmergedTree = true).assertExists()
        composeTestRule.onNodeWithText(expectedGrade, useUnmergedTree = true).assertExists()
        composeTestRule.onNodeWithText(expectedSchool, useUnmergedTree = true).assertExists()
        composeTestRule.onNodeWithText(expectedEmail, useUnmergedTree = true).assertExists()
    }
}