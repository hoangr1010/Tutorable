package com.example.tutorapp395project.network

import com.example.tutorapp395project.model.LoginRequest
import com.example.tutorapp395project.model.LoginResponse
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {
    @POST("login")
    fun login(
        @Body request: LoginRequest
    ): Response<LoginResponse>
}

object RetrofitInstance {
    private const val BASE_URL = "http://localhost:8080/auth/login"
    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}