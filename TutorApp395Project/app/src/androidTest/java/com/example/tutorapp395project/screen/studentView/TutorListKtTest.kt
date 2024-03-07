package com.example.tutorapp395project.screen.studentView

import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.example.tutorapp395project.R
import com.example.tutorapp395project.viewModel.AuthViewModel
import com.example.tutorapp395project.viewModel.StudentViewModel
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class TutorListKtTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var authViewModel: AuthViewModel
    private lateinit var student: StudentViewModel


    @Before
    fun setUp() {
        authViewModel = AuthViewModel()
        student = StudentViewModel(authViewModel = authViewModel)

        composeTestRule.setContent {
            TutorList(modifier = Modifier, studentViewModel = student)

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

    @After
    fun tearDown() {
    }

    @Test
    fun testTutorList() {
        composeTestRule.onNodeWithText("Say hi to your tutor").assertIsDisplayed()
    }

    @Test
    fun testTutorCard() {
        composeTestRule.onNodeWithText("Roronoa Zoro", useUnmergedTree = false).assertIsDisplayed()
        composeTestRule.onNodeWithText("Geography", useUnmergedTree = false).assertIsDisplayed()
    }
}
