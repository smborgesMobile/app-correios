package br.com.smdevelopment.rastreamentocorreios.presentation.screens.login

data class LoginUiState(
    val showLoginLoading: Boolean = false,
    val showCreateUserLoading: Boolean = false,
    val showCreateUserError: Boolean = false,
    val showGoogleButtonLoading: Boolean = false,
    val userPassword: String = String(),
    val userEmail: String = String(),
    val showGoogleLoginSuccess: Boolean = false,
    val showGoogleLoginError: Boolean = false,
    val showLoginSuccess: Boolean = false,
    val showLoginError: Boolean = false,
    val showCreateUserSuccess: Boolean = false,
    val enableCreateAccountButton: Boolean = false,
    val enableLoginButton: Boolean = false,
    val showPasswordEyes: Boolean = false,
    val showChangePasswordError: Boolean = false,
    val showChangePasswordSuccess: Boolean = false
)
