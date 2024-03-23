package com.example.tutorapp395project.viewModel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tutorapp395project.data.LoginData
import com.example.tutorapp395project.data.LoginResponse
import com.example.tutorapp395project.data.RegisterData
import com.example.tutorapp395project.data.RegisterResponse
import com.example.tutorapp395project.data.User
import com.example.tutorapp395project.data.dummyToken
import com.example.tutorapp395project.data.dummyUserStudent
import com.example.tutorapp395project.data.dummyUserTutor
import com.example.tutorapp395project.data.toStudentRegisterData
import com.example.tutorapp395project.data.toTutorRegisterData
import com.example.tutorapp395project.repository.AuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response

class AuthViewModel(
    private val authRepository: AuthRepository = AuthRepository()
): ViewModel() {

    val loginDataState = mutableStateOf(LoginData())
//    val UserState = mutableStateOf(User())
//    val token = mutableStateOf("")

    val registerDataState = mutableStateOf(RegisterData())
    val registerState = mutableStateOf<String>("")

    // DEVELOPMENT ONLY
    val UserState = mutableStateOf(dummyUserStudent)
    val token = mutableStateOf(dummyToken)

    fun onLoginChange(update: (LoginData) -> LoginData) {
        loginDataState.value = update(loginDataState.value)
    }

    fun onRegisterChange(update: (RegisterData) -> RegisterData) {
        registerDataState.value = update(registerDataState.value)
    }

    fun onLogin() {
        Log.d("AuthViewModel", "Email: ${loginDataState.value.email}, Password: ${loginDataState.value.password}, Role: ${loginDataState.value.role}")
        loginDataState.value = loginDataState.value.copy(
            role = loginDataState.value.role.trim(),
            email = loginDataState.value.email.trim(),
            password = loginDataState.value.password.trim()
        )
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
        UserState.value = UserState.value.copy(role = "")
    }

    fun onRegister() {
        Log.d("AuthViewModel", "${registerDataState.value}")
        viewModelScope.launch(Dispatchers.IO) {
            try {
                lateinit var response: Response<RegisterResponse>
                if (registerDataState.value.role == "student") {
                    response = authRepository.registerAsStudent(toStudentRegisterData(registerDataState.value))
                } else {
                    response = authRepository.registerAsTutor(toTutorRegisterData(registerDataState.value))
                }

                if (response.isSuccessful) {
                    val registerResponse: RegisterResponse? = response.body()
                    if (registerResponse != null) {
                        Log.d("AuthViewModel", "Register response: ${registerResponse.result}")
                        registerState.value = if (registerResponse.result) "✅ You successfully create new account!" else "❌ Error creating account. Please try again."
                    }
                } else {
                    Log.e("AuthViewModel", "Error registering: ${response.message()}")
                    registerState.value = "❌ Error creating account: ${response.message()}. Please try again."
                }

            } catch (e: Exception) {
                Log.e("AuthViewModel", "Error registering: ", e)
                registerState.value = "❌ Error creating account: ${e}. Please try again."
            }
        }
    }

}