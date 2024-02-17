package com.example.tutorapp395project.viewModel
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.tutorapp395project.model.LoginRequest
import com.example.tutorapp395project.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val repository: AuthRepository
) : ViewModel() {

    var email = mutableStateOf("")
    var password = mutableStateOf("")
    var role = mutableStateOf("")

    fun onEmailChanged(newEmail: String) {
        email.value = newEmail
    }

    fun onPasswordChanged(newPassword: String) {
        password.value = newPassword
    }

    fun onRoleChanged(newRole: String) {
        role.value = newRole
    }

    fun onLoginClick(navController: NavController, target: String) {
        viewModelScope.launch {
            val loginRequest = LoginRequest(email.value, password.value, role.value)
            if (role.value == "Student") {
                val response = repository.loginStudent(loginRequest)
                if (response.isSuccessful) {
                    navController.navigate(target)
                }
                else {
                    print("Invalid Login")
                }
            }
            if (role.value == "Tutor") {
                val response = repository.loginStudent(loginRequest)
                if (response.isSuccessful) {
                    navController.navigate(target)
                } else {
                    print("Invalid Login")
                }
            }

        }
    }
}
