package com.example.tutorapp395project.repository

<<<<<<< HEAD
import com.example.tutorapp395project.network.AuthService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/*
    Purpose: interface to define the login request
    Parameters: None
    Returns: Response<LoginResponse>
 */
interface AuthRepository {
    /*
        Purpose: function to perform the login
        Parameters:
            - email: String
            - password: String
            - role: String
            - onLoginSuccess: () -> Unit
            - onLoginFailed: (String) -> Unit
        Returns: Unit
     */
    fun preformLogin(
        email: String, password: String, role: String,
        onLoginSuccess: () -> Unit, onLoginFailed: (String) -> Unit
    ) {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://localhost:8080/auth/login")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val authService = retrofit.create(AuthService::class.java)
        //val call = AuthService.login(email, password, role)
    }
}

=======
import com.example.tutorapp395project.model.LoginRequest
import com.example.tutorapp395project.model.LoginStudentResponse
import com.example.tutorapp395project.network.RetrofitInstance
import retrofit2.Response

class AuthRepository {

    suspend fun loginStudent(loginRequest: LoginRequest): Response<LoginStudentResponse> {
        return RetrofitInstance.loginApi.loginStudent(loginRequest)
    }

    suspend fun loginTutor(loginRequest: LoginRequest): Response<LoginStudentResponse> {
        return RetrofitInstance.loginApi.loginStudent(loginRequest)
    }
}
>>>>>>> fddd448e1c9c85c4e4966bce1170441f9d0360ce
