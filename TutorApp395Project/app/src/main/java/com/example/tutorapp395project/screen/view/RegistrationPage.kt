package com.example.tutorapp395project.screen.view

import android.graphics.drawable.Icon
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.tutorapp395project.R
import com.example.tutorapp395project.classes.Screen
import com.example.tutorapp395project.ui.theme.TutorApp395ProjectTheme
import com.example.tutorapp395project.viewModel.AuthViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

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

    var snackBarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    LaunchedEffect(key1 = authViewModel.registerState) {
        snapshotFlow { authViewModel.registerState.value }
            .collect { state ->
                if (state.isNotEmpty()) {
                    scope.launch {
                        snackBarHostState.showSnackbar(
                            message = state,
                        )
                        if (state.contains("successfully")) {
                            navController.navigate(Screen.LoginPage.route)
                        }
                    }
                }
            }
    }



    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackBarHostState) },
        containerColor = Color(0xFF00539C),
        content = {
            Column (
                modifier = Modifier
                    .padding(it)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                Image(
                    painter = painterResource(R.drawable.tutorapple), // image file
                    contentDescription = "tutorapple-logo",
                    modifier = Modifier
                        .fillMaxHeight(0.3f)
                        .wrapContentSize()
                )

                when (authViewModel.registerDataState.value.role) {
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
        onClick = {
                  authViewModel.onRegisterChange {currentRegisterData ->
                        currentRegisterData.copy(role = role.lowercase())
                  }
        },
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

    var experienceExpanded by remember { mutableStateOf(false) }
    var expertiseExpanded by remember { mutableStateOf(false) }

    Column (
        modifier = modifier
            .fillMaxSize()
            .padding(bottom = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(0.dp)

    ) {
        Text(
            text = "Let's be a Tutor-apple ${authViewModel.registerDataState.value.role.capitalize()}!",
            style = TextStyle(
                fontSize = 30.sp,
                fontWeight = FontWeight(500),
                color = Color.White,
                textAlign = TextAlign.Center
            ),
            modifier = Modifier.padding(start = 20.dp, end = 20.dp)
        )
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(7.dp, Alignment.Bottom),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .weight(1f) // This will make the LazyColumn fill the remaining height
                .wrapContentSize()
                .padding(20.dp)
                .background(color = Color.White, shape = RoundedCornerShape(30.dp))
                .padding(start = 40.dp, end = 40.dp, top = 20.dp, bottom = 20.dp)

        ) {
            item {
                OutlinedTextField(
                    value = authViewModel.registerDataState.value.first_name,
                    modifier = Modifier.fillMaxWidth(),
                    onValueChange = {
                        authViewModel.onRegisterChange { currentRegisterData ->
                            currentRegisterData.copy(first_name = it)
                        }
                    },
                    label = { Text("First Name", fontWeight = FontWeight.Black) }
                )

                OutlinedTextField(
                    value = authViewModel.registerDataState.value.last_name,
                    modifier = Modifier.fillMaxWidth(),
                    onValueChange = {
                        authViewModel.onRegisterChange { currentRegisterData ->
                            currentRegisterData.copy(last_name = it)
                        }
                    },
                    label = { Text("Last Name", fontWeight = FontWeight.Black) }
                )

                OutlinedTextField(
                    value = authViewModel.registerDataState.value.date_of_birth,
                    modifier = Modifier.fillMaxWidth(),
                    onValueChange = {
                        authViewModel.onRegisterChange { currentRegisterData ->
                            currentRegisterData.copy(date_of_birth = it)
                        }
                    },
                    label = { Text("Date of Birth (MM/DD/YYYY)", fontWeight = FontWeight.Black) }
                )

                OutlinedTextField(
                    value = authViewModel.registerDataState.value.email,
                    modifier = Modifier.fillMaxWidth(),
                    onValueChange = {
                        authViewModel.onRegisterChange { currentRegisterData ->
                            currentRegisterData.copy(email = it)
                        }
                    },
                    label = { Text("Email", fontWeight = FontWeight.Black) }
                )

                OutlinedTextField(
                    value = authViewModel.registerDataState.value.password,
                    modifier = Modifier.fillMaxWidth(),
                    onValueChange = {
                        authViewModel.onRegisterChange { currentRegisterData ->
                            currentRegisterData.copy(password = it)
                        }
                    },
                    label = { Text("Password", fontWeight = FontWeight.Black) }
                )

                when (authViewModel.registerDataState.value.role) {
                    "student" -> {
                        OutlinedTextField(
                            value = if (authViewModel.registerDataState.value.grade != 0) authViewModel.registerDataState.value.grade.toString() else "",
                            modifier = Modifier.fillMaxWidth(),
                            onValueChange = {
                                authViewModel.onRegisterChange { currentRegisterData ->
                                    if (it.isNotEmpty()) {
                                        currentRegisterData.copy(grade = it.toInt())
                                    } else {
                                        currentRegisterData.copy(grade = 0)
                                    }
                                }
                            },
                            label = { Text("Grade", fontWeight = FontWeight.Black) }
                        )

                        OutlinedTextField(
                            value = authViewModel.registerDataState.value.school,
                            modifier = Modifier.fillMaxWidth(),
                            onValueChange = {
                                authViewModel.onRegisterChange { currentRegisterData ->
                                    currentRegisterData.copy(school = it)
                                }
                            },
                            label = { Text("School", fontWeight = FontWeight.Black) }
                        )
                    }

                    "tutor" -> {

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentSize(Alignment.TopStart)
                                .padding(top = 10.dp)
                                .border(1.dp, Color.Gray, RoundedCornerShape(4.dp))

                        ) {

                            val items = listOf("fresher", "junior", "senior")
                            var selectedIndex by remember { mutableStateOf(0) }

                            Text(
                                text = "Experience: " + items[selectedIndex].capitalize(),
                                modifier = Modifier
                                    .clickable(onClick = { experienceExpanded = true })
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                fontWeight = FontWeight.Bold
                            )

                            DropdownMenu(
                                expanded = experienceExpanded,
                                modifier = Modifier
                                    .padding(16.dp)
                                    .fillMaxWidth(),
                                onDismissRequest = { experienceExpanded = false },
                            ) {
                                items.forEachIndexed { index, s ->
                                    DropdownMenuItem(
                                        onClick = {
                                            selectedIndex = index
                                            experienceExpanded = false
                                            authViewModel.onRegisterChange { currentRegisterData ->
                                                currentRegisterData.copy(experience = s)
                                            }
                                        },
                                        text = { Text(s) },
                                    )
                                }
                            }
                        }

                        OutlinedTextField(
                            value = authViewModel.registerDataState.value.description ?: "",
                            modifier = Modifier.fillMaxWidth(),
                            onValueChange = {
                                authViewModel.onRegisterChange { currentRegisterData ->
                                    currentRegisterData.copy(description = it)
                                }
                            },
                            label = { Text("Description", fontWeight = FontWeight.Black) }
                        )

                        OutlinedTextField(
                            value = authViewModel.registerDataState.value.degrees?.firstOrNull()
                                ?: "",
                            modifier = Modifier.fillMaxWidth(),
                            onValueChange = {
                                authViewModel.onRegisterChange { currentRegisterData ->
                                    currentRegisterData.copy(degrees = listOf(it))
                                }
                            },
                            label = { Text("Degree", fontWeight = FontWeight.Black) }
                        )

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentSize(Alignment.TopStart)
                                .padding(top = 10.dp)
                                .border(1.dp, Color.Gray, RoundedCornerShape(4.dp))

                        ) {

                            val items = listOf(
                                "mathematics",
                                "english",
                                "physics",
                                "chemistry",
                                "biology",
                                "history",
                                "geography",
                                "business",
                                "studies",
                                "computer science",
                                "literature"
                            )
                            var selectedIndex by remember { mutableStateOf(0) }

                            Text(
                                text = "Expertise: " + items[selectedIndex].capitalize(),
                                modifier = Modifier
                                    .clickable(onClick = { expertiseExpanded = true })
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                fontWeight = FontWeight.Bold
                            )

                            DropdownMenu(
                                expanded = expertiseExpanded,
                                modifier = Modifier
                                    .padding(16.dp)
                                    .fillMaxWidth(),
                                onDismissRequest = { expertiseExpanded = false },
                            ) {
                                items.forEachIndexed { index, s ->
                                    DropdownMenuItem(
                                        onClick = {
                                            selectedIndex = index
                                            expertiseExpanded = false
                                            authViewModel.onRegisterChange { currentRegisterData ->
                                                currentRegisterData.copy(expertise = listOf(s))
                                            }
                                        },
                                        text = { Text(s) },
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        Button(
            onClick = {
                authViewModel.onRegister()
            },
            colors = ButtonDefaults.buttonColors(Color(0xFFEEA47F)),
            modifier = Modifier
                .padding(top = 16.dp)
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