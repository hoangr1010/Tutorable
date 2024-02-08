package com.example.tutorapp395project.network

import com.example.tutorapp395project.model.LoginRequest
import com.example.tutorapp395project.model.LoginResponse
import dagger.Component
import dagger.Provides
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST

/*
    Purpose: interface to define the login request
    Parameters: None
    Returns: Response<LoginResponse>
 */
@Component(modules = [RetrofitInstance::class])
interface AuthService {
    @POST("login")
    fun login(
        @Body request: LoginRequest
    ): Response<LoginResponse>
}

/*
    Purpose: object to create the retrofit instance
    Parameters: None
    Returns: Retrofit
 */
object RetrofitInstance {

    @Provides
    fun provideBaseUrl(): String {
        return "http://localhost:8080/auth/"  // Base URL without endpoint
    }

    @Provides
    fun provideRetrofit(baseUrl: String): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl + "login")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    fun provideAuthService(retrofit: Retrofit): AuthService {
        return retrofit.create(AuthService::class.java)
    }
}