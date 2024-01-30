package br.com.smdevelopment.rastreamentocorreios.presentation.screens.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.smdevelopment.rastreamentocorreios.entities.retrofit.Resource
import br.com.smdevelopment.rastreamentocorreios.repositories.AuthRepository
import com.google.firebase.auth.AuthCredential
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LoginViewModel(
    private val authRepository: AuthRepository
) : ViewModel() {

    private var _googleState = MutableStateFlow<GoogleState>(GoogleState.Loading(false))
    val googleState: StateFlow<GoogleState> = _googleState

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


    sealed interface GoogleState {
        data class Loading(val isLoading: Boolean) : GoogleState
        object Success : GoogleState
        object Error : GoogleState
    }
}