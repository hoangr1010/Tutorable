package com.example.tutorapp395project.screen.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeFloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallTopAppBar
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

/*
    Function: This creates a column that lays out all the users scheduled appointments
    Parameters: modifier -> takes modifier parameters
    Return: None
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TutorAppointmentLayout(modifier: Modifier = Modifier) {
    var presses by remember { mutableIntStateOf(0) }
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                colors = topAppBarColors(
                    containerColor = (Color(0xFFEEA47F)),
                    titleContentColor = Color(0xFF191C1D),
                ),
                title = {
                    Text(modifier = Modifier
                        .fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        text = "Schedule",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis)
                },
                navigationIcon = {
                    IconButton(onClick = { /* do something */ }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Localized description"
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { /* do something */ }) {
                        Icon(
                            imageVector = Icons.Filled.Menu,
                            contentDescription = "Localized description"
                        )
                    }
                },
                scrollBehavior = scrollBehavior,
            )
        },
        bottomBar = {
            BottomAppBar(
                containerColor = (Color(0xFFEEA47F)),
                contentColor = Color(0xFF191C1D),
            ) {
//                Button(onClick = {/* TODO */ },
//                    colors = ButtonDefaults.buttonColors(Color(0xFFEEA47F)),
//                    modifier = Modifier
//                        .fillMaxWidth(0.7f)
//                ) {
//                    Text(
//                        modifier = Modifier
//                            .fillMaxWidth(),
//                        textAlign = TextAlign.Center,
//                        text = "Bottom app bar",
//                    )
                    IconButton(onClick = { /* TODO */ }) {
                        Icon(
                            Icons.Filled.Edit,
                            contentDescription = "Localized description",
                        )
                    }
                }
//            }
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { presses++ },
                containerColor = Color(0xFF66B8FF),
                contentColor = Color(0xFFCCE7FF)) {
                Icon(Icons.Default.Add, contentDescription = "Add")
            }
        }
    ) { innerPadding ->
        LazyColumn(
//            verticalArrangement = Arrangement.spacedBy(15.dp, Alignment.Top),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(color = Color(0xFF00539C))
        ) {
            items(19) {
                Appointment(
                    "3:00PM - 4:00PM", "January 24th, 2024", "Math",
                    "Student", "Nami"
                )
                Appointment(
                    "4:00PM - 5:00PM", "January 24th, 2024", "Chemistry",
                    "Student", "Vinsmoke Sanji"
                )
                Appointment(
                    "3:00PM - 4:00PM", "January 31th, 2024", "Biology",
                    "Student", "Tony Tony Chopper"
                )
                Appointment(
                    "4:00PM - 5:00PM", "January 31th, 2024", "History",
                    "Student", "Nico Robin"
                )
                Appointment(
                    "6:00PM - 7:00PM", "January 31th, 2024", "Physics",
                    "Student", "Franky"
                )
            }
        }
    }
}



@Preview
@Composable
fun previewTutorSchedule(){
    BackgroundNoLogo()
    TutorAppointmentLayout()
}