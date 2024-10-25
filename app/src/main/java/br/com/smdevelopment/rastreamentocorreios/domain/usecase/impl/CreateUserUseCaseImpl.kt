package br.com.smdevelopment.rastreamentocorreios.domain.usecase.impl

import android.util.Log
import br.com.smdevelopment.rastreamentocorreios.data.entities.retrofit.Resource
import br.com.smdevelopment.rastreamentocorreios.domain.usecase.CreateUserUseCase
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await

class CreateUserUseCaseImpl(private val auth: FirebaseAuth) : CreateUserUseCase {

    override suspend fun createUserAndPassword(
        email: String,
        password: String
    ): Flow<Resource<Boolean>> = callbackFlow {
        val onCompleteListener = OnCompleteListener<AuthResult> { task ->
            if (task.isSuccessful) {
                Log.d(TAG, "Successo: ${task.exception?.message}")
                trySend(Resource.Success(true))
            } else {
                Log.d(TAG, "Error: ${task.exception?.message}")
                trySend(Resource.Error(message = "Failed to create user: ${task.exception?.message}"))
            }
            close()
        }

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(onCompleteListener).await()
    }.flowOn(Dispatchers.IO)

    private companion object {
        const val TAG = "CreateUserUseCaseImpl"
    }
}