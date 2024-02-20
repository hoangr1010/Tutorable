package com.example.tutorapp395project.screen.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
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
import com.example.tutorapp395project.screen.component.LoginBox
import com.example.tutorapp395project.ui.theme.TutorApp395ProjectTheme
import com.example.tutorapp395project.viewModel.AuthViewModel

/*
    Function: Creates the Registration page
    Parameters: navController -> Navigation controller used to navigate between different composables
    Return: None

 */
@Composable
fun RegistrationPage(
    navController: NavController,
    authViewModel: AuthViewModel,
) {

    Scaffold(
        containerColor = Color(0xFF00539C),
        content = {
            Column (
                modifier = Modifier
                    .padding(it)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceAround
            ) {
//                Image(
//                    painter = painterResource(R.drawable.tutorapple), // image file
//                    contentDescription = "tutorapple-logo",
//                    modifier = Modifier.size(300.dp).wrapContentSize()
//                )
                when (authViewModel.registerRoleState.value) {
                    "" -> {
                        RegistrationText(
                            navController = navController,
                            authViewModel = authViewModel
                        )
                    }
                    else -> {
                        RegistrationFields(
                            navController = navController,
                            authViewModel = authViewModel
                        )
                    }
                }
            }
        }
    )}

/*
    Function: Creates the Registration text and the student and tutor buttons on the page
    Parameters: modifier -> takes modifier parameters
    Return: None
 */
@Composable
fun RegistrationText(
        navController: NavController, modifier: Modifier = Modifier,
        authViewModel: AuthViewModel
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(20.dp, Alignment.Bottom),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .wrapContentSize()
            .padding(20.dp)
            .background(color = Color.White, shape = RoundedCornerShape(30.dp))
            .padding(20.dp)
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
        )

        RoleButton(painterResource(R.drawable.student), "Student", authViewModel = authViewModel)
        RoleButton(painterResource(R.drawable.teacher), "Tutor", authViewModel = authViewModel)
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
fun RoleButton(icon: Painter,
               role: String,
               modifier: Modifier = Modifier,
               authViewModel: AuthViewModel
) {
    Button(
        onClick = { authViewModel.onRegisterRoleChange(role.lowercase())},
        modifier = Modifier
            .widthIn(max = 200.dp),
        content = {
            Row (
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Image(painter = icon,
                    contentDescription = null,
                    modifier = Modifier
                        .height(50.dp)
                )
                Text(role, fontSize = 25.sp,
                    style = TextStyle(
                        textAlign = TextAlign.Center,
                        color = Color(0xFFB24444)))
            }
        },
        colors = ButtonDefaults.buttonColors(Color(0xFFEEA47F))
    )
}

@Composable
fun RegistrationFields(
    navController: NavController,
    modifier: Modifier = Modifier,
    authViewModel: AuthViewModel
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(7.dp, Alignment.Bottom),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .wrapContentSize()
            .padding(20.dp)
            .background(color = Color.White, shape = RoundedCornerShape(30.dp))
            .padding(20.dp)
    ) {

        OutlinedTextField(
            value = "",
            onValueChange = {  },
            label = { Text("First Name", fontWeight = FontWeight.Black) }
        )

        OutlinedTextField(
            value = "",
            onValueChange = {  },
            label = { Text("Email", fontWeight = FontWeight.Black) }
        )

        Button(
            onClick = {navController.navigate(Screen.StudentSchedule.route)},
            colors = ButtonDefaults.buttonColors(Color(0xFFEEA47F)),
            modifier = Modifier
                .padding(16.dp)
        ) {
            Text(
                text = "Register"
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun RegistrationPagePreview() {
    TutorApp395ProjectTheme {
        RegistrationPage(navController = NavController(LocalContext.current), authViewModel = AuthViewModel())
    }
}