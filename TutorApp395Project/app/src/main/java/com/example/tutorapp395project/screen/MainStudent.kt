package com.example.tutorapp395project.screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.tutorapp395project.R
import com.example.tutorapp395project.screen.view.HomeBar
import com.example.tutorapp395project.screen.view.SettingsColumn
import com.example.tutorapp395project.screen.view.StudentAppointmentLayout
import com.example.tutorapp395project.screen.view.StudentProfileColumn
import com.example.tutorapp395project.viewModel.AuthViewModel
import com.example.tutorapp395project.viewModel.HomeViewModel

@Composable
fun MainStudent(
    navController: NavController,
    authViewModel: AuthViewModel,
    homeViewModel: HomeViewModel = viewModel()
) {

    Scaffold(
        containerColor = Color(0xFF00539C),
        bottomBar = { HomeBar(navController = navController, homeViewModel = homeViewModel) },
        content = {
            when {
                homeViewModel.viewState.value == "profile" -> {
                    StudentProfileColumn(
                        image = R.drawable.user_image,
                        authViewModel = authViewModel,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(it)
                    )

                }
                homeViewModel.viewState.value == "schedule" -> {
                    StudentAppointmentLayout(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(it)
                    )
                }
                homeViewModel.viewState.value == "setting" -> {
                    SettingsColumn(
                        navController = navController,
                        authViewModel = authViewModel,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(it)
                    )
                }
            }
        }
    )
}

