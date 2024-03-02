package com.example.tutorapp395project.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.tutorapp395project.ui.theme.TutorApp395ProjectTheme

/*
    Function: Creates the Tutor Registration page
    Parameters: navController -> Navigation controller used to navigate between different composables
    Return: None

 */
@Composable
fun TutorRegistration(navController: NavController) {
    Background()
    RegistrationBox()
    TutorRegistrationFields(navController = navController)
}

/*
    Function: Organizes all the fields into a column for the user to input all of their data into.
              then once the register button is clicked, the data is sent to the backend for
              processing
    Parameters: onClick -> Sends the user to the next page and submits all of their data to the
                           backend for processing
                modifier -> takes modifier parameters
    Return: None
 */
@Composable
fun TutorRegistrationFields(navController: NavController, modifier: Modifier = Modifier) {
    Column(
        verticalArrangement = Arrangement.spacedBy(7.dp, Alignment.Bottom),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .height(990.dp)
            .fillMaxWidth()
    ) {
        TextField("Name")
        TextField("Date of Birth")
        TextField("Expertise")
        TextField("Degree")
        TextField("Experience")
        TextField("Email")
        TextField("Password")
        Button(
            onClick = {navController.navigate(Screen.TutorSchedule.route)},
            colors = ButtonDefaults.buttonColors(Color(0xFFEEA47F)),
            modifier = Modifier
                .padding(1.dp)
        ) {
            Text(
                text = "Register")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TutorRegistrationPreview() {
    TutorApp395ProjectTheme {
        Background()
        RegistrationBox()
        TutorRegistrationFields(navController = NavController(LocalContext.current))
    }
}