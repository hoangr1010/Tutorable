package com.example.tutorapp395project.screen.studentView

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class StudentScheduleKtTest {

    @get:Rule
    val composeTestRule = createComposeRule()
    @Before
    fun setUp() {
    }

    @After
    fun tearDown() {
    }

    @Test
    fun studentAppointmentLayout_displayCorrectAppointment() {
        val expectedAppointment = "3:00PM - 4:00PM"

        composeTestRule.setContent {
            StudentAppointmentLayout()
        }
        composeTestRule.onNodeWithText(expectedAppointment).assertExists()
    }

    @Test
    fun appointment_displaysCorrectDetails() {
        val expectedDetails = "3:00PM - 4:00PM" // Replace with the expected details based on your setup

        composeTestRule.setContent {
            Appointment(time = "3:00PM - 4:00PM", date = "January 24th, 2024", subject = "Math",
                        with = "Tutor", person = "Karen McTutor")
        }
        composeTestRule.onNodeWithText(expectedDetails).assertExists()
    }
}