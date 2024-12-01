package br.com.smdevelopment.rastreamentocorreios.domain.usecase.impl

import br.com.smdevelopment.rastreamentocorreios.data.entities.resource.Resource
import br.com.smdevelopment.rastreamentocorreios.domain.usecase.CreateUserUseCase
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class CreateUserUseCaseImpl(private val auth: FirebaseAuth) : CreateUserUseCase {

    override suspend fun createUserAndPassword(
        email: String,
        password: String
    ): Flow<Resource<Boolean>> = flow {
        try {
            val result = auth.createUserWithEmailAndPassword(email, password)
            emit(Resource.Success(result.isSuccessful))
        } catch (e: Exception) {
            emit(Resource.Error(message = "Failed to create user: ${e.message}"))
        }
    }
}