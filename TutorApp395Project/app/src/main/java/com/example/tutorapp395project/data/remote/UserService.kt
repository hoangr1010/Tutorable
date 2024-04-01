package com.example.tutorapp395project.data.remote

import com.example.tutorapp395project.data.AddAvailabilityRequest
import com.example.tutorapp395project.data.AddAvailabilityResponse
import com.example.tutorapp395project.data.CreateSessionRequest
import com.example.tutorapp395project.data.CreateSessionResponse
import com.example.tutorapp395project.data.DeleteSessionRequest
import com.example.tutorapp395project.data.DeleteSessionResponse
import com.example.tutorapp395project.data.EditSessionTimeRequest
import com.example.tutorapp395project.data.EditSessionTimeResponse
import com.example.tutorapp395project.data.GetAvailabilityRequest
import com.example.tutorapp395project.data.GetAvailabilityResponse
import com.example.tutorapp395project.data.SessionRequest
import com.example.tutorapp395project.data.SessionResponse
import com.example.tutorapp395project.data.TutorFilterRequest
import com.example.tutorapp395project.data.TutorFilterResponse
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

    @POST("search_tutor_availability")
    suspend fun filterTutors(@Body tutorFilterRequest: TutorFilterRequest): Response<TutorFilterResponse>

    @POST("add_tutoring_session")
    suspend fun createSession(@Body createSessionRequest: CreateSessionRequest): Response<CreateSessionResponse>

    @POST("delete_tutoring_session")
    suspend fun deleteSession(@Body deleteSessionRequest: DeleteSessionRequest): Response<DeleteSessionResponse>

    @POST("edit_tutoring_session")
    suspend fun editSessionTime(@Body editSessionTimeRequest: EditSessionTimeRequest): Response<EditSessionTimeResponse>
}