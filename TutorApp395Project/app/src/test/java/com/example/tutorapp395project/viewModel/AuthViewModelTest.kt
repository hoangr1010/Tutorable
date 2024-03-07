package com.example.tutorapp395project.viewModel

import com.example.tutorapp395project.data.LoginData
import com.example.tutorapp395project.data.RegisterData
import com.example.tutorapp395project.repository.AuthRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class AuthViewModelTest {

    @Mock
    private lateinit var authRepository: AuthRepository
    private lateinit var authViewModel: AuthViewModel

    @Before
    fun setUp() {
        authRepository = Mockito.mock(AuthRepository::class.java)
        authViewModel = AuthViewModel(authRepository)
    }

    @Test
    fun onLoginChange_updatesLoginDataState() = runBlockingTest {
        val initialLoginData = LoginData()
        val updatedLoginData = LoginData(email = "test@example.com", password = "password", role = "student")

        authViewModel.onLoginChange { updatedLoginData }

        assertEquals(updatedLoginData, authViewModel.loginDataState.value)
    }

    @Test
    fun onRegisterChange_updatesRegisterDataState() = runBlockingTest {
        val initialRegisterData = RegisterData()
        val updatedRegisterData = RegisterData(email = "test@example.com", password = "password", role = "student")

        authViewModel.onRegisterChange { updatedRegisterData }

        assertEquals(updatedRegisterData, authViewModel.registerDataState.value)
    }

    @Test
    fun onLogout_resetsUserStateAndToken() = runBlockingTest {
        authViewModel.onLogout()

        assertEquals("", authViewModel.token.value)
        assertEquals("", authViewModel.UserState.value.role)
    }

/*    @Test
    fun onRegister_updatesRegisterDataStateAndRegisterState() = runBlockingTest {
        val registerData = RegisterData(email = "test@example.com", password = "password", role = "student")

        authViewModel.onRegisterChange { registerData }
        authViewModel.onRegister()

        assertEquals(registerData, authViewModel.registerDataState.value)
        assertTrue(authViewModel.registerState.value.isNotEmpty())
    }*/
}