package com.example.tutorapp395project.data.remote

import com.example.tutorapp395project.data.AddAvailabilityRequest
import com.example.tutorapp395project.data.AddAvailabilityResponse
import com.example.tutorapp395project.data.GetAvailabilityRequest
import com.example.tutorapp395project.data.GetAvailabilityResponse
import com.example.tutorapp395project.data.SessionRequest
import com.example.tutorapp395project.data.SessionResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface UserService {

    @POST("get_tutoring_session_list")
    suspend fun getSessionList(@Body sessionRequest: SessionRequest): Response<SessionResponse>

    @POST("get_tutor_availability")
    suspend fun getAvailability(@Body getAvailabilityRequest: GetAvailabilityRequest): Response<GetAvailabilityResponse>

    @POST("add_tutor_availability")
    suspend fun addAvailability(@Body addAvailabilityRequest: AddAvailabilityRequest): Response<AddAvailabilityResponse>
}