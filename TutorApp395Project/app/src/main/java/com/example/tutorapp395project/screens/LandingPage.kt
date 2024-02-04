package com.example.tutorapp395project.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tutorapp395project.R
import com.example.tutorapp395project.ui.theme.TutorApp395ProjectTheme

@Composable
fun LandingPage() {
        Background()
        LoginButton()
        BackHandler(enabled = true) {}
}

/*
    Function: Creates the background color and logo
    Parameters: modifier -> Takes modifier parameters
    Return: None
 */
@Composable
fun Background(modifier: Modifier = Modifier) {
    val image = painterResource(R.drawable.logo)
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
fun LoginButton(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    Column(
        verticalArrangement = Arrangement.spacedBy(438.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .width(600.dp)
            .height(800.dp)
            .padding(start = 62.dp, top = 700.dp, end = 62.dp, bottom = 10.dp)
    ){
        Button(
            onClick = { },
            colors = ButtonDefaults.buttonColors(Color(0xFFEEA47F)),
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .padding(bottom = 30.dp)
        ){
            Text(
                text = "LOGIN",
                style = TextStyle(
                    color = Color(0xFFB24444)
                )
            )
        }
    }
}

@Preview
    (showBackground = true)
@Composable
fun LandingPagePreview() {
    TutorApp395ProjectTheme {
        Background()
        LoginButton()
    }
}