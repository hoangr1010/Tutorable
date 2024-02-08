package com.example.tutorapp395project.repository

import com.example.tutorapp395project.network.AuthService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/*
    Purpose: interface to define the login request
    Parameters: None
    Returns: Response<LoginResponse>
 */
interface AuthRepository {
    /*
        Purpose: function to perform the login
        Parameters:
            - email: String
            - password: String
            - role: String
            - onLoginSuccess: () -> Unit
            - onLoginFailed: (String) -> Unit
        Returns: Unit
     */
    fun preformLogin(
        email: String, password: String, role: String,
        onLoginSuccess: () -> Unit, onLoginFailed: (String) -> Unit
    ) {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://localhost:8080/auth/login")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val authService = retrofit.create(AuthService::class.java)
        //val call = AuthService.login(email, password, role)
    }
}

