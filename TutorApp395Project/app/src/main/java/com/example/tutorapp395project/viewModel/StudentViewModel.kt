package com.example.tutorapp395project.viewModel

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tutorapp395project.data.CreateSessionRequest
import com.example.tutorapp395project.data.DeleteSessionRequest
import com.example.tutorapp395project.data.SessionRequest
import com.example.tutorapp395project.data.SessionResponse
import com.example.tutorapp395project.data.SessionViewState
import com.example.tutorapp395project.data.Tutor
import com.example.tutorapp395project.data.TutorFilterRequest
import com.example.tutorapp395project.data.TutoringSession
import com.example.tutorapp395project.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.time.LocalDate
import kotlin.math.abs

class StudentViewModel(
    private val userRepository: UserRepository = UserRepository(),
    val authViewModel: AuthViewModel
) : ViewModel() {

    // Date and time slot state
    @RequiresApi(Build.VERSION_CODES.O)
    val selectedDate = mutableStateOf<LocalDate>(LocalDate.now())
    val selectedTimeSlotIdsSet = mutableStateOf<Set<Int>>(setOf())

    // List of sessions state
    val sessionState = mutableStateOf(SessionViewState())

    // List of tutor filter dialog state
    val tutorFilterDialogState = mutableStateOf(TutorFilterDialogState())

    // Session Card state
    val sessionInfoCardShow = mutableStateOf(false)
    val sessionInfo = mutableStateOf(TutoringSession(
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
    ))

    // Session form state
    val sessionFormOpen = mutableStateOf(false)
    private val sessionTutorSelected = mutableStateOf(
        Tutor(
            id = 0,
            email = "",
            role = "",
            first_name = "",
            last_name = "",
            date_of_birth = "",
            expertise = listOf(),
            verified_status = false,
            experience = "",
            description = "",
            degrees = listOf(),
            grade = 0,
            school = ""
        )
    )
    val sessionName = mutableStateOf("")
    val sessionDescription = mutableStateOf("")

    // Creating session button state
    val createSessionButtonState = mutableStateOf("")

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
        Log.d("StudentViewModel", "Session clicked: $session")
        Log.d("StudentViewModel", "current session: ${sessionInfo.value}")
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
     * If the request is successful, it updates the list of sessions by calling `getSessionsForStudent`.
     * It also sets the `sessionInfoCardShow` state to false, indicating that the session information card should be hidden.
     *
     * @param sessionId The ID of the session to be deleted.
     */
    fun deleteSession(sessionId: Int) {
        Log.d("StudentViewModel", "SessionId: $sessionId")

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = userRepository.deleteSession(DeleteSessionRequest(session_id = sessionId))
                Log.d("StudentViewModel", "Response: $response")
                Log.d("StudentViewModel", "Response body for Delete: ${response.body()}")

                if (response.isSuccessful) {
                    val deleteSessionResponse = response.body()
                    if (deleteSessionResponse != null) {
                        Log.d("StudentViewModel", "Session deleted: $sessionId")
                        getSessionsForStudent(
                            role = authViewModel.UserState.value.role,
                            id = authViewModel.UserState.value.id.toInt()
                        )
                        sessionInfoCardShow.value = false
                    }
                } else {
                    Log.d("StudentViewModel", "Error in Deleting Session: ${response.errorBody()}")
                }
            } catch (e: Exception) {
                Log.e("StudentViewModel", "Error in Deleting Session: $e")
            }
        }
    }

    fun getSessionsForStudent(
        role: String,
        id: Int
    ) {
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

    @RequiresApi(Build.VERSION_CODES.O)
    fun tutorFilter() {
        val tutorFilterRequest = TutorFilterRequest(
            date = selectedDate.value.toString(),
            time_block_id_list = selectedTimeSlotIdsSet.value.toList()
        )

        Log.d("StudentViewModel", "TutorFilterRequest: $tutorFilterRequest")

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = userRepository.filterTutors(tutorFilterRequest)
                Log.d("StudentViewModel", "Response: $response")
                Log.d("StudentViewModel", "Response body: ${response.body()}")

                if (response.isSuccessful) {
                    val tutorFilterResponse = response.body()

                    if (tutorFilterResponse != null) {
                        tutorFilterDialogState.value = tutorFilterDialogState.value.copy(
                            isOpen = true,
                            tutor_list = tutorFilterResponse.tutor_list ?: listOf()
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

    fun selectTutor(tutor: Tutor) {
        sessionTutorSelected.value = tutor
        tutorFilterDialogState.value = tutorFilterDialogState.value.copy(isOpen = false)
        sessionFormOpen.value = true
        Log.d("StudentViewModel", "Selected tutorId: ${sessionTutorSelected.value.id}")
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun createNewSession() {
        Log.d(
            "StudentViewModel",
            "name: $sessionName, description: $sessionDescription, date: ${sessionName.value}, time_block_id_list: ${sessionDescription.value}"
        )

        val createSessionRequest = CreateSessionRequest(
            tutor_id = sessionTutorSelected.value.id,
            student_id = authViewModel.UserState.value.id.toInt(),
            name = sessionName.value,
            description = sessionDescription.value,
            subject = sessionTutorSelected.value.expertise[0],
            grade = authViewModel.UserState.value.grade,
            date = selectedDate.value.toString(),
            time_block_id_list = selectedTimeSlotIdsSet.value.toList()

        )
        Log.d("StudentViewModel", "CreateSessionRequest: $createSessionRequest")
        createSessionButtonState.value = "loading"

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = userRepository.createSession(createSessionRequest)
                Log.d("StudentViewModel", "Response: $response")
                Log.d("StudentViewModel", "Response body: ${response.body()}")

                if (response.isSuccessful) {
                    val createSessionResponse = response.body()
                    if (createSessionResponse != null) {
                        Log.d(
                            "StudentViewModel",
                            "Session created with time block: ${createSessionResponse.time_block_id_list}"
                        )
                        getSessionsForStudent(
                            role = authViewModel.UserState.value.role,
                            id = authViewModel.UserState.value.id.toInt()
                        )
                        createSessionButtonState.value = "success"

                        // Reset state of the form
                        sessionDescription.value = ""
                        sessionName.value = ""
                        selectedTimeSlotIdsSet.value = setOf()
                        sessionTutorSelected.value = Tutor(
                            id = 0,
                            email = "",
                            role = "",
                            first_name = "",
                            last_name = "",
                            date_of_birth = "",
                            expertise = listOf(),
                            verified_status = false,
                            experience = "",
                            description = "",
                            degrees = listOf(),
                            grade = 0,
                            school = ""
                        )

                        delay(2000)

                    }
                } else {
                    Log.d("StudentViewModel", "Error in Creating Session: ${response.errorBody()}")
                }
            } catch (e: Exception) {
                Log.e("StudentViewModel", "Error in Creating Session: $e")
            }

            createSessionButtonState.value = ""
            tutorFilterDialogState.value = tutorFilterDialogState.value.copy(isOpen = false)
            sessionFormOpen.value = false
        }
    }
}

data class TutorFilterDialogState(
    val isOpen: Boolean = false,
    val tutor_list: List<Tutor>? = listOf()
)