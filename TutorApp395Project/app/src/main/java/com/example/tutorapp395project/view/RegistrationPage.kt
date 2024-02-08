package com.example.tutorapp395project.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.tutorapp395project.R
import com.example.tutorapp395project.ui.theme.TutorApp395ProjectTheme

@Composable
fun RegistrationPage(navController: NavController) {
    Background()
    LoginBox()
    RegistrationText(navController = navController)
}

/*
    Function: Creates the Registration text and the student and tutor buttons on the page
    Parameters: modifier -> takes modifier parameters
    Return: None
 */
@Composable
fun RegistrationText(navController: NavController, modifier: Modifier = Modifier) {
    Column(
        verticalArrangement = Arrangement.spacedBy(20.dp, Alignment.Bottom),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .height(870.dp)
            .fillMaxWidth()
            .padding(bottom = 20.dp)
    ) {
        Text(
            text = "Choose Your Role",
            style = TextStyle(
                fontSize = 35.sp,
                //fontFamily = FontFamily(Font(R.font.roboto)),
                fontWeight = FontWeight(900),
                color = Color(0xFF000000),
                textAlign = TextAlign.Center
            ),
            modifier = Modifier
                .width(282.dp)
                .height(41.dp)
        )
        StudentButton(painterResource(R.drawable.student), "Student", navController = navController)
        TutorButton(painterResource(R.drawable.teacher), "Tutor", navController = navController)
    }
}

/*
    Function: Creates a button that if chosen takes the user to the student registration page
    Parameters: icon -> Image used on the button
                text -> text printed on button
                onClick -> Link to next page
                modifier -> takes modifier parameters
    Return: None
 */
@Composable
fun StudentButton(icon: Painter, text: String, navController: NavController, modifier: Modifier = Modifier) {
    Button(
        onClick = {navController.navigate(Screen.StudentRegistration.route)},
        modifier = Modifier
            .fillMaxWidth(0.8f),
        content = {
            Column {
                Image(painter = icon,
                    contentDescription = null,
                    modifier = Modifier
                        .height(100.dp)
                        .align(Alignment.CenterHorizontally))
                Spacer(modifier = Modifier.width(8.dp)) // Adjust spacing
                Text(text, fontSize = 40.sp,
                    style = TextStyle(
                        textAlign = TextAlign.Center,
                        color = Color(0xFFB24444)))
            }
        },
        colors = ButtonDefaults.buttonColors(Color(0xFFEEA47F))
    )
}

/*
    Function: Creates a button that if chosen takes the user to the tutor registration page
    Parameters: modifier -> takes modifier parameters
    Return: None
 */
@Composable
fun TutorButton(icon: Painter, text: String, navController: NavController, modifier: Modifier = Modifier) {
    Button(
        onClick = {navController.navigate(Screen.TutorRegistration.route)},
        modifier = Modifier
            .fillMaxWidth(0.8f),
        content = {
            Column {
                // Specify the icon using the icon parameter
                Image(painter = icon, contentDescription = null, modifier = Modifier
                    .height(100.dp)
                    .align(Alignment.CenterHorizontally))
                Spacer(modifier = Modifier.width(8.dp)) // Adjust spacing
                Text(text,
                    fontSize = 40.sp,
                    style = TextStyle(color = Color(0xFFB24444))
                )
            }
        },
        colors = ButtonDefaults.buttonColors(Color(0xFFEEA47F))
    )
}

@Preview(showBackground = true)
@Composable
fun RegistrationPagePreview() {
    TutorApp395ProjectTheme {
        Background()
        LoginBox()
        RegistrationText(navController = NavController(LocalContext.current))
    }
}