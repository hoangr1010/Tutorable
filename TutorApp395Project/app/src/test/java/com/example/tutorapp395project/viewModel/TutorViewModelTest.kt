package com.example.tutorapp395project.viewModel

import com.example.tutorapp395project.data.AddAvailabilityRequest
import com.example.tutorapp395project.data.AddAvailabilityResponse
import com.example.tutorapp395project.data.AvailabilityState
import com.example.tutorapp395project.data.DeleteSessionRequest
import com.example.tutorapp395project.data.DeleteSessionResponse
import com.example.tutorapp395project.data.SessionResponse
import com.example.tutorapp395project.repository.UserRepository
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Response
import java.time.LocalDate

@RunWith(MockitoJUnitRunner::class)
class TutorViewModelTest {

    @Mock
    private lateinit var tutorRepository: UserRepository
    @Mock
    private lateinit var tutorViewModel: TutorViewModel
    @Mock
    private lateinit var authViewModel: AuthViewModel

    @Before
    fun setUp() {
        authViewModel = AuthViewModel()
        tutorViewModel = TutorViewModel(tutorRepository, authViewModel)
    }

    @Test
    fun getSessionsForTutor_updatesState() = runBlockingTest {
        val role = "tutor"
        val id = 1
        val sessionResponse = SessionResponse(role = role, id = id)

        tutorViewModel.getSessionsForTutor(role, id)

        assertEquals(
            sessionResponse.tutoring_session_list,
            tutorViewModel.sessionState.value.session_list
        )
    }

    @Test
    fun selectedDate_initialState() {
        // Check initial state
        assertEquals(LocalDate.now(), tutorViewModel.selectedDate.value)
    }

    @Test
    fun selectedDate_updatesState() {
        // Check initial state
        assertEquals(LocalDate.now(), tutorViewModel.selectedDate.value)

        // Update state
        val newDate = LocalDate.of(2022, 1, 1)
        tutorViewModel.selectedDate.value = newDate

        // Check updated state
        assertEquals(newDate, tutorViewModel.selectedDate.value)
    }

    @Test
    fun resetAvailability_State() {
        // Set initial state
        tutorViewModel.availabilityState.value = AvailabilityState(
            isLoading = true,
            time_block_id_list = listOf(1, 2, 3)
        )

        // Reset state
        tutorViewModel.resetAvailability()

        // Check reset state
        assertEquals(AvailabilityState(), tutorViewModel.availabilityState.value)
    }

    @Test
    fun deleteSession_callsUserRepository() = runBlockingTest {
        val sessionId = 1
        val deleteSessionRequest = DeleteSessionRequest(session_id = sessionId)
        val deleteSessionResponse = DeleteSessionResponse(
            time_block_id_list = listOf(1, 2, 3),
            session_id_deleted = sessionId
        )
        val response = mock(Response::class.java)
        `when`(response.body()).thenReturn(deleteSessionResponse)

        tutorViewModel.deleteSession(sessionId)
        verify(tutorRepository).deleteSession(deleteSessionRequest)
    }

    @Test
    fun deleteSession_updatesState() = runBlockingTest {
        val sessionId = 1
        val deleteSessionRequest = DeleteSessionRequest(session_id = sessionId)
        val deleteSessionResponse = DeleteSessionResponse(
            time_block_id_list = listOf(1, 2, 3),
            session_id_deleted = sessionId
        )
        val response = Response.success(deleteSessionResponse)

        `when`(tutorRepository.deleteSession(deleteSessionRequest)).thenReturn(response)

        tutorViewModel.deleteSession(sessionId)

        assertEquals(
            deleteSessionResponse.time_block_id_list,
            tutorViewModel.availabilityState.value.time_block_id_list
        )
    }

    @Test
    fun saveAvailability_callsUserRepository() = runBlockingTest {
        val id = "1"
        val addAvailabilityRequest = AddAvailabilityRequest(
            id = id.toInt(),
            date = LocalDate.now().toString(),
            time_block_id_list = listOf(1, 2, 3)
        )
        val addAvailabilityResponse = AddAvailabilityResponse(
            date = LocalDate.now().toString(),
            time_block_id_list = listOf(1, 2, 3)
        )
        val response = Response.success(addAvailabilityResponse)

        `when`(tutorRepository.addAvailability(addAvailabilityRequest)).thenReturn(response)
        tutorViewModel.saveAvailability(id)
        verify(tutorRepository).addAvailability(addAvailabilityRequest)
    }

    @Test
    fun getAvailability_updatesState() = runBlockingTest {
        val id = "1"
        val addAvailabilityRequest = AddAvailabilityRequest(
            id = id.toInt(),
            date = LocalDate.now().toString(),
            time_block_id_list = listOf(1, 2, 3)
        )
        val addAvailabilityResponse = AddAvailabilityResponse(
            date = LocalDate.now().toString(),
            time_block_id_list = listOf()
        )
        val response = Response.success(addAvailabilityResponse)

        `when`(tutorRepository.addAvailability(addAvailabilityRequest)).thenReturn(response)
        tutorViewModel.saveAvailability(id)

        assertEquals(
            addAvailabilityResponse.time_block_id_list,
            tutorViewModel.availabilityState.value.time_block_id_list
        )
    }

    @Test
    fun toggleTimeSlot_updatesState() {
        val timeBlockId = 1
        val timeBlockIdList = listOf(1)

        tutorViewModel.toggleTimeSlotId(timeBlockId)

        assertEquals(
            timeBlockIdList,
            tutorViewModel.availabilityState.value.time_block_id_list
        )
    }






}