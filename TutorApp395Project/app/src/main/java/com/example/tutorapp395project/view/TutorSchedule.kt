package com.example.tutorapp395project.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.tutorapp395project.ui.theme.TutorApp395ProjectTheme

@Composable
fun TutorSchedule(navController: NavController) {
    BackgroundNoLogo()
    HomeBar(navController = navController)
    TutorAppointmentLayout()
}

/*
    Function: This creates a column that lays out all the users scheduled appointments
    Parameters: modifier -> takes modifier parameters
    Return: None
 */
@Composable
fun TutorAppointmentLayout(modifier: Modifier = Modifier) {
    Column (
        verticalArrangement = Arrangement.spacedBy(15.dp, Alignment.Top),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 20.dp)
    ){
        Appointment("3:00PM - 4:00PM", "January 24th, 2024", "Math",
            "Student","Tommy McStudent")
        Appointment("4:00PM - 5:00PM", "January 24th, 2024", "History",
            "Student","Tommy McStudent")
        Appointment("3:00PM - 4:00PM", "January 31th, 2024", "Math",
            "Student","Tommy McStudent")
        Appointment("4:00PM - 5:00PM", "January 31th, 2024", "History",
            "Student","Tommy McStudent")
        Appointment("6:00PM - 7:00PM", "January 31th, 2024", "Math",
            "Student","Anita Wynn")
    }


}

@Preview(showBackground = true)
@Composable
fun TutorSchedulePreview() {
    TutorApp395ProjectTheme {
        BackgroundNoLogo()
        HomeBar(navController = NavController(LocalContext.current))
        TutorAppointmentLayout()
    }
}