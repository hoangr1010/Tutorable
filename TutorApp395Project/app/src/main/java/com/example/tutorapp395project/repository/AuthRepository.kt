package com.example.tutorapp395project.repository

import com.example.tutorapp395project.data.LoginData
import com.example.tutorapp395project.data.LoginResponse
import com.example.tutorapp395project.data.RegisterDataStudent
import com.example.tutorapp395project.data.RegisterDataTutor
import com.example.tutorapp395project.data.RegisterResponse
import com.example.tutorapp395project.data.remote.AuthService
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AuthRepository {

    private var authService: AuthService

    init {
        val retrofit = Retrofit
            .Builder()
            .baseUrl("http://10.0.2.2:8080/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        authService = retrofit.create(AuthService::class.java)
    }

    suspend fun login(loginData: LoginData): Response<LoginResponse> {
        return authService.login(loginData)
    }

    suspend fun registerAsStudent(registerData: RegisterDataStudent): Response<RegisterResponse> {
        return authService.registerAsStudent(registerData)
    }

    suspend fun registerAsTutor(registerData: RegisterDataTutor): Response<RegisterResponse> {
        return authService.registerAsTutor(registerData)
    }
}
