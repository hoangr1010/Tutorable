package com.example.tutorapp395project.screen.view

import android.content.Intent
import android.net.Uri
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.matcher.IntentMatchers
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

    /*
     * Purpose: Test if the FixedSessionInfoCard composable is displayed correctly
     */
    @Test
     fun fixedSessionInfoCard_Exists() {
         composeTestRule.setContent {
             FixedSessionInfoCard(
                    sessionId = 1,
                    tutorName = "John Doe",
                    subject = "Math"
             )
         }
         composeTestRule.onNodeWithText("Session Information").assertExists()
     }

    /*
     * Purpose: Test if the FreeSessionInfoCard composable is displayed correctly
     */
    @Test
    fun freeSessionInfoCard_Exists() {
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

    /*
     * Purpose: Test if the title is displayed correctly
     */
    @Test
    fun title_Exists() {
        val titleText = "Test Title"

        composeTestRule.setContent {
            Title(titleText)
        }
        composeTestRule.onNodeWithText(titleText).assertExists()
    }

    /*
     * Purpose: Test if the subtitle is displayed correctly
     */
    @Test
    fun subtitle_Exists() {
        val subtitleText = "Test Subtitle"

        composeTestRule.setContent {
            Subtitle(subtitleText)
        }
        composeTestRule.onNodeWithText(subtitleText).assertExists()
    }

    /*
     * Purpose: Test if the fixed session info text displayed correctly
     */
    @Test
    fun fixedSessionInfo_Exists() {
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

    /*
     * Purpose: Test if the free session info text displayed correctly
     */
    @Test
    fun freeSessionInfo_Exists() {
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

    /*
     * Purpose: Test if the send email button is displayed correctly
     */
    @Test
    fun freeSessionInfo_SendEmailButtonExists() {
        composeTestRule.onNodeWithText("Send Email").assertExists()
    }

    /*
     * Purpose: Test if the email intent is correctly created
     */
    @Test
    fun freeSessionInfo_EmailIntentCorrectlyCreated() {
        Intents.init()
        val date = "2024/04/04"
        val time = "14:00-15:00"
        val opponentEmail = "opponent@gmail.com"
        val studentViewModel = StudentViewModel()

        // Create the FreeSessionInfo composable
        composeTestRule.setContent {
            FreeSessionInfo(
                dateIn=date,
                timeslot = time,
                onDelete = { },
                calendarState = rememberUseCaseState(),
                opponentEmail = opponentEmail,
                studentViewModel = studentViewModel
            )
        }

        try {
            Intents.intended(IntentMatchers.hasAction(Intent.ACTION_SENDTO))
            Intents.intended(IntentMatchers.hasData(Uri.parse("mailto:${opponentEmail}")))
        } finally {
            Intents.release()
        }
    }
}