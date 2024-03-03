package com.example.tutorapp395project.data

data class TutoringSession(
    val tutoring_session_id: Int,
    val tutor_id: Int,
    val student_id: Int,
    val name: String,
    val description: String,
    val subject: String,
    val grade: Int,
    val tutoring_session_status: String,
    val date: String,
    val time_block_id_list: List<Int>
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
