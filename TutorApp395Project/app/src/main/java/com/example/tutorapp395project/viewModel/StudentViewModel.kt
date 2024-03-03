package com.example.tutorapp395project.viewModel

import android.graphics.PointF.length
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tutorapp395project.data.SessionRequest
import com.example.tutorapp395project.data.SessionResponse
import com.example.tutorapp395project.data.SessionViewState
import com.example.tutorapp395project.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDate
import kotlin.math.abs

class StudentViewModel(
    private val userRepository: UserRepository = UserRepository(),
    authViewModel: AuthViewModel
): ViewModel() {

    init {
//        getSessionsForStudent(
//            role = authViewModel.UserState.value.role,
//            id = authViewModel.UserState.value.id.toInt()
//        )
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
            // In case the length of the set is greater than 1, not allow to add more than 1 time slot
            if (set.size > 1) {
                return
            } else if (set.size == 1 && abs(set.toList()[0] - id) > 1) (
                    // In case new time block is not consecutive to the existing time block, not allow to add
                    return
                    ) else {
                set.add(id)
            }
        }
        selectedTimeSlotIdsSet.value = set
    }

    private fun getSessionsForStudent(
        role: String,
        id: Int
    ){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = userRepository.getSessionList(SessionRequest(role = role, id = id))
                Log.d("StudentViewModel", "Response: $response")
                Log.d("StudentViewModel", "Response body: ${response.body()}")

                if (response.isSuccessful) {
                    val sessionResponse: SessionResponse? = response.body()

                    if (sessionResponse != null) {
                        sessionState.value = SessionViewState(
                            isLoading = false,
                            session_list = sessionResponse.tutoring_session_list
                        )
                    }
                } else {
                    Log.d("StudentViewModel", "Error: ${response.errorBody()}")
                }
            } catch (e: Exception) {
                Log.e("StudentViewModel", "Error: $e")
            }
        }
    }


}