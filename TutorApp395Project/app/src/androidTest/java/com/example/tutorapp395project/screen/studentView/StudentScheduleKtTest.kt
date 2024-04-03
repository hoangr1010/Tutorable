package com.example.tutorapp395project.screen.studentView

import androidx.compose.ui.Modifier
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.example.tutorapp395project.screen.view.SessionView
import com.example.tutorapp395project.viewModel.AuthViewModel
import com.example.tutorapp395project.viewModel.HomeViewModel
import com.example.tutorapp395project.viewModel.StudentViewModel
import org.junit.Rule
import org.junit.Test

class StudentScheduleKtTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    // Initalize ViewModels used for testing Student Schedule page
    private lateinit var authViewModel: AuthViewModel
    private lateinit var studentViewModel: StudentViewModel
    private lateinit var homeViewModel: HomeViewModel

    /*
     * Purpose: Test if the StudentAppointmentLayout displays the correct appointment
     */
    @Test
    fun studentAppointmentLayout_displayCorrectAppointment() {
        val expectedAppointment = "3:00PM - 4:00PM"

        composeTestRule.setContent {
            StudentAppointmentLayout(
                studentViewModel = studentViewModel,
                homeViewModel = homeViewModel,
                modifier = Modifier,
                authViewModel = authViewModel
            )
        }
        composeTestRule.onNodeWithText(expectedAppointment).assertExists()
    }

    /*
     * Purpose: Test if the StudentAppointmentLayout displays the correct date
     */
    @Test
    fun appointment_displaysCorrectDetails() {
        val date = "3:00PM - 4:00PM"

        composeTestRule.setContent {
            SessionView(time = "3:00PM - 4:00PM", date = "January 24th, 2024", subject = "Math",
                        with = "Tutor", person = "Karen McTutor")
        }
        composeTestRule.onNodeWithText(date).assertExists()
    }
}