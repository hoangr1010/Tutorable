package com.example.tutorapp395project

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role.Companion.Button
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tutorapp395project.ui.theme.TutorApp395ProjectTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TutorApp395ProjectTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Background()
                    Display("LOGIN")
                }
            }
        }
    }
}
@Composable
fun Background(modifier: Modifier = Modifier) {
    val image = painterResource(R.drawable.logo)
    Column (
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xFF00539C))
    ){
        Image(
            painter = image, // image file
            contentDescription = null,
            modifier = Modifier
                .padding(top = 68.dp, bottom = 508.dp)


            // Add Modifiers
        )
    }
}

@Composable
fun Display(name: String, modifier: Modifier = Modifier) {

    Column( // Main column to align all elements onto
        verticalArrangement = Arrangement.spacedBy(438.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .width(360.dp)
            .height(800.dp)
            .padding(start = 63.dp, top = 68.dp, end = 62.dp, bottom = 70.dp)
    ) {
        Row(
            //horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterHorizontally),
            //verticalAlignment = Alignment.Bottom,
            modifier = Modifier
                .border(
                    width = 1.dp, color = Color(0xFF000000),
                    shape = RoundedCornerShape(size = 15.dp)
                )
                .width(235.dp)
                .height(44.dp)
                .background(color = Color(0xFFEEA47F), shape = RoundedCornerShape(size = 15.dp))
                .padding(start = 99.dp, top = 15.dp, end = 99.dp, bottom = 15.dp)
                .clickable { }


        ) {
            Text(
                text = name,
                style = TextStyle(
                    fontSize = 12.sp,
                    //fontFamily = FontFamily(Font(R.font.roboto)),
                    fontWeight = FontWeight(400),
                    color = Color(0xFFB24444)
                ),
                modifier = Modifier
                    .width(35.dp)
                    .height(14.dp)
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
        Display("LOGIN")
    }
}