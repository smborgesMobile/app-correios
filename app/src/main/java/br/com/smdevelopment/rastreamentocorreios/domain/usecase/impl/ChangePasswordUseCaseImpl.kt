package br.com.smdevelopment.rastreamentocorreios.domain.usecase.impl

import br.com.smdevelopment.rastreamentocorreios.domain.usecase.ChangePasswordUseCase
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await

class ChangePasswordUseCaseImpl(private val firebaseAuth: FirebaseAuth) : ChangePasswordUseCase {

    override suspend fun changePassword(email: String): Flow<Boolean> = callbackFlow {
        val onCompleteListener = OnCompleteListener<Void> { task ->
            if (task.isSuccessful) {
                trySend(true)
            } else {
                trySend(false)
            }
        }

        firebaseAuth.sendPasswordResetEmail(email)
            .addOnCompleteListener(onCompleteListener).await()

        awaitClose { channel.close() }
    }.flowOn(Dispatchers.IO)
}