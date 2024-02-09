package com.example.tutorapp395project.viewModel

import com.example.tutorapp395project.network.RetrofitInstance

/*
    Purpose: This class is used to handle the login process
    Functions:
        - login: This function is used to login the user
    Return: None
 */
class AuthViewModel {
    private val apiService = RetrofitInstance.provideAuthService(
        RetrofitInstance.provideRetrofit(RetrofitInstance.provideBaseUrl())
    )

    /*
        Purpose: This function is used to login the user
        Parameters:
            - email: String
            - password: String
            - role: String
            - navController: NavController
            - target: String
        Return: None
     */
    /*
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
     */
}