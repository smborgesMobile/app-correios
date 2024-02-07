package br.com.smdevelopment.rastreamentocorreios.presentation.screens.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.smdevelopment.rastreamentocorreios.entities.retrofit.Resource
import br.com.smdevelopment.rastreamentocorreios.repositories.AuthRepository
import br.com.smdevelopment.rastreamentocorreios.usecase.ChangePasswordUseCase
import br.com.smdevelopment.rastreamentocorreios.usecase.CreateUserUseCase
import br.com.smdevelopment.rastreamentocorreios.usecase.LoginUseCase
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel(
    private val authRepository: AuthRepository,
    private val firebaseUserCase: CreateUserUseCase,
    private val firebaseLoginUseCase: LoginUseCase,
    private val changePasswordUseCase: ChangePasswordUseCase
) : ViewModel() {

    private var _loginUiState = MutableStateFlow(LoginUiState())
    val loginUiState: StateFlow<LoginUiState> = _loginUiState.asStateFlow()

    val currentUser =
        FirebaseAuth.getInstance().currentUser

    fun googleSignIn(credential: AuthCredential) {
        _loginUiState.update { currentState ->
            currentState.copy(
                showGoogleLoginSuccess = false,
                showGoogleButtonLoading = true
            )
        }
        viewModelScope.launch(Dispatchers.Default) {
            authRepository.googleSignIn(credential).collect {
                when (it) {
                    is Resource.Success -> {
                        _loginUiState.update { currentState ->
                            currentState.copy(
                                showGoogleLoginSuccess = true,
                                showGoogleButtonLoading = false
                            )
                        }
                    }

                    is Resource.Error -> {
                        _loginUiState.update { currentState ->
                            currentState.copy(
                                showGoogleLoginError = true,
                                showGoogleButtonLoading = false
                            )
                        }
                    }

                    else -> {}
                }
            }
        }
    }

    fun sendChangePasswordEmail() {
        viewModelScope.launch(Dispatchers.Default) {
            changePasswordUseCase.changePassword(_loginUiState.value.userEmail)
                .catch {
                    _loginUiState.update { currentState ->
                        currentState.copy(
                            showChangePasswordError = true
                        )
                    }
                    Log.d("sm.borges", "exception: $it")
                }
                .collect { success ->
                    if (success) {
                        _loginUiState.update { currentState ->
                            currentState.copy(
                                showChangePasswordSuccess = true,
                                showChangePasswordError = false // Reset error flag
                            )
                        }
                    } else {
                        _loginUiState.update { currentState ->
                            currentState.copy(
                                showChangePasswordError = true
                            )
                        }
                    }
                }
        }
    }

    fun createAccount() {
        viewModelScope.launch(Dispatchers.Default) {
            _loginUiState.update { currentState ->
                currentState.copy(showCreateUserLoading = true)
            }

            firebaseUserCase.createUserAndPassword(
                _loginUiState.value.userEmail,
                _loginUiState.value.userPassword
            )
                .catch {
                    _loginUiState.update { currentState ->
                        currentState.copy(
                            showCreateUserError = true,
                            showCreateUserLoading = false
                        )
                    }
                }
                .collect {
                    when (it) {
                        is Resource.Success -> {
                            _loginUiState.update { currentState ->
                                currentState.copy(
                                    showCreateUserSuccess = true,
                                    showCreateUserLoading = false,
                                    showCreateUserError = false
                                )
                            }
                        }

                        is Resource.Error -> {
                            _loginUiState.update { currentState ->
                                currentState.copy(
                                    showCreateUserError = true,
                                    showCreateUserLoading = false
                                )
                            }
                        }

                        else -> {

                        }
                    }
                }
        }
    }

    fun loginUser() {
        viewModelScope.launch(Dispatchers.Default) {
            _loginUiState.update { currentState ->
                currentState.copy(showLoginLoading = true)
            }
            firebaseLoginUseCase.login(
                _loginUiState.value.userPassword,
                _loginUiState.value.userEmail
            ).catch {
                _loginUiState.update { currentState ->
                    currentState.copy(
                        showLoginError = true,
                        showLoginLoading = false
                    )
                }
            }.collect {
                if (it) {
                    _loginUiState.update { currentState ->
                        currentState.copy(
                            showLoginSuccess = true,
                            showLoginLoading = false,
                            showLoginError = false
                        )
                    }
                } else {
                    _loginUiState.update { currentState ->
                        currentState.copy(
                            showLoginError = true,
                            showLoginLoading = false
                        )
                    }
                }
            }
        }
    }

    fun changePasswordEyes(hide: Boolean) {
        _loginUiState.update { currentState ->
            currentState.copy(showPasswordEyes = hide)
        }
    }

    fun onEmailChange(email: String) {
        _loginUiState.update { currentState ->
            currentState.copy(
                userEmail = email,
                showCreateUserError = false,
                showLoginError = false,
                enableCreateAccountButton = isValidEmail(currentState.userEmail)
                        && isValidPassword(currentState.userPassword),
                enableLoginButton = isValidEmail(currentState.userEmail)
                        && isValidPassword(currentState.userPassword),
                showChangePasswordSuccess = false,
                showChangePasswordError = false
            )
        }
    }

    fun onPasswordChanged(password: String) {
        _loginUiState.update { currentState ->
            currentState.copy(
                userPassword = password,
                showCreateUserError = false,
                showLoginError = false,
                enableCreateAccountButton = isValidEmail(currentState.userEmail)
                        && isValidPassword(password),
                enableLoginButton = isValidEmail(currentState.userEmail)
                        && isValidPassword(password),
                showChangePasswordSuccess = false,
                showChangePasswordError = false
            )
        }
    }

    private fun isValidEmail(email: String): Boolean {
        val emailRegex = Regex("[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")
        return email.matches(emailRegex)
    }

    private fun isValidPassword(password: String): Boolean {
        return password.length >= 6
    }

    fun onPasswordErrorDisplayed() {
        _loginUiState.update {
            it.copy(showChangePasswordError = false)
        }
    }
}