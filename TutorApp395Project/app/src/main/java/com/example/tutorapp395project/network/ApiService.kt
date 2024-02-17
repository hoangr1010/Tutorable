package com.example.tutorapp395project.network

import com.example.tutorapp395project.model.LoginRequest
import com.example.tutorapp395project.model.LoginStudentResponse
import com.example.tutorapp395project.model.LoginTutorResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("login")
    suspend fun loginStudent(@Body loginRequest: LoginRequest): Response<LoginStudentResponse>
    suspend fun loginTutor(@Body loginRequest: LoginRequest): Response<LoginTutorResponse>
}