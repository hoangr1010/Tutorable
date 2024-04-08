package com.example.tutorapp395project.screen.studentView

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.tutorapp395project.viewModel.AuthViewModel
import com.example.tutorapp395project.viewModel.HomeViewModel
import com.example.tutorapp395project.viewModel.StudentViewModel
import org.junit.Rule
import org.junit.runner.RunWith

class StudentScheduleKtTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    // Initalize ViewModels used for testing Student Schedule page
    private lateinit var authViewModel: AuthViewModel
    private lateinit var studentViewModel: StudentViewModel
    private lateinit var homeViewModel: HomeViewModel

   /* @Before
    fun setUp() {
        authViewModel = spyk(AuthViewModel())
        studentViewModel = spyk(StudentViewModel())
        homeViewModel = spyk(HomeViewModel())

        every { studentViewModel.sessionState.value.session_list } returns listOf(
            TutoringSession(
                10,
                10,
                3,
                "Karen McTutor",
                "Tommy McStudent",
                "Tutoring Session",
                "Description",
                "Math",
                9,
                "Active",
                "2024-01-24T15:00:00Z",
                listOf(1),
                "student@gmail.com",
                "tutor@gmail.com"
                )
        )
    }*/
/*
    *//*
     * Purpose: Test if the StudentAppointmentLayout displays the correct appointment
     *//*
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

    *//*
     * Purpose: Test if the StudentAppointmentLayout displays the correct date
     *//*
    @Test
    fun appointment_displaysCorrectDetails() {
        val date = "3:00PM - 4:00PM"

        composeTestRule.setContent {
            SessionView(
                time = "3:00PM - 4:00PM", date = "January 24th, 2024", subject = "Math",
                with = "Tutor", person = "Karen McTutor"
            )
        }
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText(date).assertExists()
    }

    *//*
     * Purpose: Test if the StudentAppointmentLayout displays "No appointments scheduled"
     *          when there are no appointments
     *//*
    @Test
    fun studentAppointmentLayout_displayNoAppointments() {
        val expectedText = "No appointments scheduled"

        // Mock the ViewModel to return an empty list of sessions
        every { studentViewModel.sessionState.value.session_list } returns emptyList()

        composeTestRule.setContent {
            StudentAppointmentLayout(
                studentViewModel = studentViewModel,
                homeViewModel = homeViewModel,
                modifier = Modifier,
                authViewModel = authViewModel
            )
        }
        composeTestRule.onNodeWithText(expectedText).assertExists()
    }*/
}