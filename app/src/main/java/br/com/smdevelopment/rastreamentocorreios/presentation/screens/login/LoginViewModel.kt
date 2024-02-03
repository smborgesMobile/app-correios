package br.com.smdevelopment.rastreamentocorreios.presentation.screens.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.smdevelopment.rastreamentocorreios.entities.retrofit.Resource
import br.com.smdevelopment.rastreamentocorreios.repositories.AuthRepository
import br.com.smdevelopment.rastreamentocorreios.usecase.CreateUserUseCase
import br.com.smdevelopment.rastreamentocorreios.usecase.LoginUseCase
import com.google.firebase.auth.AuthCredential
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class LoginViewModel(
    private val authRepository: AuthRepository,
    private val firebaseUserCase: CreateUserUseCase,
    private val firebaseLoginUseCase: LoginUseCase
) : ViewModel() {

    private var _googleState = MutableStateFlow<GoogleState>(GoogleState.Loading(false))
    val googleState: StateFlow<GoogleState> = _googleState

    private var _password = MutableStateFlow("")
    val password: StateFlow<String> = _password

    private var _email = MutableStateFlow("")
    val email: StateFlow<String> = _email

    private var _passwordEyes = MutableStateFlow(false)
    val passwordEyes: StateFlow<Boolean> = _passwordEyes

    private var _createPasswordResult =
        MutableStateFlow<CreateAccountUIState>(CreateAccountUIState.Initial)
    val createPasswordResult: StateFlow<CreateAccountUIState> = _createPasswordResult

    private var _fieldValidation =
        MutableStateFlow<EmailAndPasswordValidationUiState>(EmailAndPasswordValidationUiState.AreInvalid)
    val fieldValidation: StateFlow<EmailAndPasswordValidationUiState> = _fieldValidation

    private var _loginUiState =
        MutableStateFlow<LoginAccountUIState>(LoginAccountUIState.Initial)
    val loginUiState: StateFlow<LoginAccountUIState> = _loginUiState

    fun googleSignIn(credential: AuthCredential) {
        _googleState.value = GoogleState.Loading(true)
        viewModelScope.launch(Dispatchers.Default) {
            authRepository.googleSignIn(credential).collect {
                when (it) {
                    is Resource.Success -> {
                        _googleState.emit(GoogleState.Success)
                    }

                    is Resource.Error -> {
                        _googleState.emit(GoogleState.Error)
                    }

                    else -> {}
                }
            }
        }
    }

    fun createAccount() {
        viewModelScope.launch(Dispatchers.Default) {
            _createPasswordResult.emit(CreateAccountUIState.Loading(true))
            firebaseUserCase.createUserAndPassword(email.value, password.value)
                .catch {
                    _createPasswordResult.emit(CreateAccountUIState.Error)
                }
                .collectLatest {
                    when (it) {
                        is Resource.Success -> {
                            _createPasswordResult.emit(CreateAccountUIState.Success)
                        }

                        is Resource.Error -> {
                            _createPasswordResult.emit(CreateAccountUIState.Error)
                        }

                        else -> {}
                    }

                    _createPasswordResult.emit(CreateAccountUIState.Loading(false))
                }
        }
    }

    fun loginUser() {
        viewModelScope.launch(Dispatchers.Default) {
            _loginUiState.emit(LoginAccountUIState.Loading(true))
            firebaseLoginUseCase.login(password.value, email.value)
                .catch {
                    _loginUiState.value = LoginAccountUIState.Error
                }
                .collectLatest {
                    if (it) {
                        _loginUiState.value = LoginAccountUIState.Success
                    } else {
                        _loginUiState.value = LoginAccountUIState.Error
                    }
                    _createPasswordResult.emit(CreateAccountUIState.Loading(false))
                }
        }
    }

    fun changePasswordEyes(hide: Boolean) {
        _passwordEyes.value = hide
    }

    fun onEmailChange(email: String) {
        validateFields(_email.value, _password.value)
        _email.value = email
    }

    fun onPasswordChanged(password: String) {
        validateFields(_email.value, _password.value)
        _password.value = password
    }

    private fun validateFields(email: String, password: String) {
        if (isValidEmail(email) && isValidPassword(password)) {
            _fieldValidation.value = EmailAndPasswordValidationUiState.AreValid
        } else {
            _fieldValidation.value = EmailAndPasswordValidationUiState.AreInvalid
        }
    }

    private fun isValidEmail(email: String): Boolean {
        val emailRegex = Regex("[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")
        return email.matches(emailRegex)
    }

    private fun isValidPassword(password: String): Boolean {
        // Implement your password validation logic here
        // Example: Return true if the password meets certain criteria
        return password.length >= 6
    }

    sealed interface GoogleState {
        data class Loading(val isLoading: Boolean) : GoogleState
        object Success : GoogleState
        object Error : GoogleState
    }

    sealed interface CreateAccountUIState {
        data class Loading(val isLoading: Boolean) : CreateAccountUIState
        object Success : CreateAccountUIState
        object Error : CreateAccountUIState
        object Initial : CreateAccountUIState
    }

    sealed interface LoginAccountUIState {
        data class Loading(val isLoading: Boolean) : LoginAccountUIState
        object Success : LoginAccountUIState
        object Error : LoginAccountUIState
        object Initial : LoginAccountUIState
    }

    sealed interface EmailAndPasswordValidationUiState {
        object AreValid : EmailAndPasswordValidationUiState
        object AreInvalid : EmailAndPasswordValidationUiState
    }
}