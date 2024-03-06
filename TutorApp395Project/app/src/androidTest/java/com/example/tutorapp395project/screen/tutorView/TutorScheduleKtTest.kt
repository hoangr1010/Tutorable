package com.example.tutorapp395project.screen.tutorView

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class TutorScheduleKtTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Before
    fun setUp() {
    }

    @After
    fun tearDown() {
    }

    @Test
    fun tutorAppointmentLayout_displaysCorrectAppointment() {
        val expectedAppointment = "3:00PM - 4:00PM"

        composeTestRule.setContent {
            TutorAppointmentLayout()
        }
        composeTestRule.onNodeWithText(expectedAppointment).assertExists()
    }
}
