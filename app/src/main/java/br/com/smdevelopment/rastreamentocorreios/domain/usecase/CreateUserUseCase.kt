package br.com.smdevelopment.rastreamentocorreios.domain.usecase

import br.com.smdevelopment.rastreamentocorreios.data.entities.resource.Resource
import kotlinx.coroutines.flow.Flow

interface CreateUserUseCase {

    suspend fun createUserAndPassword(email: String, password: String): Flow<Resource<Boolean>>
}