package com.example.tutorapp395project.screen.view

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class SessionViewKtTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Before
    fun setUp() {
        composeTestRule.setContent {
            SessionView(
                time = "3:00-4:00",
                date = "10/10/2021",
                subject = "Math",
                with = "With",
                person = "John Doe"
            )
        }
    }

    @After
    fun tearDown() {
    }

    @Test
    fun sessionView_TimeDateSubjectExists() {
        composeTestRule.onNodeWithText("time: 3:00-4:00\ndate: 10/10/2021\nsubject: Math\n\n")
            .assertExists()
    }

    @Test
    fun sessionView_WithPersonExists() {
        composeTestRule.onNodeWithText("With: John Doe\n").assertExists()
    }

    @Test
    fun sessionViewPreview() {
    }
}