package com.example.tutorapp395project.data

data class TutoringSession(
    val tutor_session_id: Int,
    val tutor_id: Int,
    val student_id: Int,
    val tutor_name: String,
    val student_name: String,
    val name: String,
    val description: String,
    val subject: String,
    val grade: Int,
    val tutoring_session_status: String,
    val date: String,
    val time_block_id_list: List<Int>,
    val student_email: String,
    val tutor_email: String
)

data class SessionRequest(
    val role: String,
    val id: Int
)

data class SessionResponse(
    val id: Int,
    val role: String,
    val tutoring_session_list: List<TutoringSession>? = listOf()
)

data class SessionViewState(
    val isLoading: Boolean = true,
    val session_list: List<TutoringSession>? = listOf()
)

data class TutorFilterRequest (
    val date: String = "",
    val time_block_id_list: List<Int> = listOf(),
)

data class TutorFilterResponse (
    val tutor_list: List<Tutor>? = listOf()
)

data class CreateSessionRequest(
    val tutor_id: Int,
    val student_id: Int,
    val name: String,
    val description: String,
    val subject: String,
    val grade: Int,
    val date: String,
    val time_block_id_list: List<Int>
)

data class CreateSessionResponse(
    val time_block_id_list: List<Int>
)

data class DeleteSessionRequest(
    val session_id: Int
)

data class DeleteSessionResponse(
    val time_block_id_list: List<Int>,
    val session_id_deleted: Int
)

data class EditSessionTimeRequest(
    val tutor_session_id: Int,
    val date: String,
    val time_block_id_list: List<Int>
)

data class EditSessionTimeResponse(
    val TutorSessionID: Int,
    val SessionDate: String,
    val time_block_id_list: List<Int>
)
