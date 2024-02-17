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
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
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
import com.example.tutorapp395project.viewModel.LoginViewModel

/*
    Function: Creates the login page with the background and user input fields
    Parameters:
        - navController: NavController
    Return: None

 */
@Composable
fun LoginPage(navController: NavController) {
    val viewModel: LoginViewModel = viewModel()
    Background()
    LoginBox()
    Fields(navController = navController)
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
fun Fields(navController: NavController, modifier: Modifier = Modifier,
           viewModel: LoginViewModel = viewModel()) {

    Column(
        verticalArrangement = Arrangement.spacedBy(0.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 400.dp)
    ){
        OutlinedTextField(
            value = viewModel.email.value,
            onValueChange = viewModel::onEmailChanged,
            label = { Text("Email", fontWeight = FontWeight.Black) }
        )
        Spacer(modifier = Modifier.height(10.dp))
        DropdownTextBox(
            items = listOf("Student", "Tutor"),
            selectedItem = viewModel.role.value,
            onItemSelected = viewModel::onRoleChanged
        )
        OutlinedTextField(
            value = viewModel.password.value,
            onValueChange = viewModel::onPasswordChanged,
            label = { Text("Password", fontWeight = FontWeight.Black)},
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
        )
        Text(
            text = "Forgot Password?",
            fontWeight = FontWeight.Black,
            fontSize = 15.sp,
            modifier = Modifier
                .align(Alignment.End)
                .padding(end = 50.dp, bottom = 20.dp),
            style = TextStyle(
                color = Color(0xFFEEA47F)
            )
        )
        Spacer(modifier = Modifier.height(50.dp))
        AuthButton(email = viewModel.email.value, password = viewModel.password.value,
            role = viewModel.role.value, navController = navController,
            target = Screen.StudentSchedule.route)
        RegisterButton(navController = navController)
    }
}

/*
    Function: Creates the Login button which when clicked logs the user into their own account.
    Parameters: email -> user's email
                password -> user's password
                role -> user's role
                navController -> navigation controller
                target -> target page
                modifier -> takes modifier parameters
    Return: None
 */
@Composable
fun AuthButton(email: String, password: String, role: String, navController: NavController,
               target: String, viewModel: LoginViewModel = viewModel(),
               modifier: Modifier = Modifier) {
    Button(
        onClick = {viewModel.onLoginClick(navController, target)},
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
    Function: Creates the Register button which when clicked takes the user to the register page.
    Parameters: navController -> navigation controller
                modifier -> takes modifier parameters
    Return: None
 */
@Composable
fun RegisterButton(navController: NavController, modifier: Modifier = Modifier) {
    TextButton(
        onClick = {navController.navigate(Screen.RegistrationPage.route)},
        modifier = Modifier
            .fillMaxWidth(0.75f)
            .padding(bottom = 30.dp)
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

/*
    Function: Creates the dropdown menu for the role
    Parameters:
        - items: List<String>
        - selectedItem: String
        - onItemSelected: (String) -> Unit
        - modifier: Modifier
        - arrowIcon: ImageVector
    Return: None
 */
@Composable
fun DropdownTextBox(
    items: List<String>,
    selectedItem: String,
    onItemSelected: (String) -> Unit,
    modifier: Modifier = Modifier,
    arrowIcon: ImageVector = Icons.Default.ArrowDropDown
) {
    var expanded by remember { mutableStateOf(false) }
    var textValue by remember { mutableStateOf(selectedItem) }

    Box(modifier = modifier) {
        OutlinedTextField(
            value = textValue,
            onValueChange = { textValue = it },
            modifier = Modifier
                .fillMaxWidth(0.72f)
                .clickable { expanded = true }
                //.padding(end = 0.dp) // Adjust padding to make space for the arrow
        )
        IconButton(
            onClick = { expanded = true },
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(end = 2.dp)
        ) {
            Icon(
                imageVector = arrowIcon,
                contentDescription = "Dropdown Arrow"
            )
        }

        if (expanded) {
            DropdownMenu(
                expanded = true,
                onDismissRequest = { expanded = false },
                modifier = Modifier
                    .fillMaxWidth(0.72f)
                    .padding(top = 48.dp) // Adjust the top padding to position the dropdown menu
            ) {
                items.forEach { item ->
                    DropdownMenuItem(
                        onClick = {
                            onItemSelected(item)
                            textValue = item
                            expanded = false
                        }
                    ) {
                        Text(text = item)
                    }
                }
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