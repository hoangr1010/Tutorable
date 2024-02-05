package com.example.tutorapp395project.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.tutorapp395project.ui.theme.TutorApp395ProjectTheme

@Composable
fun StudentRegistration(navController: NavController) {
    Background()
    RegistrationBox()
    StudentRegistrationFields(navController = navController)
}

/*
    Function: Creates a box that is the background for the user input fields
    Parameters: modifier -> takes modifier parameters
    Return: None
 */
@Composable
fun RegistrationBox(modifier: Modifier = Modifier) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(1000.dp)
            .padding(top = 275.dp)
            .background(
                color = Color(0xFFD9D9D9),
                shape = RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp)
            )
    )
}

/*
    Function: Creates a field that takes the user input and sends it to the backend for processing
    Parameters: field -> name of the string field name
                modifier -> takes modifier parameters
    Return: None
 */
@Composable
fun TextField(field: String, modifier: Modifier = Modifier) {
    var userText by remember { mutableStateOf("Enter $field") }

    OutlinedTextField(
        value = userText,
        onValueChange = { userText = it },
        label = { Text("$field", fontWeight = FontWeight.Black) }
    )
    //return TODO("Provide the return value")
}

/*
    Function: Organizes all the fields into a column for the user to input all of their data into.
              then once the register button is clicked, the data is sent to the backend for
              processing
    Parameters: modifier -> takes modifier parameters
    Return: None
 */
@Composable
fun StudentRegistrationFields(navController: NavController, modifier: Modifier = Modifier) {
    Column(
        verticalArrangement = Arrangement.spacedBy(7.dp, Alignment.Bottom),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .height(990.dp)
            .fillMaxWidth()
    ) {
        TextField("Name")
        TextField("Date of Birth")
        TextField("Grade")
        TextField("School")
        TextField("Email")
        TextField("Password")
        Button(
            onClick = {navController.navigate(Screen.StudentSchedule.route)},
            colors = ButtonDefaults.buttonColors(Color(0xFFEEA47F)),
            modifier = Modifier
                .padding(16.dp)
            ) {
                Text(
                    text = "Register")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun StudentRegistrationPreview() {
    TutorApp395ProjectTheme {
        Background()
        RegistrationBox()
        StudentRegistrationFields(navController = NavController(LocalContext.current))
    }
}