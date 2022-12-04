package br.com.smdevelopment.rastreamentocorreios.di

import br.com.smdevelopment.rastreamentocorreios.converters.DeliveryConverter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    //#region --- provide converter

    @Provides
    fun provideConverter() = DeliveryConverter()

    //#endregion -- provide converter
}