package com.example.tutorapp395project.screen.view

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.tutorapp395project.viewModel.StudentViewModel
import com.maxkeppeker.sheets.core.models.base.rememberUseCaseState
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SessionInfoViewKtTest {

    @get:Rule
    val composeTestRule = createComposeRule()
    @Test
     fun fixedSessionInfoCard() {
         composeTestRule.setContent {
             FixedSessionInfoCard(
                    sessionId = 1,
                    tutorName = "John Doe",
                    subject = "Math"
             )
         }
         composeTestRule.onNodeWithText("Session Information").assertExists()
     }

    @Test
    fun freeSessionInfoCard() {
        composeTestRule.setContent {
            FreeSessionInfoCard(
                dateIn = "2024/04/04",
                timeslot = "14:00-15:00",
                calendarState = rememberUseCaseState(),
                opponentEmail = "opponent@gmail.com",
                studentViewModel = StudentViewModel()
            )
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
        val opponentEmail = "opponent@gmail.com"
        val studentViewModel = StudentViewModel()

        composeTestRule.setContent {
            FreeSessionInfo(date, time, { }, rememberUseCaseState(), opponentEmail, studentViewModel)
        }

        composeTestRule.onNodeWithText(date).assertExists()
        composeTestRule.onNodeWithText(time).assertExists()
    }
}