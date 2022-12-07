package br.com.smdevelopment.rastreamentocorreios.di

import br.com.smdevelopment.rastreamentocorreios.business.DeliveryBusiness
import br.com.smdevelopment.rastreamentocorreios.business.DeliveryBusinessImpl
import br.com.smdevelopment.rastreamentocorreios.business.NotificationBusiness
import br.com.smdevelopment.rastreamentocorreios.business.NotificationBusinessImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(ViewModelComponent::class)
interface DeliveryBusinessModule {
    @Binds
    fun provideBusiness(business: DeliveryBusinessImpl): DeliveryBusiness
}

@Module
@InstallIn(SingletonComponent::class)
interface NotificationBusinessModule {
    @Binds
    fun provideBusiness(business: NotificationBusinessImpl): NotificationBusiness
}