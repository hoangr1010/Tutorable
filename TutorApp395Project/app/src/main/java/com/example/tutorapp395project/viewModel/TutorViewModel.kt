package com.example.tutorapp395project.viewModel

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tutorapp395project.data.AvailabilityRequest
import com.example.tutorapp395project.data.SessionRequest
import com.example.tutorapp395project.data.SessionResponse
import com.example.tutorapp395project.data.SessionViewState
import com.example.tutorapp395project.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDate

class TutorViewModel(
    private val userRepository: UserRepository = UserRepository(),
    authViewModel: AuthViewModel
): ViewModel() {

    init {
        getSessionsForTutor(
            role = authViewModel.UserState.value.role,
            id = authViewModel.UserState.value.id.toInt()
        )
    }

    @RequiresApi(Build.VERSION_CODES.O)
    val selectedDate =  mutableStateOf<LocalDate>(LocalDate.now())
    val selectedTimeSlotIdsSet = mutableStateOf<Set<Int>>(setOf())

    val sessionState = mutableStateOf(SessionViewState())

    fun toggleTimeSlotId(id: Int) {
        val set = selectedTimeSlotIdsSet.value.toMutableSet()
        if (id in set) {
            set.remove(id)
        } else {
            set.add(id)
        }
        selectedTimeSlotIdsSet.value = set
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun resetAvailability() {
        selectedDate.value = LocalDate.now()
        selectedTimeSlotIdsSet.value = setOf()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun saveAvailability(id: String) {
        // save to database
        val availabilityRequest = AvailabilityRequest(
            id = id,
            date = selectedDate.value.toString(),
            time_block_id_list = selectedTimeSlotIdsSet.value.toList()
        )
        Log.d("TutorViewModel", "AvailabilityRequest: $availabilityRequest")
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

}