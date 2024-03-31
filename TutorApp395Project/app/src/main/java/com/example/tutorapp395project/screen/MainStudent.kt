package com.example.tutorapp395project.screen

import android.os.Build
import android.util.Log
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
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.tutorapp395project.R
import com.example.tutorapp395project.data.TimeSlots
import com.example.tutorapp395project.screen.view.HomeBar
import com.example.tutorapp395project.screen.view.SettingsColumn
import com.example.tutorapp395project.screen.studentView.StudentAppointmentLayout
import com.example.tutorapp395project.screen.studentView.StudentProfileColumn
import com.example.tutorapp395project.screen.studentView.TutorList
import com.example.tutorapp395project.viewModel.AuthViewModel
import com.example.tutorapp395project.viewModel.HomeViewModel
import com.example.tutorapp395project.viewModel.StudentViewModel
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
    homeViewModel: HomeViewModel = HomeViewModel(),
    studentViewModel: StudentViewModel = StudentViewModel(authViewModel = authViewModel),
) {

    val timeSlotDialogOpen = remember { mutableStateOf(false) }

    val createSessionButtonState =
        remember { mutableStateOf(studentViewModel.createSessionButtonState.value) }
    LaunchedEffect(studentViewModel.createSessionButtonState) {
        createSessionButtonState.value = studentViewModel.createSessionButtonState.value
    }

    val calendarState = rememberUseCaseState(
        onFinishedRequest = {
            timeSlotDialogOpen.value = true
        }
    )

    Scaffold(
        containerColor = Color(0xFF00539C),
        bottomBar = { HomeBar(navController = navController, homeViewModel = homeViewModel) },
        floatingActionButton = {
            if (homeViewModel.viewState.value == "schedule") {
                FloatingActionButton(
                    onClick = {
                        calendarState.show(); studentViewModel.isEditing.value =
                        false; studentViewModel.disableTimeSlot.value = setOf()
                    },
                    containerColor = Color(0xFF66B8FF),
                    contentColor = Color(0xFFCCE7FF),
                    shape = MaterialTheme.shapes.large,
                ) {
                    Row(
                        modifier = Modifier.padding(8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(2.dp)
                    ) {
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

            if (timeSlotDialogOpen.value) {
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
                                        studentViewModel.disableTimeSlot.value = setOf()
                                        studentViewModel.isEditing.value = false
                                    },
                                    colors = ButtonDefaults.buttonColors(Color.Gray)
                                ) {
                                    Text("Cancel")
                                }

                                Button(
                                    onClick = {
                                        studentViewModel.tutorFilter()
                                    },
                                    colors = ButtonDefaults.buttonColors(Color(0xFFEEA47F))

                                ) {
                                    Text("Continue")
                                }
                            }
                        }
                    }
                }

            }

            if (studentViewModel.tutorFilterDialogState.value.isOpen) {
                Dialog(onDismissRequest = {
                    studentViewModel.tutorFilterDialogState.value =
                        studentViewModel.tutorFilterDialogState.value.copy(isOpen = false)
                }) {
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
                                    text = "Select a Tutor",
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(10.dp)
                                )
                            }

                            TutorList(
                                modifier = Modifier.weight(1f),
                                studentViewModel = studentViewModel,
                            )

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 10.dp, start = 10.dp, end = 10.dp),
                                horizontalArrangement = Arrangement.SpaceAround
                            ) {
                                Button(
                                    onClick = {
                                        studentViewModel.tutorFilterDialogState.value =
                                            studentViewModel.tutorFilterDialogState.value.copy(
                                                isOpen = false
                                            )
                                    },
                                    colors = ButtonDefaults.buttonColors(Color.Gray)
                                ) {
                                    Text("Cancel")
                                }
                            }
                        }
                    }
                }
            }

            if (studentViewModel.sessionFormOpen.value) {
                Dialog(onDismissRequest = {
                    studentViewModel.sessionFormOpen.value = false
                    studentViewModel.isEditing.value = false
                }) {
                    Card(
                        modifier = Modifier.wrapContentSize(),
                        colors = CardColors(
                            containerColor = Color(0xFFCCE7FF),
                            contentColor = Color(0xFFEEA47F),
                            disabledContainerColor = Color(0xFFD3D3D3),
                            disabledContentColor = Color(0xFFA9A9A9),
                        )
                    ) {
                        Column(
                            modifier = Modifier.wrapContentSize(),
                            verticalArrangement = Arrangement.spacedBy(
                                0.dp,
                                Alignment.CenterVertically
                            ),
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "Session Form",
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(10.dp)
                                )
                            }

                            SessionForm(studentViewModel = studentViewModel)

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 10.dp, bottom = 10.dp),
                                horizontalArrangement = Arrangement.SpaceAround
                            ) {
                                Button(
                                    onClick = {
                                        studentViewModel.createNewSession()
                                    },
                                    colors = ButtonDefaults.buttonColors(Color(0xFFEEA47F))
                                ) {
                                    Log.d(
                                        "MainStudent",
                                        "createSessionButtonState: ${createSessionButtonState.value}"
                                    )
                                    when (studentViewModel.createSessionButtonState.value) {
                                        "loading" -> {
                                            Text("Loading")
                                        }

                                        "success" -> {
                                            Text("✅Success")
                                        }

                                        "error" -> {
                                            Text("❌Error, try again")
                                        }

                                        else -> {
                                            Text("Create New Session")
                                        }
                                    }
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
                            .padding(it),
                        studentViewModel = studentViewModel,
                        authViewModel = authViewModel,
                        homeViewModel = homeViewModel
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

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TimeSlotSessionView(
    studentViewModel: StudentViewModel,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .padding(start = 10.dp, end = 10.dp)
    ) {
        items(TimeSlots.slots) { timeSlot ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(5.dp, MaterialTheme.shapes.medium, true)
                    .clip(MaterialTheme.shapes.medium)
                    .background(
                        color = if (timeSlot.id in studentViewModel.selectedTimeSlotIdsSet.value) Color(
                            0xFFEEA47F
                        )
                        else if (timeSlot.id in studentViewModel.disableTimeSlot.value) Color.LightGray
                        else Color.White,
                    )
                    .border(
                        width = 0.dp,
                        color = Color(0xFFEEA47F),
                        shape = MaterialTheme.shapes.medium
                    )
                    .padding(10.dp)
                    .clickable {
                        if (timeSlot.id !in studentViewModel.disableTimeSlot.value) {
                            studentViewModel.toggleTimeSlotId(timeSlot.id)
                        }
                    },
                contentAlignment = Alignment.Center,

                ) {
                Text(
                    text = timeSlot.time,
                    fontSize = 24.sp,
                    color = if (timeSlot.id in studentViewModel.selectedTimeSlotIdsSet.value) Color.White else Color(
                        0xFFEEA47F
                    ),
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
        }
    }

}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SessionForm(
    studentViewModel: StudentViewModel
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(0.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .wrapContentSize()
            .padding(start = 20.dp, end = 20.dp)
            .background(color = Color.White, shape = RoundedCornerShape(30.dp))
            .padding(20.dp)
    ) {
        OutlinedTextField(
            value = studentViewModel.sessionName.value,
            onValueChange = {
                studentViewModel.sessionName.value = it
            },
            label = { Text("Session Name", fontWeight = FontWeight.Black) },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(10.dp))
        OutlinedTextField(
            value = studentViewModel.sessionDescription.value,
            onValueChange = {
                studentViewModel.sessionDescription.value = it
            },
            label = { Text("Session Description", fontWeight = FontWeight.Black) },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(10.dp))
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun MainStudentPreview() {
    MainStudent(navController = rememberNavController(), authViewModel = AuthViewModel())
}

