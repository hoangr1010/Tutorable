package com.example.tutorapp395project.screen.tutorView

import androidx.compose.ui.Modifier
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.example.tutorapp395project.R
import com.example.tutorapp395project.data.User
import com.example.tutorapp395project.screen.studentView.StudentProfileColumn
import com.example.tutorapp395project.viewModel.AuthViewModel
import io.mockk.every
import io.mockk.mockk
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class TutorProfileKtTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var authViewModel: AuthViewModel
    private lateinit var user: User

    @Before
    fun setUp() {

        authViewModel = mockk(relaxed = true)
        user = User("1000", first_name = "Jane", last_name = "Doe",
            date_of_birth = "2006-05-02T00:00:00Z", email = "janedoe@gmail.com");
        every { authViewModel.UserState.value } returns user
    }

    @After
    fun tearDown() {
    }

    @Test
    fun tutorProfileColumn() {
        composeTestRule.setContent {
            StudentProfileColumn(image = R.drawable.tutor_headshot, authViewModel = authViewModel,
                modifier = Modifier
            )
        }
        composeTestRule.onNodeWithText("Jane Doe", useUnmergedTree = true).assertExists()
        //composeTestRule.onNodeWithText("2002-02-02", useUnmergedTree = true).assertExists()
        composeTestRule.onNodeWithText("janedoe@gmail.com", useUnmergedTree = true).assertExists()
    }
}