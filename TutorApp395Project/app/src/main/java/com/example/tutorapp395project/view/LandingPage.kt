package com.example.tutorapp395project.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.tutorapp395project.R
import com.example.tutorapp395project.ui.theme.TutorApp395ProjectTheme

/*
    Function: Creates the landing page with the background and buttons
    Parameters:
        - navController: NavController
    Return: None
 */
@Composable
fun LandingPage(navController: NavController) {
    Background()
    ButtonLayout(navController = navController)
}

/*
    Function: Creates the background color and logo
    Parameters: modifier -> Takes modifier parameters
    Return: None
 */
@Composable
fun Background(modifier: Modifier = Modifier) {
    val image = painterResource(R.drawable.tutorapple)
    Column (
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xFF00539C))
    ) {
        Image(
            painter = image, // image file
            contentDescription = null,
            modifier = Modifier
                .padding(top = 68.dp, bottom = 508.dp)
        )
    }
}

/*
    Function: Creates the Login button which when clicked takes the user to the next page.
    Parameters: modifier -> takes modifier parameters
    Return: None
 */
@Composable
fun LoginButton(navController: NavController, target: String, modifier: Modifier = Modifier) {
    Button(
        onClick = {navController.navigate(target)},
        colors = ButtonDefaults.buttonColors(Color(0xFFEEA47F)),
        modifier = Modifier
            .fillMaxWidth(0.7f)
            //.padding(bottom = 30.dp)
    ){
        Text(
            text = "LOGIN",
            style = TextStyle(
                color = Color(0xFFB24444)
            )
        )
    }
}

/*
    Function: Creates the layout for the buttons
    Parameters:
        - navController: NavController -> takes the navigation controller
        - modifier -> takes modifier parameters
    Return: None
 */
@Composable
fun ButtonLayout(navController: NavController, modifier: Modifier = Modifier) {
    Column(
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(bottom = 50.dp)
            .fillMaxSize()
    ) {
        LoginButton(navController = navController, target = Screen.LoginPage.route)
    }
}
@Preview
    (showBackground = true,
            showSystemUi = true)
@Composable
fun LandingPagePreview() {
    TutorApp395ProjectTheme {
        Background()
        ButtonLayout(navController = NavController(LocalContext.current))
    }
}