package com.example.tutorapp395project.screen.view

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
    Function: Creates a column for all the settings buttons to be placed into
    Parameters: modifier -> takes modifier parameters
    Return: None
 */
@Composable
fun SettingsColumn(
        navController: NavController,
        authViewModel: AuthViewModel,
        modifier: Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(15.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
    ) {

        Button(
            onClick = {
                navController.navigate(ScreenGraph.authGraph.route)
                authViewModel.onLogout()
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

@Preview
@Composable
fun SettingsPreview() {
    TutorApp395ProjectTheme {
        SettingsColumn(
            navController = NavController(LocalContext.current),
            authViewModel = AuthViewModel(),
            modifier = Modifier
                .fillMaxSize()
        )
    }
}