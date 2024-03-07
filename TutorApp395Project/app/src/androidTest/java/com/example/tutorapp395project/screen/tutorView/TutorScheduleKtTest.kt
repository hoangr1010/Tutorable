package com.example.tutorapp395project.screen.tutorView

import androidx.compose.ui.Modifier
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.example.tutorapp395project.viewModel.AuthViewModel
import com.example.tutorapp395project.viewModel.HomeViewModel
import com.example.tutorapp395project.viewModel.TutorViewModel
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class TutorScheduleKtTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var authViewModel: AuthViewModel
    private lateinit var tutorViewModel: TutorViewModel
    private lateinit var homeViewModel: HomeViewModel
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
            TutorAppointmentLayout(
                tutorViewModel = tutorViewModel,
                homeViewModel = homeViewModel,
                modifier = Modifier,
                authViewModel = authViewModel
            )
        }
        composeTestRule.onNodeWithText(expectedAppointment).assertExists()
    }
}
