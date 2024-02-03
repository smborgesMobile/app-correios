package br.com.smdevelopment.rastreamentocorreios.usecase

import kotlinx.coroutines.flow.Flow

interface LoginUseCase {
    suspend  fun login(password: String, email: String): Flow<Boolean>
}