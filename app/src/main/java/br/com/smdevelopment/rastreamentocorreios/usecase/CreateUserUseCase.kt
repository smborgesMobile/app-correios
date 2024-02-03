package br.com.smdevelopment.rastreamentocorreios.usecase

import br.com.smdevelopment.rastreamentocorreios.entities.retrofit.Resource
import kotlinx.coroutines.flow.Flow

interface CreateUserUseCase {

    suspend fun createUserAndPassword(email: String, password: String): Flow<Resource<Boolean>>
}