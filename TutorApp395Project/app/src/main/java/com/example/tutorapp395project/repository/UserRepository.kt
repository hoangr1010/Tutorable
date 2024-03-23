package com.example.tutorapp395project.repository

import com.example.tutorapp395project.data.AddAvailabilityRequest
import com.example.tutorapp395project.data.AddAvailabilityResponse
import com.example.tutorapp395project.data.CreateSessionRequest
import com.example.tutorapp395project.data.CreateSessionResponse
import com.example.tutorapp395project.data.DeleteSessionRequest
import com.example.tutorapp395project.data.DeleteSessionResponse
import com.example.tutorapp395project.data.GetAvailabilityRequest
import com.example.tutorapp395project.data.GetAvailabilityResponse
import com.example.tutorapp395project.data.SessionRequest
import com.example.tutorapp395project.data.SessionResponse
import com.example.tutorapp395project.data.TutorFilterRequest
import com.example.tutorapp395project.data.TutorFilterResponse
import com.example.tutorapp395project.data.remote.AuthService
import com.example.tutorapp395project.data.remote.UserService
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class UserRepository {

    private var userService: UserService

    init {
        val retrofit = Retrofit
            .Builder()
            .baseUrl("http://10.0.2.2:8080/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        userService = retrofit.create(UserService::class.java)
    }

    suspend fun getSessionList(sessionRequest: SessionRequest): Response<SessionResponse> {
        return userService.getSessionList(sessionRequest)
    }

    suspend fun getAvailability(getAvailabilityRequest: GetAvailabilityRequest): Response<GetAvailabilityResponse> {
        return userService.getAvailability(getAvailabilityRequest)
    }

    suspend fun addAvailability(addAvailabilityRequest: AddAvailabilityRequest): Response<AddAvailabilityResponse> {
        return userService.addAvailability(addAvailabilityRequest)
    }

    suspend fun filterTutors(tutorFilterRequest: TutorFilterRequest): Response<TutorFilterResponse> {
        return userService.filterTutors(tutorFilterRequest)
    }

    suspend fun createSession(createSessionRequest: CreateSessionRequest): Response<CreateSessionResponse> {
        return userService.createSession(createSessionRequest)
    }

    suspend fun deleteSession(deleteSessionRequest: DeleteSessionRequest): Response<DeleteSessionResponse> {
        return userService.deleteSession(deleteSessionRequest)
    }

}