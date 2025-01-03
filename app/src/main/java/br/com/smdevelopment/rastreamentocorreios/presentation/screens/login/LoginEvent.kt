package br.com.smdevelopment.rastreamentocorreios.presentation.screens.login

import com.google.firebase.auth.AuthCredential

sealed class LoginEvent {
    data class EmailChanged(val email: String) : LoginEvent()
    data class PasswordChanged(val password: String) : LoginEvent()
    data class GoogleSignIn(val credential: AuthCredential) : LoginEvent()
    data object CreateAccount : LoginEvent()
    data object LoginUser : LoginEvent()
    data object SendChangePasswordEmail : LoginEvent()
    data class ChangePasswordEyes(val hide: Boolean) : LoginEvent()
}