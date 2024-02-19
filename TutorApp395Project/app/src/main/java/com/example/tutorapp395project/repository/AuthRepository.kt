package com.example.tutorapp395project.repository

import com.example.tutorapp395project.data.LoginData
import com.example.tutorapp395project.data.LoginResponse
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AuthRepository {

    private var authService: com.example.tutorapp395project.data.remote.AuthService

    init {
        val retrofit = Retrofit
            .Builder()
            .baseUrl("http://10.0.2.2:8080/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        authService = retrofit.create(com.example.tutorapp395project.data.remote.AuthService::class.java)
    }

    suspend fun login(loginData: LoginData): Response<LoginResponse> {
        return authService.login(loginData)
    }
}
