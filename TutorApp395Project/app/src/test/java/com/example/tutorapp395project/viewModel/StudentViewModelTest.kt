
import com.example.tutorapp395project.data.CreateSessionRequest
import com.example.tutorapp395project.data.CreateSessionResponse
import com.example.tutorapp395project.data.DeleteSessionRequest
import com.example.tutorapp395project.data.DeleteSessionResponse
import com.example.tutorapp395project.data.EditSessionTimeRequest
import com.example.tutorapp395project.data.EditSessionTimeResponse
import com.example.tutorapp395project.data.SessionRequest
import com.example.tutorapp395project.data.SessionResponse
import com.example.tutorapp395project.data.Tutor
import com.example.tutorapp395project.data.TutorFilterRequest
import com.example.tutorapp395project.data.TutorFilterResponse
import com.example.tutorapp395project.repository.UserRepository
import com.example.tutorapp395project.viewModel.AuthViewModel
import com.example.tutorapp395project.viewModel.StudentViewModel
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Response

@RunWith(MockitoJUnitRunner::class)
class StudentViewModelTest {

    @Mock
    private lateinit var studentViewModel: StudentViewModel
    @Mock
    private lateinit var authViewModel: AuthViewModel
    @Mock
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
        val deleteSessionResponse = DeleteSessionResponse(
            time_block_id_list = listOf(),
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

    @Test
    fun editSessionTime_callsUserRepository() = runBlockingTest {
        val sessionId = 1
        val timeBlockId = listOf(1, 2, 3)
        val date = "2024/04/01"
        val editSessionTimeRequest = EditSessionTimeRequest(
            tutor_session_id = sessionId,
            date = date,
            time_block_id_list = timeBlockId
        )
        val editSessionTimeResponse = EditSessionTimeResponse(
            TutorSessionID = 1,
            SessionDate = "2024/04/01",
            time_block_id_list = listOf(1, 2, 3),
        )
        val response = mock(Response::class.java)
        `when`(response.body()).thenReturn(editSessionTimeResponse)

        studentViewModel.editSessionTime()
        verify(userRepository).editSessionTime(editSessionTimeRequest)
    }

    @Test
    fun createNewSession_callsUserRepository() = runBlockingTest {
        val tutorId = 1
        val studentId = 2
        val timeBlockId = listOf(1, 2, 3)
        val date = "2024/04/01"
        val createSessionRequest = CreateSessionRequest(
            tutor_id = tutorId,
            student_id = studentId,
            name = "tommy",
            description = "test",
            subject = "Maths",
            grade = 1,
            date = date,
            time_block_id_list = timeBlockId
        )
        val createSessionResponse = CreateSessionResponse(
            time_block_id_list = listOf(1, 2, 3),
        )
        val response = mock(Response::class.java)
        `when`(response.body()).thenReturn(createSessionResponse)

        studentViewModel.createNewSession()
        verify(userRepository).createSession(createSessionRequest)
    }

    @Test
    fun tutorFilter_callsUserRepository() = runBlockingTest {
        val tutor: Tutor = Tutor(
            id = 1,
            email = "tommy@gmail.com",
            role = "tutor",
            first_name = "tommy",
            last_name = "tut",
            date_of_birth = "2000/04/01",
            expertise = listOf("Maths"),
            verified_status = true,
            experience = "Expert",
            description = "I am a tutor",
            degrees = listOf("BSc"),
            grade = 5,
            school = "UBC"
        )
        val tutorFilterRequest = TutorFilterRequest(
            date = "2024/04/01",
            time_block_id_list = listOf(1, 2, 3)
            )
        val tutorFilterResponse = TutorFilterResponse(
            tutor_list = listOf(tutor)
        )
        val response = mock(Response::class.java)
        `when`(response.body()).thenReturn(tutorFilterResponse)

        studentViewModel.tutorFilter()
        verify(userRepository).filterTutors(tutorFilterRequest)
    }
}

