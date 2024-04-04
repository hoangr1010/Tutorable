package com.example.tutorapp395project.screen.view

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class SessionInfoDialogKtTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Before
    fun setUp() {
        composeTestRule.setContent {
            SessionInfoDialog(
                sessionId = 123,
                tutorName = "John Doe",
                subject = "Math",
                dateIn = "10/10/2021",
                timeslot = "3:00-4:00",
                opponentEmail = "opponent@gmail.com"
            )
        }
    }

    /*
     * Purpose: Test if the session info dialog displays the session ID
     */
    @Test
    fun sessionInfoDialog_sessionIdExists() {
        composeTestRule.onNodeWithText("123").assertExists()
    }

    /*
     * Purpose: Test if the session info dialog displays the tutor name
     */
    @Test
    fun sessionInfoDialog_tutorNameExists() {
        composeTestRule.onNodeWithText("John Doe").assertExists()
    }

    /*
     * Purpose: Test if the session info dialog displays the subject
     */
    @Test
    fun sessionInfoDialog_subjectExists() {
        composeTestRule.onNodeWithText("Math").assertExists()
    }

    /*
     * Purpose: Test if the session info dialog displays the date in
     */
    @Test
    fun sessionInfoDialog_dateInExists() {
        composeTestRule.onNodeWithText("10/10/2021").assertExists()
    }

    /*
     * Purpose: Test if the session info dialog displays the timeslot
     */
    @Test
    fun sessionInfoDialog_timeslotExists() {
        composeTestRule.onNodeWithText("3:00-4:00").assertExists()
    }

    /*
     * Purpose: Test if the Send Email button has a email link attached to it
     */
    @Test
    fun sessionInfoDialog_opponentEmailExists() {
        composeTestRule.onNodeWithText("opponent@gmail.com").assertExists()
    }

}