package com.example.tutorapp395project.screen.studentView

import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.example.tutorapp395project.R
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class TutorListKtTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Before
    fun setUp() {
        composeTestRule.setContent {
            TutorList()

            val name = "Roronoa Zoro"
            val subject = "Geography"
            TutorCard(
                painter = painterResource(id = R.drawable.image2),
                name = name,
                subject = subject,
                modifier = Modifier
            )
        }
    }

    @After
    fun tearDown() {
    }

    @Test
    fun testTutorList() {
        composeTestRule.onNodeWithText("Say hi to your tutor").assertIsDisplayed()
    }

    @Test
    fun testTutorCard() {
        composeTestRule.onNodeWithText("Roronoa Zoro").assertIsDisplayed()
        composeTestRule.onNodeWithText("Geography").assertIsDisplayed()
    }
}
