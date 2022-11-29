package br.com.smdevelopment.rastreamentocorreios.presentation.di

import br.com.smdevelopment.rastreamentocorreios.presentation.repositories.DeliveryRepository
import br.com.smdevelopment.rastreamentocorreios.presentation.repositories.DeliveryRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
interface RepositoryModules {
    @Binds
    fun provideMainRepository(repository: DeliveryRepositoryImpl): DeliveryRepository
}