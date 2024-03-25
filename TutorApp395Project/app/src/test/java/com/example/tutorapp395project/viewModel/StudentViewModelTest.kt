
import com.example.tutorapp395project.data.SessionRequest
import com.example.tutorapp395project.data.SessionResponse
import com.example.tutorapp395project.data.TutoringSession
import com.example.tutorapp395project.repository.UserRepository
import com.example.tutorapp395project.viewModel.AuthViewModel
import com.example.tutorapp395project.viewModel.StudentViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Response

@ExperimentalCoroutinesApi
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
        `when`(userRepository.getSessionList(sessionRequest)).thenReturn(Response.success(sessionResponse))

        studentViewModel.getSessionsForStudent(role, id)
        verify(userRepository).getSessionList(sessionRequest)
    }

    @Test
    fun onSessionClick_updatesState() {
        // Arrange
        val session = TutoringSession(
            tutor_session_id = 1,
            tutor_id = 1,
            student_id = 1,
            tutor_name = "Tutor",
            student_name = "Student",
            name = "Session",
            description = "Description",
            subject = "Subject",
            grade = 1,
            tutoring_session_status = "Status",
            date = "Date",
            time_block_id_list = listOf(1)
        )

        // Act
        studentViewModel.onSessionClick(session)

        // Assert
        assertEquals(session, studentViewModel.sessionInfo.value)
        assertTrue(studentViewModel.sessionInfoCardShow.value)
    }
}
