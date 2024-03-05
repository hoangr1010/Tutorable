package com.example.tutorapp395project.data.remote

import com.example.tutorapp395project.data.LoginData
import com.example.tutorapp395project.data.LoginResponse
import com.example.tutorapp395project.data.RegisterData
import com.example.tutorapp395project.data.RegisterDataStudent
import com.example.tutorapp395project.data.RegisterDataTutor
import com.example.tutorapp395project.data.RegisterResponse
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST


interface AuthService : UserService {
    @POST("auth/login")
    suspend fun login(@Body loginData: LoginData): Response<LoginResponse>

    @POST("auth/register")
    suspend fun registerAsStudent(@Body registerData: RegisterDataStudent): Response<RegisterResponse>

    @POST("auth/register")
    suspend fun registerAsTutor(@Body registerData: RegisterDataTutor): Response<RegisterResponse>

}