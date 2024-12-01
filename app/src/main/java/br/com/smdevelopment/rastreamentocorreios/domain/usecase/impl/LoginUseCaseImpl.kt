package br.com.smdevelopment.rastreamentocorreios.domain.usecase.impl

import br.com.smdevelopment.rastreamentocorreios.domain.usecase.LoginUseCase
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class LoginUseCaseImpl(private val firebaseAuth: FirebaseAuth) : LoginUseCase {

    override suspend fun login(password: String, email: String): Flow<Boolean> = flow {
        try {
            val result = firebaseAuth.signInWithEmailAndPassword(email, password)
            emit(result.isSuccessful)
        } catch (e: Exception) {
            emit(false)
        }
    }
}