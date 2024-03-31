@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.tutorapp395project.screen.studentView

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.tutorapp395project.screen.TimeSlotSessionView
import com.example.tutorapp395project.screen.view.SessionInfoDialog
import com.example.tutorapp395project.screen.view.SessionView
import com.example.tutorapp395project.utils.getTimeInterval
import com.example.tutorapp395project.utils.stringToReadableDate
import com.example.tutorapp395project.viewModel.AuthViewModel
import com.example.tutorapp395project.viewModel.HomeViewModel
import com.example.tutorapp395project.viewModel.StudentViewModel
import com.example.tutorapp395project.viewModel.TutorViewModel
import com.maxkeppeker.sheets.core.models.base.Header
import com.maxkeppeker.sheets.core.models.base.UseCaseState
import com.maxkeppeker.sheets.core.models.base.rememberUseCaseState
import com.maxkeppeler.sheets.calendar.CalendarDialog
import com.maxkeppeler.sheets.calendar.models.CalendarConfig
import com.maxkeppeler.sheets.calendar.models.CalendarSelection
import com.maxkeppeler.sheets.calendar.models.CalendarStyle

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun StudentAppointmentLayout(
    studentViewModel: StudentViewModel,
    modifier: Modifier = Modifier,
    authViewModel: AuthViewModel,
    homeViewModel: HomeViewModel,
) {
    val timeSlotDialogOpen = studentViewModel.timeSlotDialogOpen

    val calendarState = rememberUseCaseState(
        onFinishedRequest = {
            timeSlotDialogOpen.value = true
            studentViewModel.sessionInfoCardShow.value = false

            studentViewModel.getAvailability(
                id = studentViewModel.tutorId.value,
                date = studentViewModel.selectedDate.value.toString()
            )
        }
    )

    if (homeViewModel.viewState.value == "schedule") {
        studentViewModel.getSessionsForStudent(
            role = authViewModel.UserState.value.role,
            id = authViewModel.UserState.value.id.toInt()
        )
    }

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(15.dp, Alignment.Top),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
            .background(color = Color(0xFF00539C))

    ) {

        item {
            CalendarDialog(
                state = calendarState,
                header = Header.Default(title = "Select a Date"),
                config = CalendarConfig(
                    yearSelection = true,
                    monthSelection = true,
                    style = CalendarStyle.MONTH,
                ),
                selection = CalendarSelection.Date { newDate ->
                    studentViewModel.selectedDate.value = newDate
                }
            )
        }

        if (timeSlotDialogOpen.value) {
            item {
                Dialog(onDismissRequest = { timeSlotDialogOpen.value = false }) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth(0.9f)
                            .fillMaxHeight(0.8f),
                        colors = CardColors(
                            containerColor = Color(0xFFCCE7FF),
                            contentColor = Color(0xFFEEA47F),
                            disabledContainerColor = Color(0xFFD3D3D3),
                            disabledContentColor = Color(0xFFA9A9A9),
                        )
                    ) {
                        Column() {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "Select 1 or 2 consecutive time slots",
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(10.dp)
                                )
                            }

                            TimeSlotSessionView(
                                studentViewModel = studentViewModel,
                                modifier = Modifier.weight(1f)
                            )

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(10.dp),
                                horizontalArrangement = Arrangement.SpaceAround
                            ) {
                                Button(
                                    onClick = {
                                        timeSlotDialogOpen.value = false
                                    },
                                    colors = ButtonDefaults.buttonColors(Color.Gray)
                                ) {
                                    Text("Cancel")
                                }

                                Button(
                                    onClick = {
                                        studentViewModel.editSessionTime()
                                    },
                                    colors = ButtonDefaults.buttonColors(Color(0xFFEEA47F))

                                ) {
                                    Text("Edit")
                                }
                            }
                        }
                    }
                }
            }
        }

        if (studentViewModel.sessionState.value.isLoading) {
            item {
                CircularProgressIndicator(color = Color.White)
            }
        } else {
            if (studentViewModel.sessionState.value.session_list.isNullOrEmpty()) {
                item {
                    Text(
                        text = "No appointments scheduled",
                        color = Color.White
                    )
                }
            } else {
                studentViewModel.sessionState.value.session_list?.forEach { session ->
                    Log.d("StudentAppointmentLayout", "session: $session")



                    item {
                        SessionView(
                            time = getTimeInterval(session.time_block_id_list),
                            date = stringToReadableDate(session.date),
                            subject = session.subject,
                            with = "Tutor",
                            person = session.tutor_name,
                            onClick = {
                                studentViewModel.onSessionClick(session = session)
                                studentViewModel.tutorId.value = session.tutor_id
                            },
                            modifier = Modifier.padding(10.dp)
                        )

                        if (studentViewModel.sessionInfoCardShow.value) {
                            SessionInfoDialog(
                                sessionId = studentViewModel.sessionInfo.value.tutor_session_id,
                                tutorName = studentViewModel.sessionInfo.value.tutor_name,
                                subject = studentViewModel.sessionInfo.value.subject,
                                dateIn = stringToReadableDate(studentViewModel.sessionInfo.value.date),
                                timeslot = getTimeInterval(studentViewModel.sessionInfo.value.time_block_id_list),

                                onDismiss = {
                                    studentViewModel.sessionInfoCardShow.value = false
                                    studentViewModel.isEditing.value = false
                                },

                                onDelete = {
                                    studentViewModel.deleteSession(
                                        studentViewModel.sessionInfo.value.tutor_session_id
                                    )
                                },
                                calendarState = calendarState,
                                studentViewModel = studentViewModel
                            )
                        }
                    }
                }
            }
        }
    }

}

fun SessionInfoDialog(
    sessionId: Int,
    tutorName: String,
    subject: String,
    dateIn: String,
    timeslot: String,
    onDismiss: () -> Unit,
    onDelete: () -> Unit,
    calendarState: UseCaseState,
    studentViewModel: StudentViewModel
) {

}

/*
    Function: This shows the scheduled appointment on the users schedule page
    Parameters: time -> time in which the appointment is scheduled
                date -> date for the session
                subject -> subject for the tutor session
                with -> tutor or student
                person -> name of the person
                modifier -> takes modifier parameters
    Return: None
 */

/*
    Function: Creates just a simple blue background without a logo
    Parameters: modifier -> takes modifier parameters
    Return: None
 */

@Composable
fun BackgroundNoLogo(modifier: Modifier = Modifier) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xFF00539C))
    ) {}
}


//@Preview
//@Composable
//fun PreviewStudentSchedule() {
//    BackgroundNoLogo()
//    StudentAppointmentLayout(
//        studentViewModel = StudentViewModel(authViewModel = AuthViewModel()),
//        authViewModel = AuthViewModel(),
//        homeViewModel = HomeViewModel()
//    )
////    Appointment("3:00PM", "024/02/21","math","Karen McTutor", "Nami" )
//
//}