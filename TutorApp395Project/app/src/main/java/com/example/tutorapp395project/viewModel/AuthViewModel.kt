package com.example.tutorapp395project.viewModel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tutorapp395project.data.LoginData
import com.example.tutorapp395project.data.LoginResponse
import com.example.tutorapp395project.data.User
import com.example.tutorapp395project.data.dummyToken
import com.example.tutorapp395project.data.dummyUser
import com.example.tutorapp395project.repository.AuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AuthViewModel(
    private val authRepository: AuthRepository = AuthRepository()
): ViewModel() {

    val loginDataState = mutableStateOf(LoginData())
    val UserState = mutableStateOf(User())
    val token = mutableStateOf("")

    val registerRoleState = mutableStateOf("")

    // DEVELOPMENT ONLY
//    val UserState = mutableStateOf(dummyUser)
//    val token = mutableStateOf(dummyToken)

    fun onEmailChange(email: String) {
        loginDataState.value = loginDataState.value.copy(email = email)
    }

    fun onPasswordChange(password: String) {
        loginDataState.value = loginDataState.value.copy(password = password)
    }

    fun onRoleChange(role: String) {
        loginDataState.value = loginDataState.value.copy(role = role)
    }

    fun onRegisterRoleChange(role: String) {
        registerRoleState.value = role
    }

    fun onLogin() {
        Log.d("AuthViewModel", "Email: ${loginDataState.value.email}, Password: ${loginDataState.value.password}, Role: ${loginDataState.value.role}")
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = authRepository.login(loginDataState.value)
                Log.d("AuthViewModel", "Response: $response")
                Log.d("AuthViewModel", "Response body: ${response.body()}")

                if (response.isSuccessful) {
                    // save token and user
                    val loginResponse: LoginResponse? = response.body()

                    if (loginResponse != null) {
                        token.value = loginResponse.token
                        UserState.value = loginResponse.user
                    }

                } else {
                    Log.e("AuthViewModel", "Error logging in: ${response.message()}")
                }
            } catch (e: Exception) {
                Log.e("AuthViewModel", "Error logging in: ", e)
            }
        }
    }

    fun onLogout() {
        token.value = ""
        UserState.value = User()
    }

}