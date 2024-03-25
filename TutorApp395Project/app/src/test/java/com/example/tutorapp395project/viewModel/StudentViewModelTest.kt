
import com.example.tutorapp395project.data.DeleteSessionRequest
import com.example.tutorapp395project.data.DeleteSessionResponse
import com.example.tutorapp395project.data.SessionRequest
import com.example.tutorapp395project.data.SessionResponse
import com.example.tutorapp395project.repository.UserRepository
import com.example.tutorapp395project.viewModel.AuthViewModel
import com.example.tutorapp395project.viewModel.StudentViewModel
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Response

@RunWith(MockitoJUnitRunner::class)
class StudentViewModelTest {

    private lateinit var studentViewModel: StudentViewModel
    private lateinit var authViewModel: AuthViewModel
    private lateinit var userRepository: UserRepository

    @Before
    fun setUp() {
        userRepository = mock(UserRepository::class.java)
        authViewModel = mock(AuthViewModel::class.java)
        studentViewModel = StudentViewModel(userRepository, authViewModel)
    }

    @Test
    fun toggleTimeSlotId_addsIdToSet() {
        studentViewModel.toggleTimeSlotId(1)
        assertEquals(setOf(1), studentViewModel.selectedTimeSlotIdsSet.value)
    }

    @Test
    fun toggleTimeSlotId_removesIdFromSet() {
        studentViewModel.toggleTimeSlotId(1)
        studentViewModel.toggleTimeSlotId(1)
        assertEquals(setOf<Int>(), studentViewModel.selectedTimeSlotIdsSet.value)
    }

    @Test
    fun toggleTimeSlotId_updatesState() {
        // Initial state
        assertEquals(setOf<Int>(), studentViewModel.selectedTimeSlotIdsSet.value)

        // Toggle on
        studentViewModel.toggleTimeSlotId(1)
        assertEquals(setOf(1), studentViewModel.selectedTimeSlotIdsSet.value)

        // Toggle off
        studentViewModel.toggleTimeSlotId(1)
        assertEquals(setOf<Int>(), studentViewModel.selectedTimeSlotIdsSet.value)
    }

    @Test
    fun getSessionsForStudent_callsUserRepository() = runBlockingTest {
        val role = "student"
        val id = 1
        val sessionRequest = SessionRequest(role = role, id = id)
        val sessionResponse = mock(SessionResponse::class.java)
        `when`(userRepository.getSessionList(sessionRequest)).thenReturn(
            Response.success(
                sessionResponse
            )
        )

        studentViewModel.getSessionsForStudent(role, id)
        verify(userRepository).getSessionList(sessionRequest)
    }

    @Test
    fun getSessionsForStudent_updatesState() = runBlockingTest {
        val role = "student"
        val id = 1
        val sessionRequest = SessionRequest(role = role, id = id)
        val sessionResponse = SessionResponse(role = role, id = id)
        `when`(userRepository.getSessionList(sessionRequest)).thenReturn(
            Response.success(
                sessionResponse
            )
        )

        studentViewModel.getSessionsForStudent(role, id)
        assertEquals(
            sessionResponse.tutoring_session_list,
            studentViewModel.sessionState.value.session_list
        )
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

        studentViewModel.deleteSession(sessionId)
        verify(userRepository).deleteSession(deleteSessionRequest)
    }

    @Test
    fun deleteSession_updatesState() = runBlockingTest {
        val sessionId = 1
        val deleteSessionRequest = DeleteSessionRequest(session_id = sessionId)
        val deleteSessionResponse = DeleteSessionResponse(
            time_block_id_list = listOf(1, 2, 3),
            session_id_deleted = sessionId
        )
        val response = mock(Response::class.java)
        `when`(response.body()).thenReturn(deleteSessionResponse)

        studentViewModel.deleteSession(sessionId)
        assertEquals(
            deleteSessionResponse.time_block_id_list,
            studentViewModel.sessionInfo.value.time_block_id_list
        )
    }
}

