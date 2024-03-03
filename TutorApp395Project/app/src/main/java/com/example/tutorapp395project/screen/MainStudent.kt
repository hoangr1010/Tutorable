package com.example.tutorapp395project.screen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.tutorapp395project.R
import com.example.tutorapp395project.data.TimeSlots
import com.example.tutorapp395project.screen.view.HomeBar
import com.example.tutorapp395project.screen.view.SettingsColumn
import com.example.tutorapp395project.screen.studentView.StudentAppointmentLayout
import com.example.tutorapp395project.screen.studentView.StudentProfileColumn
import com.example.tutorapp395project.viewModel.AuthViewModel
import com.example.tutorapp395project.viewModel.HomeViewModel
import com.example.tutorapp395project.viewModel.StudentViewModel
import com.example.tutorapp395project.viewModel.TutorViewModel
import com.maxkeppeker.sheets.core.models.base.Header
import com.maxkeppeker.sheets.core.models.base.rememberUseCaseState
import com.maxkeppeler.sheets.calendar.CalendarDialog
import com.maxkeppeler.sheets.calendar.models.CalendarConfig
import com.maxkeppeler.sheets.calendar.models.CalendarSelection
import com.maxkeppeler.sheets.calendar.models.CalendarStyle

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainStudent(
    navController: NavController,
    authViewModel: AuthViewModel,
    homeViewModel: HomeViewModel = viewModel(),
    studentViewModel: StudentViewModel = viewModel(),
) {


    val dialogState = remember { mutableStateOf(false) }

    val calendarState = rememberUseCaseState(
        onFinishedRequest = { dialogState.value = true }
    )

    Scaffold(
        containerColor = Color(0xFF00539C),
        bottomBar = { HomeBar(navController = navController, homeViewModel = homeViewModel) },
        floatingActionButton = {
            if (homeViewModel.viewState.value == "schedule") {
                FloatingActionButton(onClick = { calendarState.show() },
                    containerColor = Color(0xFF66B8FF),
                    contentColor = Color(0xFFCCE7FF),
                    shape = MaterialTheme.shapes.large,
                ) {
                    Row (
                        modifier = Modifier.padding(8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(2.dp)
                    ){
                        Icon(Icons.Default.Add, contentDescription = "Add")
                        Text(text = "Session")
                    }
                }
            }
        },

        content = {

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

            if (dialogState.value) {
                Dialog(onDismissRequest = { dialogState.value = false }) {
                    Card (
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

                            TimeSlotSessionView(studentViewModel = studentViewModel, modifier = Modifier.weight(1f))

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(10.dp),
                                horizontalArrangement = Arrangement.SpaceAround
                            ) {
                                Button(
                                    onClick = {
                                        dialogState.value = false
                                    },
                                    colors = ButtonDefaults.buttonColors(Color.Gray)
                                ) {
                                    Text("Cancel")
                                }

                                Button(
                                    onClick = { },
                                    colors = ButtonDefaults.buttonColors(Color(0xFFEEA47F))

                                ) {
                                    Text("Save")
                                }
                            }
                        }
                    }
                }

            }


            when {
                homeViewModel.viewState.value == "profile" -> {
                    StudentProfileColumn(
                        image = R.drawable.user_image,
                        authViewModel = authViewModel,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(it)
                    )

                }
                homeViewModel.viewState.value == "schedule" -> {
                    StudentAppointmentLayout(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(it)
                    )
                }
                homeViewModel.viewState.value == "setting" -> {
                    SettingsColumn(
                        navController = navController,
                        authViewModel = authViewModel,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(it)
                    )
                }
            }
        }
    )
}

@Composable
fun TimeSlotSessionView(
    studentViewModel: StudentViewModel,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .padding(start = 10.dp, end = 10.dp)
    ) {
        items (TimeSlots.slots) { timeSlot ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(5.dp, MaterialTheme.shapes.medium, true)
                    .clip(MaterialTheme.shapes.medium)
                    .background(
                        color = if (timeSlot.id in studentViewModel.selectedTimeSlotIdsSet.value) Color(
                            0xFFEEA47F
                        ) else Color.White,
                    )
                    .border(
                        width = 0.dp,
                        color = Color(0xFFEEA47F),
                        shape = MaterialTheme.shapes.medium
                    )
                    .padding(10.dp)
                    .clickable {
                        studentViewModel.toggleTimeSlotId(timeSlot.id)
                    },
                contentAlignment = Alignment.Center,

                ) {
                Text(
                    text = timeSlot.time,
                    fontSize = 24.sp,
                    color = if (timeSlot.id in studentViewModel.selectedTimeSlotIdsSet.value) Color.White else Color(0xFFEEA47F),
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
        }
    }

}


@Preview
@Composable
fun MainStudentPreview() {
    MainStudent(navController = rememberNavController(), authViewModel = AuthViewModel())
}

