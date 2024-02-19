package com.example.tutorapp395project.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.tutorapp395project.ui.theme.TutorApp395ProjectTheme
import com.example.tutorapp395project.viewModel.AuthViewModel

@Composable
fun LoginPage(
        navController: NavController,
        authViewModel: AuthViewModel = viewModel()
    ) {
    Background()
    LoginBox()
    Fields(
         navController = navController,
        authViewModel = authViewModel
    )
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
fun Fields(
        navController: NavController,
        modifier: Modifier = Modifier,
        authViewModel: AuthViewModel = viewModel()
    ){

    val loginData = authViewModel.loginDataState.value

    Column(
        verticalArrangement = Arrangement.spacedBy(0.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 400.dp)
    ){
        OutlinedTextField(
            value = loginData.email,
            onValueChange = { authViewModel.onEmailChange(it) },
            label = { Text("Email", fontWeight = FontWeight.Black) }
        )
        Spacer(modifier = Modifier.height(10.dp))
        DropdownTextBox(
            items = listOf("student", "tutor"),
            selectedItem = loginData.role,
            onItemSelected = { authViewModel.onRoleChange(it) }
        )
        OutlinedTextField(
            value = loginData.password,
            onValueChange = { authViewModel.onPasswordChange(it) },
            label = { Text("Password", fontWeight = FontWeight.Black)},
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
        )

        Spacer(modifier = Modifier.height(24.dp))

        // LOGIN BUTTON
        Button(
            onClick = {
                authViewModel.onLogin()
                authViewModel.loginDataState.value = authViewModel.loginDataState.value.copy(email = "", password = "")
                navController.navigate(ScreenGraph.authGraph.route)
            },
            colors = ButtonDefaults.buttonColors(Color(0xFFEEA47F)),

        ) {
            Text(
                "Login",
                style = TextStyle(
                    color = Color(0xFFB24444),
                )
            )
        }

        // REGISTER BUTTON
        TextButton(
            onClick = {navController.navigate(Screen.RegistrationPage.route)}
        ) {
            Text(
                text = "Don't have an Account? Register Here!",
                style = TextStyle(
                    color = Color(0xFFEEA47F),
                    fontWeight = FontWeight.Black,
                    textAlign = TextAlign.End
                )
            )
        }
    }
}

@Composable
fun DropdownTextBox(
    items: List<String>,
    selectedItem: String,
    onItemSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedValue by remember { mutableStateOf(selectedItem) }

    Box(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = selectedValue,
            modifier = Modifier
                .fillMaxWidth()
                .clickable { expanded = true }
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.fillMaxWidth()
        ) {
            items.forEach { item ->
                DropdownMenuItem(onClick = {
                    selectedValue = item
                    onItemSelected(item)
                    expanded = false
                },text = { Text(text = item) })
            }
        }
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
        Fields(navController = NavController(LocalContext.current))
    }
}