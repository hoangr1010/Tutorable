package com.example.tutorapp395project.screen.studentView

import androidx.compose.ui.Modifier
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.example.tutorapp395project.R
import com.example.tutorapp395project.data.User
import com.example.tutorapp395project.viewModel.AuthViewModel
import io.mockk.every
import io.mockk.mockk
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class StudentProfileKtTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var authViewModel: AuthViewModel
    private lateinit var user: User
    @Before
    fun setUp() {
        //val format = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        //var date: Date? = format.parse("2000-08-29")

        authViewModel = mockk(relaxed = true)
        user = User("699", first_name = "John", last_name = "Doe",
            date_of_birth = "2002-02-02T00:00:00Z", grade = 12 , school = "High School",
            email = "johndoe@gmail.com");
        every { authViewModel.UserState.value } returns user
    }

    @After
    fun tearDown() {
    }

    @Test
    fun studentProfileColumn() {
        composeTestRule.setContent {
            StudentProfileColumn(image = R.drawable.student_id_photo, authViewModel = authViewModel,
                                 modifier = Modifier)
        }
        composeTestRule.onNodeWithText("John Doe", useUnmergedTree = true).assertExists()
        //composeTestRule.onNodeWithText("2002-02-02", useUnmergedTree = true).assertExists()
        composeTestRule.onNodeWithText("12", useUnmergedTree = true).assertExists()
        composeTestRule.onNodeWithText("High School", useUnmergedTree = true).assertExists()
        composeTestRule.onNodeWithText("johndoe@gmail.com", useUnmergedTree = true).assertExists()
    }

    @Test
    fun profilePic() {
    }

    @Test
    fun profileName() {
        composeTestRule.setContent {
            ProfileName(name = "John Doe")
        }
        composeTestRule.onNodeWithText("John Doe", useUnmergedTree = true).assertExists()
    }

    @Test
    fun profileField() {
        composeTestRule.setContent {
            ProfileField("Email", "johndoe@gmail.com")
        }
        composeTestRule.onNodeWithText("Email", useUnmergedTree = true).assertExists()
        composeTestRule.onNodeWithText("johndoe@gmail.com", useUnmergedTree = true).assertExists()
    }
}