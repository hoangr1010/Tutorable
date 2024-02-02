package com.example.tutorapp395project

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tutorapp395project.ui.theme.TutorApp395ProjectTheme

class Settings : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BackgroundNoLogo()
            HomeBar()
            SettingsColumn()
        }
    }
}

/*
    Function: Creates a column for all the settings buttons to be placed into
    Parameters: modifier -> takes modifier parameters
    Return: None
 */
@Composable
fun SettingsColumn(modifier: Modifier = Modifier) {
    Column(
        verticalArrangement = Arrangement.spacedBy(15.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
    ) {
        SettingButton(option = "Edit Profile", color = 0xFFEEA47F,
            onClick = { /*TODO*/ })
        SettingButton(option = "About", color = 0xFFEEA47F,
            onClick = { /*TODO*/ })
        SettingButton(option = "Logout", color = 0xFFEEA47F,
            onClick = { /*TODO*/ })
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
fun SettingButton(option: String, onClick: () -> Unit, color: Long, modifier: Modifier = Modifier) {
    Button(
        onClick = { onClick() },
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
        HomeBar()
        SettingsColumn()
    }
}