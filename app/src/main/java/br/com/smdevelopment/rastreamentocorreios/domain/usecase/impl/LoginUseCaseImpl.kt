package br.com.smdevelopment.rastreamentocorreios.domain.usecase.impl

import br.com.smdevelopment.rastreamentocorreios.domain.usecase.LoginUseCase
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await

class LoginUseCaseImpl(private val firebaseAuth: FirebaseAuth) : LoginUseCase {

    override suspend fun login(password: String, email: String): Flow<Boolean> = flow {
        try {
            val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()
            emit(result.user != null)
        } catch (_: Exception) {
            emit(false)
        }
    }
}