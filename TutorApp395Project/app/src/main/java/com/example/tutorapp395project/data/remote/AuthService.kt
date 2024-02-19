package com.example.tutorapp395project.data.remote

import com.example.tutorapp395project.data.LoginData
import com.example.tutorapp395project.data.LoginResponse
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST


interface AuthService {
    @POST("auth/login")
    suspend fun login(@Body loginData: LoginData): Response<LoginResponse>
}