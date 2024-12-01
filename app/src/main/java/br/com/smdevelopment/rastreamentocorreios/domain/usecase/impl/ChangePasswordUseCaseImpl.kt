package br.com.smdevelopment.rastreamentocorreios.domain.usecase.impl

import br.com.smdevelopment.rastreamentocorreios.domain.usecase.ChangePasswordUseCase
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await

class ChangePasswordUseCaseImpl(private val firebaseAuth: FirebaseAuth) : ChangePasswordUseCase {

    override suspend fun changePassword(email: String): Flow<Boolean> = flow {
        try {
            firebaseAuth.sendPasswordResetEmail(email).await()
            emit(true)
        } catch (e: Exception) {
            println("sm.borges - Exception: $e")
            emit(false)
        }
    }
}