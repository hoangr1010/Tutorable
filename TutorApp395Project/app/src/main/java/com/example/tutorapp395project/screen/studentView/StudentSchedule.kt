@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.tutorapp395project.screen.studentView
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tutorapp395project.screen.view.SessionView
import com.example.tutorapp395project.utils.getTimeInterval
import com.example.tutorapp395project.utils.stringToDate
import com.example.tutorapp395project.utils.stringToReadableDate
import com.example.tutorapp395project.viewModel.AuthViewModel
import com.example.tutorapp395project.viewModel.HomeViewModel
import com.example.tutorapp395project.viewModel.StudentViewModel

@Composable
fun StudentAppointmentLayout(
    studentViewModel: StudentViewModel,
    modifier: Modifier = Modifier,
    authViewModel: AuthViewModel,
    homeViewModel: HomeViewModel
) {

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
                        Log.d("StudentAppointmentLayout", "date: ${stringToDate(session.date).toString()}")
                        item {
                            SessionView(
                                time = getTimeInterval(session.time_block_id_list),
                                date = stringToReadableDate(session.date),
                                subject = session.subject,
                                with = "Tutor",
                                person = session.tutor_name,
                                modifier = Modifier.padding(10.dp)
                            )
                        }
                    }
                }
            }
        }

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
    Column (
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xFF00539C))
    ){}
}


@Preview
@Composable
fun PreviewStudentSChedule(){
    BackgroundNoLogo()
    StudentAppointmentLayout(
        studentViewModel = StudentViewModel(authViewModel = AuthViewModel()),
        authViewModel = AuthViewModel(),
        homeViewModel = HomeViewModel()
    )
//    Appointment("3:00PM", "024/02/21","math","Karen McTutor", "Nami" )

}