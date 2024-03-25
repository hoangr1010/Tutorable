package com.example.tutorapp395project.viewModel

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tutorapp395project.data.AddAvailabilityRequest
import com.example.tutorapp395project.data.AvailabilityState
import com.example.tutorapp395project.data.DeleteSessionRequest
import com.example.tutorapp395project.data.GetAvailabilityRequest
import com.example.tutorapp395project.data.SessionRequest
import com.example.tutorapp395project.data.SessionResponse
import com.example.tutorapp395project.data.SessionViewState
import com.example.tutorapp395project.data.TutoringSession
import com.example.tutorapp395project.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDate

class TutorViewModel(
    private val userRepository: UserRepository = UserRepository(),
    val authViewModel: AuthViewModel,
): ViewModel() {

    @RequiresApi(Build.VERSION_CODES.O)
    val selectedDate =  mutableStateOf<LocalDate>(LocalDate.now())

    val sessionState = mutableStateOf(SessionViewState())
    val availabilityState = mutableStateOf(AvailabilityState())
    val addAvailabilityState = mutableStateOf<String>("")

    // Session Card state
    val sessionInfoCardShow = mutableStateOf(false)
    val sessionInfo = mutableStateOf(
        TutoringSession(
        tutor_session_id = 0,
        tutor_id = 0,
        student_id = 0,
        tutor_name = "",
        student_name = "",
        name = "",
        description = "",
        subject = "",
        grade = 0,
        tutoring_session_status = "",
        date = "",
        time_block_id_list = listOf()
    )
    )

    fun toggleTimeSlotId(id: Int) {
        val list = availabilityState.value.time_block_id_list?.toMutableList()
        if (list != null) {
            if (id in list) {
                list.remove(id)
            } else {
                list.add(id)
            }
            availabilityState.value = AvailabilityState(
                isLoading = availabilityState.value.isLoading,
                time_block_id_list = list
            )
        }
        Log.d("TutorViewModel", "timeblock: ${availabilityState.value.time_block_id_list}")
    }

    /**
     * Handles the event when a tutoring session is clicked.
     *
     * This function is responsible for updating the state of the application
     * when a tutoring session is selected by the user. It updates the `sessionInfo`
     * state with the selected session and sets the `sessionInfoCardShow` state to true,
     * indicating that the session information card should be displayed.
     *
     * @param session The selected TutoringSession object.
     */
    fun onSessionClick(session: TutoringSession) {
        Log.d("TutorViewModel", "Session clicked: $session")
        Log.d("TutorViewModel", "current session: ${sessionInfo.value}")
        sessionInfo.value = session
        if (session == sessionInfo.value) {
            sessionInfoCardShow.value = true
            return
        }
    }

    /**
     * Deletes a tutoring session.
     *
     * This function is responsible for deleting a tutoring session from the server.
     * It makes a network request to the server with the session ID to delete the session.
     * If the request is successful, it updates the list of sessions by calling `getSessionsForTutor`.
     * It also sets the `sessionInfoCardShow` state to false, indicating that the session information card should be hidden.
     *
     * @param sessionId The ID of the session to be deleted.
     */
    fun deleteSession(sessionId: Int) {
        Log.d("TutorViewModel", "SessionId: $sessionId")

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = userRepository.deleteSession(DeleteSessionRequest(session_id = sessionId))
                Log.d("TutorViewModel", "Response: $response")
                Log.d("TutorViewModel", "Response body for Delete: ${response.body()}")

                if (response.isSuccessful) {
                    val deleteSessionResponse = response.body()
                    if (deleteSessionResponse != null) {
                        Log.d("StudentViewModel", "Session deleted: $sessionId")
                        getSessionsForTutor(
                            role = authViewModel.UserState.value.role,
                            id = authViewModel.UserState.value.id.toInt()
                        )
                        sessionInfoCardShow.value = false
                    }
                } else {
                    Log.d("TutorViewModel", "Error in Deleting Session: ${response.errorBody()}")
                }
            } catch (e: Exception) {
                Log.e("TutorViewModel", "Error in Deleting Session: $e")
            }
        }
    }



    @RequiresApi(Build.VERSION_CODES.O)
    fun resetAvailability() {
        selectedDate.value = LocalDate.now()
        availabilityState.value.time_block_id_list = emptyList()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun saveAvailability(id: String) {
        val addAvailabilityRequest = AddAvailabilityRequest(
            id = id.toInt(),
            date = selectedDate.value.toString(),
            time_block_id_list = availabilityState.value.time_block_id_list
        )
        Log.d("TutorViewModel", "AvailabilityRequest: $addAvailabilityRequest")

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = userRepository.addAvailability(addAvailabilityRequest)
                Log.d("TutorViewModel", "Response: $response")
                Log.d("TutorViewModel", "Response body: ${response.body()}")

                if (response.isSuccessful) {
                    val addAvailabilityResponse = response.body()

                    if (addAvailabilityResponse != null) {
                        addAvailabilityState.value = "✅ Availability successfully added"
                        Log.d("TutorViewModel", "Availability added")
                    }
                } else {
                    addAvailabilityState.value = "❌ Error: ${response.errorBody()}"
                    Log.d("TutorViewModel", "Error in request: ${response.errorBody()}")
                }
            } catch (e: Exception) {
                Log.e("TutorViewModel", "Error: $e")
                addAvailabilityState.value = "❌ Error: $e"

            }
        }
    }

    fun getSessionsForTutor(
        role: String,
        id: Int
    ){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = userRepository.getSessionList(SessionRequest(role = role, id = id))
                Log.d("TutorViewModel", "Response: $response")
                Log.d("TutorViewModel", "Response body: ${response.body()}")

                if (response.isSuccessful) {
                    val sessionResponse: SessionResponse? = response.body()

                    if (sessionResponse != null) {
                        sessionState.value = SessionViewState(
                            isLoading = false,
                            session_list = sessionResponse.tutoring_session_list
                        )
                    }
                } else {
                    Log.d("TutorViewModel", "Error: ${response.errorBody()}")
                }
            } catch (e: Exception) {
                Log.e("TutorViewModel", "Error: $e")
            }
        }
    }

    fun getAvailability(
        id: Int,
        date: String
    ) {
        Log.d("TutorViewModel", "id: $id, date: $date")
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = userRepository.getAvailability(GetAvailabilityRequest(id, date))
                Log.d("TutorViewModel", "Response: $response")
                Log.d("TutorViewModel", "Response body: ${response.body()}")

                if (response.isSuccessful) {
                    val availabilityResponse = response.body()

                    if (availabilityResponse != null) {
                        availabilityState.value = AvailabilityState(
                            isLoading = false,
                            time_block_id_list = availabilityResponse.time_block_id_list ?: emptyList()
                        )
                    }
                } else {
                    Log.d("TutorViewModel", "Error: ${response.errorBody()}")
                }
            } catch (e: Exception) {
                Log.e("TutorViewModel", "Error: $e")
            }
        }
    }

}