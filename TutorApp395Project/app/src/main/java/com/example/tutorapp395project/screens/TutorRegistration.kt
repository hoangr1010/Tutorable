package com.example.tutorapp395project.screens

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tutorapp395project.ui.theme.TutorApp395ProjectTheme

class TutorRegistration : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Background()
            RegistrationBox()
            TutorRegistrationFields(onClick = {
                Log.d("Registration button",
                    "Registration Button clicked.")})
        }
    }
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
fun TutorRegistrationFields(onClick: () -> Unit, modifier: Modifier = Modifier) {
    Column(
        verticalArrangement = Arrangement.spacedBy(15.dp, Alignment.Bottom),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .height(990.dp)
            .fillMaxWidth()
    ) {
        TextField("Name")
        TextField("Date of Birth")
        TextField("School")
        TextField("Degree")
        TextField("Email")
        TextField("Password")
        Button(
            onClick = { onClick() },
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
fun TutorRegistrationPreview() {
    TutorApp395ProjectTheme {
        Background()
        RegistrationBox()
        TutorRegistrationFields(onClick = {
            Log.d("Registration button",
                "Registration Button clicked.")})
    }
}