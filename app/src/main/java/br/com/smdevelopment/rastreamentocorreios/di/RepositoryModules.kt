package br.com.smdevelopment.rastreamentocorreios.di

import br.com.smdevelopment.rastreamentocorreios.repositories.DeliveryRepository
import br.com.smdevelopment.rastreamentocorreios.repositories.DeliveryRepositoryImpl
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