package com.example.tutorapp395project.viewModel

import com.example.tutorapp395project.data.SessionRequest
import com.example.tutorapp395project.data.SessionResponse
import com.example.tutorapp395project.repository.UserRepository
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Response
import java.time.LocalDate

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class TutorViewModelTest {

    @Mock
    private lateinit var tutorRepository: UserRepository
    private lateinit var tutorViewModel: TutorViewModel
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
        val sessionRequest = SessionRequest(role = role, id = id)
        val sessionResponse = SessionResponse(role = role, id = id)
        `when`(tutorRepository.getSessionList(sessionRequest)).thenReturn(Response.success(sessionResponse))

        tutorViewModel.getSessionsForTutor(role, id)

        assertEquals(sessionResponse.tutoring_session_list, tutorViewModel.sessionState.value.session_list)
    }

    @Test
    fun selectedDate_initialState() {
        // Check initial state
        assertEquals(LocalDate.now(), tutorViewModel.selectedDate.value)
    }
}