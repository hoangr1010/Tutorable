package com.example.tutorapp395project.view

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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.tutorapp395project.ui.theme.TutorApp395ProjectTheme
import com.example.tutorapp395project.viewModel.AuthViewModel

/*
    Function: Creates the settings page
    Parameters: navController -> Navigation controller used to navigate between different composables
    Return: None

 */
@Composable
fun SettingsPage(
        navController: NavController,
        route: String,
        authViewModel: AuthViewModel
    ) {
    BackgroundNoLogo()
    HomeBar(navController = navController, route = route )
    SettingsColumn(navController = navController, authViewModel = authViewModel)
}

/*
    Function: Creates a column for all the settings buttons to be placed into
    Parameters: modifier -> takes modifier parameters
    Return: None
 */
@Composable
fun SettingsColumn(
        navController: NavController,
        authViewModel: AuthViewModel
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(15.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
    ) {

        Button(
            onClick = {
                authViewModel.onLogout()
                navController.navigate(ScreenGraph.authGraph.route)
            },
            colors = ButtonDefaults.buttonColors(Color(0xFFEEA47F)),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = "Logout",
                style = TextStyle(
                    color = Color(0xFFB24444)
                )
            )
        }

    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SettingsPreview() {
    TutorApp395ProjectTheme {
        BackgroundNoLogo()
        HomeBar(navController = NavController(LocalContext.current), route = "student")
        SettingsColumn(
            navController = NavController(LocalContext.current),
            authViewModel = AuthViewModel()
        )
    }
}