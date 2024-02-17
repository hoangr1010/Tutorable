package com.example.tutorapp395project.repository

import com.example.tutorapp395project.model.LoginRequest
import com.example.tutorapp395project.model.LoginStudentResponse
import com.example.tutorapp395project.network.RetrofitInstance
import retrofit2.Response

class AuthRepository {

    suspend fun loginStudent(loginRequest: LoginRequest): Response<LoginStudentResponse> {
        return RetrofitInstance.loginApi.loginStudent(loginRequest)
    }

    suspend fun loginTutor(loginRequest: LoginRequest): Response<LoginStudentResponse> {
        return RetrofitInstance.loginApi.loginStudent(loginRequest)
    }
}