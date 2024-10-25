package br.com.smdevelopment.rastreamentocorreios.data.repositories.impl

import android.util.Log
import br.com.smdevelopment.rastreamentocorreios.data.entities.retrofit.Resource
import br.com.smdevelopment.rastreamentocorreios.data.repositories.AuthRepository
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await

class AuthRepositoryImpl(private val firebaseAuth: FirebaseAuth) : AuthRepository {

    override suspend fun googleSignIn(credential: AuthCredential): Flow<Resource<AuthResult>> {
        return flow {
            emit(Resource.Loading())
            val result: AuthResult = firebaseAuth.signInWithCredential(credential).await()
            emit(Resource.Success(result))
        }.catch {
            Log.d(TAG, "Error: ${it.message}")
            emit(Resource.Error(message = "Erro ao realizar login com o Google"))
        }
    }

    companion object {
        const val TAG = "AuthRepositoryImpl"
    }
}