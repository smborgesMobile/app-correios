package br.com.smdevelopment.rastreamentocorreios.domain.usecase

interface GetPriceFeatureToggleUseCase {
    fun isPriceToggleEnabled(): Boolean
}