package com.example.tutorapp395project

import com.example.tutorapp395project.viewModel.AuthViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

//@ExperimentalCoroutinesApi
//class AuthViewModelTest {
//
//    private val viewModel = AuthViewModel()
//
//    @Test
//    fun testLoginSuccess() = runTest {
//        // Given
//        val email = "test@example.com"
//        val password = "password"
//        val role = "user"
//        val expectedToken = "token123"
//
//        // When
//        viewModel.login(email, password, role)
//
//        // Then
//        assertEquals(expectedToken, viewModel.authToken.value)
//    }
//
//    @Test
//    fun testLoginError() = runTest {
//        // Given
//        val email = "test@example.com"
//        val password = "password"
//        val role = "user"
//        val errorMessage = "Invalid credentials"
//
//        // When
//        viewModel.login(email, password, role)
//
//        // Then
//        assertEquals(errorMessage, viewModel.errorMessage.value)
//    }
//}
