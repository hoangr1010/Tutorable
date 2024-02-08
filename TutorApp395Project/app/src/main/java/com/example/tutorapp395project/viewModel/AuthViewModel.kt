package com.example.tutorapp395project.viewModel

import androidx.navigation.NavController
import com.example.tutorapp395project.model.LoginRequest
import com.example.tutorapp395project.network.AuthService
import com.example.tutorapp395project.network.RetrofitInstance

class AuthViewModel {
    private val apiService = RetrofitInstance.retrofit.create(AuthService::class.java)

    suspend fun login(email: String, password: String, role: String, navController: NavController,
                      target: String) {
        val request = LoginRequest(email, password, role)
        val response = apiService.login(request)
        if (response.isSuccessful) {
            val loginResponse = response.body()
            navController.navigate(target)
        }
        else {
            // Handle failed login
            val errorMessage = response.message()
            // Do something with errorMessage
        }
    }
}