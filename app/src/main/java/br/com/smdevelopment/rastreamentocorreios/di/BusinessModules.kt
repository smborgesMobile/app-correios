package br.com.smdevelopment.rastreamentocorreios.di

import br.com.smdevelopment.rastreamentocorreios.business.DeliveryBusiness
import br.com.smdevelopment.rastreamentocorreios.business.DeliveryBusinessImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
interface BusinessModules {

    @Binds
    fun provideBusiness(business: DeliveryBusinessImpl): DeliveryBusiness
}