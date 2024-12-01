package br.com.smdevelopment.rastreamentocorreios.domain.usecase.impl

import br.com.smdevelopment.rastreamentocorreios.domain.usecase.ChangePasswordUseCase
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ChangePasswordUseCaseImpl(private val firebaseAuth: FirebaseAuth) : ChangePasswordUseCase {

    override suspend fun changePassword(email: String): Flow<Boolean> = flow {
        try {
            val response = firebaseAuth.sendPasswordResetEmail(email)
            emit(response.isSuccessful)
        } catch (_: Exception) {
            emit(false)
        }
    }
}