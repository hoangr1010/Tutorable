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

    // Mock the AuthRepository and AuthViewModel for testing purposes
    @Mock
    private lateinit var authRepository: AuthRepository
    private lateinit var authViewModel: AuthViewModel

    /*
     * Purpose: Set up the AuthRepository and AuthViewModel for testing
     */
    @Before
    fun setUp() {
        authRepository = Mockito.mock(AuthRepository::class.java)
        authViewModel = AuthViewModel(authRepository)
    }

    /*
     * Purpose: Test that onLoginChange updates the loginDataState
     */
    @Test
    fun onLoginChange_updatesLoginDataState() = runBlockingTest {
        val updatedLoginData = LoginData(email = "test@example.com", password = "password", role = "student")

        authViewModel.onLoginChange { updatedLoginData }

        assertEquals(updatedLoginData, authViewModel.loginDataState.value)
    }

    /*
     * Purpose: Test that onRegisterChange updates the registerDataState
     */
    @Test
    fun onRegisterChange_updatesRegisterDataState() = runBlockingTest {
        val updatedRegisterData = RegisterData(email = "test@example.com", password = "password", role = "student")

        authViewModel.onRegisterChange { updatedRegisterData }

        assertEquals(updatedRegisterData, authViewModel.registerDataState.value)
    }

    /*
     * Purpose: Test that onLogout resets the user state and token
     */
    @Test
    fun onLogout_resetsUserStateAndToken() = runBlockingTest {
        authViewModel.onLogout()

        assertEquals("", authViewModel.token.value)
        assertEquals("", authViewModel.UserState.value.role)
    }


    /*
     * Purpose: Test that onLogin calls the login method of AuthRepository with the correct parameters
     */
/*    @Test
    fun onLogin_callsAuthRepositoryLoginForStudent() = runBlockingTest {
        val loginData = LoginData(email = "test@example.com", password = "password", role = "student")
        authViewModel.onLoginChange { loginData }

        authViewModel.onLogin()

        verify(authRepository).login(any())
    }

    *//*
     * Purpose: Test that onRegister calls the registerAsStudent method of AuthRepository based
     *          on the role in registerDataState
     *//*
    @Test
    fun onRegister_callsAuthRepositoryRegisterForStudent() = runBlockingTest {
        val registerData = RegisterData(email = "test@example.com", password = "password", role = "student")
        authViewModel.onRegisterChange { registerData }

        authViewModel.onRegister()

        verify(authRepository).registerAsStudent(any())
    }

    *//*
     * Purpose: Test that onLogin calls the login method of AuthRepository with the correct parameters
     *//*
    @Test
    fun onLogin_callsAuthRepositoryLoginForTutor() = runBlockingTest {
        val loginData = LoginData(email = "test2@example.com", password = "password", role = "tutor")
        authViewModel.onLoginChange { loginData }

        authViewModel.onLogin()

        verify(authRepository).login(any())
    }

    *//*
     * Purpose: Test that onRegister calls the registerAsStudent method of AuthRepository based
     *          on the role in registerDataState
     *//*
    @Test
    fun onRegister_callsAuthRepositoryRegisterForTutor() = runBlockingTest {
        val registerData = RegisterData(email = "test2@example.com", password = "password", role = "tutor")
        authViewModel.onRegisterChange { registerData }

        authViewModel.onRegister()

        verify(authRepository).registerAsTutor(any())
    }*/
}