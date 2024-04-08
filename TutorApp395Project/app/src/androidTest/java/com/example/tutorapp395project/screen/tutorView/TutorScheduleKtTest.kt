package com.example.tutorapp395project.screen.tutorView

import androidx.compose.ui.test.junit4.createComposeRule
import com.example.tutorapp395project.viewModel.AuthViewModel
import com.example.tutorapp395project.viewModel.HomeViewModel
import com.example.tutorapp395project.viewModel.TutorViewModel
import org.junit.Rule


class TutorScheduleKtTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    // Initialize ViewModels to be used for testing purposes
    private lateinit var authViewModel: AuthViewModel
    private lateinit var tutorViewModel: TutorViewModel
    private lateinit var homeViewModel: HomeViewModel

    /*@Before
    fun setUp() {
        authViewModel = AuthViewModel()
        tutorViewModel = TutorViewModel()
        homeViewModel = HomeViewModel()
    }*/

   /* @Test
    fun tutorAppointmentLayout_displaysCorrectAppointment() {
        val expectedAppointment = "3:00PM - 4:00PM"

        composeTestRule.setContent {
            TutorAppointmentLayout(
                tutorViewModel = tutorViewModel,
                homeViewModel = homeViewModel,
                modifier = Modifier,
                authViewModel = authViewModel
            )
        }
        composeTestRule.onNodeWithText(expectedAppointment).assertExists()
    }

    *//*
     * Purpose: Check if date is displayed correctly
     *//*
    @Test
    fun tutorAppointmentLayout_displaysCorrectDate() {
        val expectedDate = "January 24th, 2024"

        composeTestRule.setContent {
            TutorAppointmentLayout(
                tutorViewModel = tutorViewModel,
                homeViewModel = homeViewModel,
                modifier = Modifier,
                authViewModel = authViewModel
            )
        }
        composeTestRule.onNodeWithText(expectedDate).assertExists()
    }

    *//*
     * Purpose: Check if subject is displayed correctly
     *//*
    @Test
    fun tutorAppointmentLayout_displaysCorrectSubject() {
        val expectedSubject = "Math"

        composeTestRule.setContent {
            TutorAppointmentLayout(
                tutorViewModel = tutorViewModel,
                homeViewModel = homeViewModel,
                modifier = Modifier,
                authViewModel = authViewModel
            )
        }
        composeTestRule.onNodeWithText(expectedSubject).assertExists()
    }

    *//*
     * Purpose: Check is no appointments text is displayed when there are no appointments
     *//*
    @Test
    fun tutorAppointmentLayout_displaysNoAppointmentsText() {
        composeTestRule.setContent {
            TutorAppointmentLayout(
                tutorViewModel = tutorViewModel,
                homeViewModel = homeViewModel,
                modifier = Modifier,
                authViewModel = authViewModel
            )
        }
        composeTestRule.onNodeWithText("No appointments scheduled").assertExists()
    }


    *//*
     * Purpose: Check if the tutor's name is displayed correctly
     *//*
    @Test
    fun tutorAppointmentLayout_displaysCorrectTutorName() {
        val expectedTutorName = "Jane Doe"
        val user = User("1000", first_name = "Jane", last_name = "Doe",
            date_of_birth = "2006-05-02T00:00:00Z", email = "janedoe@gmail.com")
        every { authViewModel.UserState.value } returns user

        composeTestRule.setContent {
            TutorAppointmentLayout(
                tutorViewModel = tutorViewModel,
                homeViewModel = homeViewModel,
                modifier = Modifier,
                authViewModel = authViewModel
            )
        }
        composeTestRule.onNodeWithText(expectedTutorName, useUnmergedTree = true).assertExists()
    }*/
}
