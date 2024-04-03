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

    @Test
    fun sessionInfoDialog_sessionIdExists() {
        composeTestRule.onNodeWithText("123").assertExists()
    }
    @Test
    fun sessionInfoDialog_tutorNameExists() {
        composeTestRule.onNodeWithText("John Doe").assertExists()
    }

    @Test
    fun sessionInfoDialog_subjectExists() {
        composeTestRule.onNodeWithText("Math").assertExists()
    }

    @Test
    fun sessionInfoDialog_dateInExists() {
        composeTestRule.onNodeWithText("10/10/2021").assertExists()
    }

    @Test
    fun sessionInfoDialog_timeslotExists() {
        composeTestRule.onNodeWithText("3:00-4:00").assertExists()
    }

}