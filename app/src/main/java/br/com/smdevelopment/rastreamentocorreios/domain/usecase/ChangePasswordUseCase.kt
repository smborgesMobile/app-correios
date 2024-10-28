package br.com.smdevelopment.rastreamentocorreios.domain.usecase

import kotlinx.coroutines.flow.Flow

interface ChangePasswordUseCase {

    suspend fun changePassword(email: String): Flow<Boolean>
}