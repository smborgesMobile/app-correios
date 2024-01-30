package br.com.smdevelopment.rastreamentocorreios.repositories.impl

import br.com.smdevelopment.rastreamentocorreios.entities.retrofit.Resource
import br.com.smdevelopment.rastreamentocorreios.repositories.AuthRepository
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
            emit(Resource.Error(message = "Erro ao realizar login com o Google"))
        }
    }
}