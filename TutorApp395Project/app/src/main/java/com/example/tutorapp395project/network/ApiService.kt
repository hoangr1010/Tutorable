package com.example.tutorapp395project.network

import com.example.tutorapp395project.model.Login
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST


interface AuthService {
    @POST("login")
    suspend fun login(@Body loginRequest: LoginRequest): ApiResponse<LoginResponse>
}

data class LoginRequest(
    val username: String,
    val password: String,
    val role: String)

data class LoginResponse(
    val token: String,
    val id : String,
    val firstName: String,
    val lastName: String,
    val role: String,
    val email: String
)

data class ApiResponse<T>(
    val data: T? = null,
    val message: String? = null
)