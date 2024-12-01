package br.com.smdevelopment.rastreamentocorreios.presentation.screens.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.smdevelopment.rastreamentocorreios.data.entities.resource.Resource
import br.com.smdevelopment.rastreamentocorreios.domain.abstraction.AuthRepository
import br.com.smdevelopment.rastreamentocorreios.domain.usecase.ChangePasswordUseCase
import br.com.smdevelopment.rastreamentocorreios.domain.usecase.CreateUserUseCase
import br.com.smdevelopment.rastreamentocorreios.domain.usecase.LoginUseCase
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel(
    private val authRepository: AuthRepository,
    private val createUserUseCase: CreateUserUseCase,
    private val loginUseCase: LoginUseCase,
    private val changePasswordUseCase: ChangePasswordUseCase
) : ViewModel() {

    private val _loginUiState = MutableStateFlow(LoginUiState())
    val loginUiState: StateFlow<LoginUiState> = _loginUiState.asStateFlow()

    val currentUser = FirebaseAuth.getInstance().currentUser

    fun onEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.EmailChanged -> updateEmail(event.email)
            is LoginEvent.PasswordChanged -> updatePassword(event.password)
            is LoginEvent.GoogleSignIn -> handleGoogleSignIn(event.credential)
            is LoginEvent.CreateAccount -> createAccount()
            is LoginEvent.LoginUser -> loginUser()
            is LoginEvent.SendChangePasswordEmail -> sendChangePasswordEmail()
            is LoginEvent.ChangePasswordEyes -> togglePasswordVisibility(event.hide)
        }
    }

    private fun handleGoogleSignIn(credential: AuthCredential) {
        updateLoginState(isGoogleButtonLoading = true)
        executeAction {
            authRepository.googleSignIn(credential).collect { result ->
                when (result) {
                    is Resource.Success -> updateLoginState(isGoogleLoginSuccess = true)
                    is Resource.Error -> updateLoginState(isGoogleLoginError = true)
                    else -> Unit
                }
            }
        }
    }

    private fun sendChangePasswordEmail() {
        executeAction {
            changePasswordUseCase.changePassword(_loginUiState.value.userEmail)
                .collect { success ->
                    updateLoginState(
                        isChangePasswordSuccess = success,
                        isChangePasswordError = !success
                    )
                }
        }
    }

    private fun createAccount() {
        updateLoginState(isCreateUserLoading = true)
        executeAction {
            createUserUseCase.createUserAndPassword(
                _loginUiState.value.userEmail,
                _loginUiState.value.userPassword
            ).collect { result ->
                when (result) {
                    is Resource.Success -> {
                        updateLoginState(
                            isCreateUserSuccess = true,
                            isCreateUserError = false
                        )

                        Log.d("sm.borges", "ViewModelSuccess")
                    }

                    is Resource.Error -> {
                        updateLoginState(isCreateUserError = true)
                        Log.d("sm.borges", "ViewModelError")
                    }
                    else -> Unit
                }
            }
        }
    }

    private fun loginUser() {
        updateLoginState(isLoginLoading = true)
        executeAction {
            loginUseCase.login(
                _loginUiState.value.userPassword,
                _loginUiState.value.userEmail
            ).collect { success ->
                updateLoginState(
                    isLoginSuccess = success,
                    isLoginError = success.not()
                )
            }
        }
    }

    private fun togglePasswordVisibility(hide: Boolean) {
        _loginUiState.update { it.copy(showPasswordEyes = hide) }
    }

    private fun updateEmail(email: String) {
        _loginUiState.update { currentState ->
            currentState.copy(
                userEmail = email,
                enableCreateAccountButton = isValidInput(email, currentState.userPassword),
                enableLoginButton = isValidInput(email, currentState.userPassword),
                showLoginError = false,
                showCreateUserError = false
            )
        }
    }

    private fun updatePassword(password: String) {
        _loginUiState.update { currentState ->
            currentState.copy(
                userPassword = password,
                enableCreateAccountButton = isValidInput(currentState.userEmail, password),
                enableLoginButton = isValidInput(currentState.userEmail, password),
                showLoginError = false,
                showCreateUserError = false
            )
        }
    }

    private fun isValidInput(email: String, password: String) =
        isValidEmail(email) && isValidPassword(password)

    private fun isValidEmail(email: String): Boolean {
        val emailRegex = Regex("[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")
        return email.matches(emailRegex)
    }

    private fun isValidPassword(password: String): Boolean {
        return password.length >= 6
    }

    private fun updateLoginState(
        isLoginLoading: Boolean = false,
        isLoginSuccess: Boolean = false,
        isLoginError: Boolean = false,
        isCreateUserLoading: Boolean = false,
        isCreateUserSuccess: Boolean = false,
        isCreateUserError: Boolean = false,
        isGoogleButtonLoading: Boolean = false,
        isGoogleLoginSuccess: Boolean = false,
        isGoogleLoginError: Boolean = false,
        isChangePasswordSuccess: Boolean = false,
        isChangePasswordError: Boolean = false
    ) {
        _loginUiState.update { currentState ->
            currentState.copy(
                showLoginLoading = isLoginLoading,
                showLoginSuccess = isLoginSuccess,
                showLoginError = isLoginError,
                showCreateUserLoading = isCreateUserLoading,
                showCreateUserSuccess = isCreateUserSuccess,
                showCreateUserError = isCreateUserError,
                showGoogleButtonLoading = isGoogleButtonLoading,
                showGoogleLoginSuccess = isGoogleLoginSuccess,
                showGoogleLoginError = isGoogleLoginError,
                showChangePasswordSuccess = isChangePasswordSuccess,
                showChangePasswordError = isChangePasswordError
            )
        }
    }

    private fun executeAction(action: suspend () -> Unit) {
        viewModelScope.launch(Dispatchers.Main) {
            try {
                action()
            } catch (e: Exception) {
                updateLoginState(isLoginError = true)
            }
        }
    }
}