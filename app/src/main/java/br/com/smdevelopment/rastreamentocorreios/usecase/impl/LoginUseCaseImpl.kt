package br.com.smdevelopment.rastreamentocorreios.usecase.impl

import br.com.smdevelopment.rastreamentocorreios.usecase.LoginUseCase
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await

class LoginUseCaseImpl(private val firebaseAuth: FirebaseAuth) : LoginUseCase {

    override suspend fun login(password: String, email: String): Flow<Boolean> = callbackFlow {
        val onCompleteListener = OnCompleteListener<AuthResult> { task ->
            if (task.isSuccessful) {
                trySend(true)
            } else {
                trySend(false)
            }
            close()
        }

        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(onCompleteListener).await()
    }.flowOn(Dispatchers.IO)

}