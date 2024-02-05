package com.example.tutorapp395project.screens

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

@Composable
fun SettingsPage(navController: NavController) {
    BackgroundNoLogo()
    HomeBar(navController = navController)
    SettingsColumn(navController = navController)
}

/*
    Function: Creates a column for all the settings buttons to be placed into
    Parameters: modifier -> takes modifier parameters
    Return: None
 */
@Composable
fun SettingsColumn(navController: NavController, modifier: Modifier = Modifier) {
    Column(
        verticalArrangement = Arrangement.spacedBy(15.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
    ) {
        SettingButton(option = "Edit Profile", navController = navController,
            target = Screen.Settings.route, color = 0xFFEEA47F)
        SettingButton(option = "About", navController = navController,
            target = Screen.Settings.route, color = 0xFFEEA47F)
        SettingButton(option = "Logout", navController = navController,
            target = Screen.LandingPage.route, color = 0xFFEEA47F)
    }
}

/*
    Function: Creates a button for the settings options to be placed into
    Parameters: option -> name of the option
                onClick() -> Link to the activity the button will send the user to
                color -> Hex code of the color used for the button
                modifier -> takes modifier parameters
    Return: None
 */
@Composable
fun SettingButton(option: String, navController: NavController, target: String, color: Long,
                  modifier: Modifier = Modifier) {
    Button(
        onClick = {navController.navigate(target)},
        colors = ButtonDefaults.buttonColors(Color(color)),
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = "$option",
            style = TextStyle(
                color = Color(0xFFB24444)
            )
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SettingsPreview() {
    TutorApp395ProjectTheme {
        BackgroundNoLogo()
        HomeBar(navController = NavController(LocalContext.current))
        SettingsColumn(navController = NavController(LocalContext.current))
    }
}