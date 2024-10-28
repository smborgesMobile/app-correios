package com.sborges.price.di

import com.sborges.price.data.api.PriceApiKtor
import com.sborges.price.data.api.PriceApiKtorImpl
import com.sborges.price.data.repositories.PriceRepositoryImpl
import com.sborges.price.domain.abstraction.PriceRepository
import com.sborges.price.domain.useCase.GetPriceUseCase
import com.sborges.price.presentation.screens.price.PriceViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val priceModules = module {
    // Apis
    single<PriceApiKtor> { PriceApiKtorImpl(get()) }

    // Repository
    single<PriceRepository> { PriceRepositoryImpl(get()) }

    // Use Case
    single { GetPriceUseCase(get()) }

    // ViewModel
    viewModel { PriceViewModel(get()) }
}