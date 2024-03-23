package com.example.tutorapp395project.screen.studentView

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.tutorapp395project.viewModel.AuthViewModel
import com.example.tutorapp395project.viewModel.HomeViewModel
import com.example.tutorapp395project.viewModel.StudentViewModel
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SessionInfoPageStudentKtTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var studentViewModel: StudentViewModel
    private lateinit var authViewModel: AuthViewModel
    private lateinit var homeViewModel: HomeViewModel

    @Before
    fun setUp() {
        studentViewModel = StudentViewModel(authViewModel = AuthViewModel())
        authViewModel = AuthViewModel()
        homeViewModel = HomeViewModel()
    }

    @Test
    fun sessionInfoDisplayS() {
        composeTestRule.setContent {
            SessionInfoDisplayS(
                studentViewModel = studentViewModel,
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
        val titleText = "Test Title"

        composeTestRule.setContent {
            Title(titleText)
        }
        composeTestRule.onNodeWithText(titleText).assertExists()
    }

    @Test
    fun subtitle() {
        val subtitleText = "Test Subtitle"

        composeTestRule.setContent {
            Subtitle(subtitleText)
        }
        composeTestRule.onNodeWithText(subtitleText).assertExists()
    }

    @Test
    fun fixedSessionInfo() {
        val sessionID = 123456
        val tutorName = "Pikachu"
        val subject = "Maths"

        composeTestRule.setContent {
            FixedSessionInfo(sessionID, tutorName, subject)
        }

        composeTestRule.onNodeWithText(sessionID.toString()).assertExists()
        composeTestRule.onNodeWithText(tutorName).assertExists()
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