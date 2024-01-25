package br.com.smdevelopment.rastreamentocorreios.di

import androidx.room.Room
import br.com.smdevelopment.rastreamentocorreios.api.DeliveryApi
import br.com.smdevelopment.rastreamentocorreios.api.LinkTrackApi
import br.com.smdevelopment.rastreamentocorreios.business.DeliveryBusiness
import br.com.smdevelopment.rastreamentocorreios.business.DeliveryBusinessImpl
import br.com.smdevelopment.rastreamentocorreios.business.NotificationBusiness
import br.com.smdevelopment.rastreamentocorreios.business.NotificationBusinessImpl
import br.com.smdevelopment.rastreamentocorreios.converters.DeliveryConverter
import br.com.smdevelopment.rastreamentocorreios.presentation.screens.delivered.DeliveredViewModel
import br.com.smdevelopment.rastreamentocorreios.presentation.screens.home.HomeViewModel
import br.com.smdevelopment.rastreamentocorreios.presentation.screens.home.LinkTrackViewModel
import br.com.smdevelopment.rastreamentocorreios.presentation.screens.pending.PendingViewModel
import br.com.smdevelopment.rastreamentocorreios.repositories.DeliveryRepository
import br.com.smdevelopment.rastreamentocorreios.repositories.DeliveryRepositoryImpl
import br.com.smdevelopment.rastreamentocorreios.repositories.LinkTrackRepository
import br.com.smdevelopment.rastreamentocorreios.repositories.impl.LinkTrackRepositoryImpl
import br.com.smdevelopment.rastreamentocorreios.room.DeliveryRoomDatabase
import br.com.smdevelopment.rastreamentocorreios.room.dao.DeliveryDao
import br.com.smdevelopment.rastreamentocorreios.usecase.TrackingUseCase
import br.com.smdevelopment.rastreamentocorreios.usecase.impl.TrackingUseCaseImpl
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    // apis
    single<DeliveryApi> { RetrofitFactory.getClient().create(DeliveryApi::class.java) }
    single<LinkTrackApi> { RetrofitFactory.getClient().create(LinkTrackApi::class.java) }

    // repositories
    factory<DeliveryRepository> {
        DeliveryRepositoryImpl(
            api = get(),
            deliveryDao = get()
        )
    }

    factory<LinkTrackRepository> {
        LinkTrackRepositoryImpl(api = get())
    }

    // business
    factory<DeliveryBusiness> {
        DeliveryBusinessImpl(
            deliveryRepository = get(),
            converter = get()
        )
    }

    factory<NotificationBusiness> {
        NotificationBusinessImpl(
            repository = get(),
            converter = get()
        )
    }

    // converter
    factory<DeliveryConverter> { DeliveryConverter() }

    // dao
    single {
        Room.databaseBuilder(
            context = androidContext(),
            klass = DeliveryRoomDatabase::class.java,
            name = "my_database"
        ).build()
    }

    single<DeliveryDao> { get<DeliveryRoomDatabase>().deliveryDao() }

    // view models
    viewModel { PendingViewModel(business = get()) }
    viewModel { HomeViewModel(business = get()) }
    viewModel { DeliveredViewModel(business = get()) }
    viewModel { LinkTrackViewModel(trackingUseCase = get()) }

    // use cases
    factory<TrackingUseCase> { TrackingUseCaseImpl(linkTrackRepository = get()) }
}