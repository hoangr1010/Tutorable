package com.example.tutorapp395project.network

import com.example.tutorapp395project.model.Login
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

/*
interface ApiService {
    @GET("Login.json")
    suspend fun getLogin(): List<Login>

    companion object {
        var apiService: ApiService? = null
        fun getInstance(): ApiService {
            if (apiService == null) {
                apiService = Retrofit.Builder()
                    .baseUrl({/*TODO: Add your base URL here*/})
                    .addConverterFactory(GsonConverterFactory.create())
                    .build().create(ApiService::class.java)
            }
            return apiService!!
        }
    }
}

 */