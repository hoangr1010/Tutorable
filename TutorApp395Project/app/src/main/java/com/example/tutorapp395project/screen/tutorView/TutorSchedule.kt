package com.example.tutorapp395project.screen.tutorView

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.test.espresso.intent.Intents.init
import com.example.tutorapp395project.screen.view.SessionView
import com.example.tutorapp395project.utils.getTimeInterval
import com.example.tutorapp395project.utils.stringToDate
import com.example.tutorapp395project.utils.stringToReadableDate
import com.example.tutorapp395project.viewModel.AuthViewModel
import com.example.tutorapp395project.viewModel.TutorViewModel

/*
    Function: This creates a column that lays out all the users scheduled appointments
    Parameters: modifier -> takes modifier parameters
    Return: None
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TutorAppointmentLayout(
    modifier: Modifier = Modifier,
    tutorViewModel: TutorViewModel,
) {

    LazyColumn(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
            .padding(top = 10.dp)
            .background(color = Color(0xFF00539C))
    ) {
        Log.d("TutorAppointmentLayout", "sessionState: ${tutorViewModel.sessionState.value}")
        if (tutorViewModel.sessionState.value.isLoading) {
            item {
                CircularProgressIndicator(color = Color.White)
            }
        } else {
            if (tutorViewModel.sessionState.value.session_list.isNullOrEmpty()) {
                item {
                    Text(
                        text = "No appointments scheduled",
                        color = Color.White
                    )
                }
            } else {
                tutorViewModel.sessionState.value.session_list?.forEach { session ->
                    Log.d("TutorAppointmentLayout", "date: ${stringToDate(session.date).toString()}")
                    item {
                        SessionView(
                            time = getTimeInterval(session.time_block_id_list),
                            date = stringToReadableDate(session.date),
                            subject = session.subject,
                            with = "With",
                            person = session.student_id.toString(),
                            modifier = Modifier.padding(10.dp)
                        )
                    }
                }
            }
        }

    }
}



@Preview
@Composable
fun previewTutorSchedule(){
    TutorAppointmentLayout(
        tutorViewModel = TutorViewModel(
            authViewModel = AuthViewModel()
        )
    )
}