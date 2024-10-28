package br.com.smdevelopment.rastreamentocorreios.domain.abstraction

import br.com.smdevelopment.rastreamentocorreios.data.entities.resource.Resource
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.AuthResult
import kotlinx.coroutines.flow.Flow

interface AuthRepository {

    suspend fun googleSignIn(credential: AuthCredential): Flow<Resource<AuthResult>>
}