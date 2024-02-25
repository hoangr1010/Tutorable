package com.example.tutorapp395project.screen.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.tutorapp395project.viewModel.HomeViewModel

@Composable
fun HomeBar(
    navController: NavController,
    homeViewModel: HomeViewModel,
    modifier: Modifier = Modifier,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom,
        modifier = Modifier
            .fillMaxSize()
    ){
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = Color(0xFFD9D9D9),
                    shape = RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp)
                )
        ) {
            Row {
                homeViewModel.fields.forEach {field ->
                    Button(
                        onClick = { homeViewModel.changeViewState(field) },
                        colors = ButtonDefaults.buttonColors(Color(0xFFEEA47F)),
                        modifier = Modifier
                            .padding(16.dp)
                    ) {
                        Text(
                            text = field,
                            style = TextStyle(color = Color(0xFFB24444))
                        )
                    }
                }
            }
        }
    }
}
