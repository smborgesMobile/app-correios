package br.com.smdevelopment.rastreamentocorreios.di

import androidx.room.Room
import br.com.smdevelopment.rastreamentocorreios.api.LinkTrackApi
import br.com.smdevelopment.rastreamentocorreios.converters.DeliveryConverter
import br.com.smdevelopment.rastreamentocorreios.presentation.screens.home.LinkTrackViewModel
import br.com.smdevelopment.rastreamentocorreios.repositories.LinkTrackRepository
import br.com.smdevelopment.rastreamentocorreios.repositories.impl.LinkTrackRepositoryImpl
import br.com.smdevelopment.rastreamentocorreios.room.DeliveryRoomDatabase
import br.com.smdevelopment.rastreamentocorreios.room.dao.DeliveryDao
import br.com.smdevelopment.rastreamentocorreios.usecase.TrackingUseCase
import br.com.smdevelopment.rastreamentocorreios.usecase.impl.GetAllTrackingUseCase
import br.com.smdevelopment.rastreamentocorreios.usecase.impl.TrackingUseCaseImpl
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    // apis
    single<LinkTrackApi> { RetrofitFactory.getClient().create(LinkTrackApi::class.java) }

    // repositories
    factory<LinkTrackRepository> {
        LinkTrackRepositoryImpl(
            api = get(),
            linkTrackDao = get()
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
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    single<DeliveryDao> { get<DeliveryRoomDatabase>().deliveryDao() }

    // view models
    viewModel {
        LinkTrackViewModel(
            trackingUseCase = get(),
            getAllTrackingUseCase = get()
        )
    }

    // use cases
    factory<TrackingUseCase> { TrackingUseCaseImpl(linkTrackRepository = get()) }
    factory<GetAllTrackingUseCase> { GetAllTrackingUseCase(repository = get()) }
}