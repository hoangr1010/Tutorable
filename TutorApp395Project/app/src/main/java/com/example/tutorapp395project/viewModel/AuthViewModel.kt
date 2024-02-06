package com.example.tutorapp395project.viewModel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tutorapp395project.network.LoginRequest
import com.example.tutorapp395project.network.RetrofitInstance
import kotlinx.coroutines.launch
import retrofit2.Retrofit

class AuthViewModel: ViewModel() {
    val authToken = mutableStateOf<String?>(null)
    val errorMessage = mutableStateOf<String?>(null)

    fun login(email: String, password: String, role: String) {
        viewModelScope.launch {
            try{
                val response = RetrofitInstance.authService.login(LoginRequest(email, password, role))
                response.data?.let { loginResponse ->
                    authToken.value = loginResponse.token
                } ?: run {
                    errorMessage.value = response.message ?: "An unknown error occurred"
                }
            } catch (e: Exception) {
                errorMessage.value = "An error occurred: ${e.message}"
            }
        }
    }

}