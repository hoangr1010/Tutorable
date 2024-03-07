package com.example.tutorapp395project.screen.view

import androidx.compose.ui.Modifier
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import com.example.tutorapp395project.viewModel.AuthViewModel
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class SettingsKtTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var navController: TestNavHostController
    private lateinit var authViewModel: AuthViewModel

    @Before
    fun setUp() {
        navController = TestNavHostController(ApplicationProvider.getApplicationContext())
        authViewModel = AuthViewModel()
        composeTestRule.setContent {
            SettingsColumn(navController, authViewModel, Modifier)
        }
    }

    @After
    fun tearDown() {
    }

    @Test
    fun settingsColumn() {
        composeTestRule.onNodeWithText("Logout").assertIsDisplayed()
    }
}