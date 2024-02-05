package com.example.tutorapp395project

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.tutorapp395project.ui.theme.TutorApp395ProjectTheme

@Composable
fun LoginPage(navController: NavController) {
    Background()
    LoginBox()
    Fields()
    LoginButton(navController = navController)
    RegisterButton(navController = navController)
}

/*
    Function: Creates the off white box that is used as the background for user input
    Parameters: modifier -> takes modifier parameters
    Return: None
 */
@Composable
fun LoginBox(modifier: Modifier = Modifier) {
    Box (
        modifier = Modifier
            .fillMaxWidth()
            .height(900.dp)
            .padding(top = 349.dp)
            .background(
                color = Color(0xFFD9D9D9),
                shape = RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp)
            )
    )
}

/*
    Function: Creates the Username and Password fields for the Login Box
    Parameters: modifier -> takes modifier parameters
    Return: None
 */
@Composable
fun Fields(modifier: Modifier = Modifier){
    var userText by remember { mutableStateOf("Username") }
    var password by rememberSaveable { mutableStateOf("") }

    Column(
        verticalArrangement = Arrangement.spacedBy(0.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 100.dp)
    ){
        OutlinedTextField(
            value = userText,
            onValueChange = { userText = it },
            label = { Text("Username", fontWeight = FontWeight.Black) }
        )
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password", fontWeight = FontWeight.Black)},
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
        )
        Text(
            text = "Forgot Password?",
            fontWeight = FontWeight.Black,
            fontSize = 12.sp,
        )
    }
}

@Composable
fun LoginButton(navController: NavController, modifier: Modifier = Modifier) {
    Column(
        //verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .width(600.dp)
            .height(800.dp)
            .padding(start = 62.dp, top = 700.dp, end = 62.dp, bottom = 10.dp)
    ){
        Button(
            onClick = {navController.navigate(Screen.StudentSchedule.route)},
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
        RegisterButton(navController = navController)
    }
}

@Composable
fun RegisterButton(navController: NavController, modifier: Modifier = Modifier) {
    TextButton(
        onClick = {navController.navigate(Screen.RegistrationPage.route)},
        modifier = Modifier
            .fillMaxWidth(0.8f)
            .padding(bottom = 30.dp)
    ) {
        Text(
            text = "Don't have an Account? Register Here!",
            style = TextStyle(
                color = Color(0xFFB24444)
            )
        )
    }
}


@Preview(
    showBackground = true
)
@Composable
fun LoginPagePreview() {
    TutorApp395ProjectTheme {
        Background()
        LoginBox()
        LoginButton(navController = NavController(LocalContext.current))
        //RegisterButton(navController = NavController(LocalContext.current))
        Fields()
    }
}