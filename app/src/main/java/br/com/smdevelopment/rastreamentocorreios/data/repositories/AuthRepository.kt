package br.com.smdevelopment.rastreamentocorreios.data.repositories

import br.com.smdevelopment.rastreamentocorreios.data.entities.retrofit.Resource
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.AuthResult
import kotlinx.coroutines.flow.Flow

interface AuthRepository {

    suspend fun googleSignIn(credential: AuthCredential): Flow<Resource<AuthResult>>
}