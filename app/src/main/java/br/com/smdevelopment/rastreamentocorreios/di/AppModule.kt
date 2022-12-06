package br.com.smdevelopment.rastreamentocorreios.di

import android.app.Application
import android.content.Context
import br.com.smdevelopment.rastreamentocorreios.converters.DeliveryConverter
import br.com.smdevelopment.rastreamentocorreios.notification.NotificationManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    //#region --- provide converter

    @Provides
    fun provideConverter() = DeliveryConverter()

    //#endregion -- provide converter

    //#region --- provider notification manager

    @Provides
    fun provideNotificationManager(@ApplicationContext appContext: Context) =
        NotificationManager(application = appContext as Application)

    //#endregion --- provider notification manager
}