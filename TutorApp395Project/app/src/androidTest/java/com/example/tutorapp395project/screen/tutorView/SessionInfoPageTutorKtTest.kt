package com.example.tutorapp395project.screen.tutorView

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.example.tutorapp395project.viewModel.AuthViewModel
import com.example.tutorapp395project.viewModel.HomeViewModel
import com.example.tutorapp395project.viewModel.TutorViewModel
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.MockK
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class SessionInfoPageTutorKtTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @MockK
    lateinit var tutorViewModel: TutorViewModel

    @MockK
    lateinit var authViewModel: AuthViewModel

    @MockK
    lateinit var homeViewModel: HomeViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @Test
    fun sessionInfoDisplayT() {
        composeTestRule.setContent {
            SessionInfoDisplayT(
                tutorViewModel = tutorViewModel,
                authViewModel = authViewModel,
                homeViewModel = homeViewModel
            )
        }
        composeTestRule.onNodeWithText("Session Information").assertExists()
    }

    @Test
    fun sessionButtons() {
        composeTestRule.setContent {
            SessionButtons()
        }
        composeTestRule.onNodeWithText("Edit").assertExists()
        composeTestRule.onNodeWithText("Cancel").assertExists()
    }

    @Test
    fun fixedSessionInfoCard() {
        composeTestRule.setContent {
            FixedSessionInfoCard()
        }
        composeTestRule.onNodeWithText("Session Information").assertExists()
    }

    @Test
    fun freeSessionInfoCard() {
        composeTestRule.setContent {
            FreeSessionInfoCard()
        }
        composeTestRule.onNodeWithText("Date").assertExists()
        composeTestRule.onNodeWithText("Time").assertExists()
    }

    @Test
    fun title() {
        composeTestRule.setContent {
            Title("Test Title")
        }
        composeTestRule.onNodeWithText("Test Title").assertExists()
    }

    @Test
    fun subtitle() {
        composeTestRule.setContent {
            Subtitle("Test Subtitle")
        }
        composeTestRule.onNodeWithText("Test Subtitle").assertExists()
    }

    @Test
    fun fixedSessionInfo() {
        val sessionId = 123456
        val studentName = "Hawking"
        val grade = 6
        val subject = "Maths"

        composeTestRule.setContent {
            FixedSessionInfo(sessionId, studentName, grade, subject)
        }
        composeTestRule.onNodeWithText(sessionId.toString()).assertExists()
        composeTestRule.onNodeWithText(studentName).assertExists()
        composeTestRule.onNodeWithText(grade.toString()).assertExists()
        composeTestRule.onNodeWithText(subject).assertExists()
    }

    @Test
    fun freeSessionInfo() {
        val date = "2024/04/04"
        val time = "14:00-15:00"

        composeTestRule.setContent {
            FreeSessionInfo(date, time)
        }
        composeTestRule.onNodeWithText(date).assertExists()
        composeTestRule.onNodeWithText(time).assertExists()

    }
}