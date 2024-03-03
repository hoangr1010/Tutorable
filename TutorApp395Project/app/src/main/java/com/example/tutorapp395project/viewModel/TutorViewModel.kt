package com.example.tutorapp395project.viewModel

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tutorapp395project.data.AddAvailabilityRequest
import com.example.tutorapp395project.data.AvailabilityState
import com.example.tutorapp395project.data.GetAvailabilityRequest
import com.example.tutorapp395project.data.SessionRequest
import com.example.tutorapp395project.data.SessionResponse
import com.example.tutorapp395project.data.SessionViewState
import com.example.tutorapp395project.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.util.Date

class TutorViewModel(
    private val userRepository: UserRepository = UserRepository(),
    authViewModel: AuthViewModel
): ViewModel() {

    init {
//        getSessionsForTutor(
//            role = authViewModel.UserState.value.role,
//            id = authViewModel.UserState.value.id.toInt()
//        )
    }

    @RequiresApi(Build.VERSION_CODES.O)
    val selectedDate =  mutableStateOf<LocalDate>(LocalDate.now())

    val sessionState = mutableStateOf(SessionViewState())
    val availabilityState = mutableStateOf(AvailabilityState())
    val addAvailabilityState = mutableStateOf<String>("")

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

    private fun getSessionsForTutor(
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