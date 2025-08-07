package br.com.smdevelopment.rastreamentocorreios.domain.di

import androidx.room.Room
import br.com.smdevelopment.rastreamentocorreios.data.api.CorreiosRapidApiKtor
import br.com.smdevelopment.rastreamentocorreios.data.api.CorreiosRapidApiKtorImpl
import br.com.smdevelopment.rastreamentocorreios.data.mappers.CorreiosRapidApiMapper
import br.com.smdevelopment.rastreamentocorreios.data.mappers.LinkTrackDomainMapper
import br.com.smdevelopment.rastreamentocorreios.data.repositories.impl.AuthRepositoryImpl
import br.com.smdevelopment.rastreamentocorreios.data.repositories.impl.LinkTrackRepositoryImpl
import br.com.smdevelopment.rastreamentocorreios.data.room.DeliveryRoomDatabase
import br.com.smdevelopment.rastreamentocorreios.domain.abstraction.AuthRepository
import br.com.smdevelopment.rastreamentocorreios.domain.abstraction.LinkTrackRepository
import br.com.smdevelopment.rastreamentocorreios.domain.routers.NotificationRouterImpl
import br.com.smdevelopment.rastreamentocorreios.domain.usecase.ChangePasswordUseCase
import br.com.smdevelopment.rastreamentocorreios.domain.usecase.CreateUserUseCase
import br.com.smdevelopment.rastreamentocorreios.domain.usecase.DeliveredUseCase
import br.com.smdevelopment.rastreamentocorreios.domain.usecase.GetPriceFeatureToggleUseCase
import br.com.smdevelopment.rastreamentocorreios.domain.usecase.InProgressUseCase
import br.com.smdevelopment.rastreamentocorreios.domain.usecase.LoginUseCase
import br.com.smdevelopment.rastreamentocorreios.domain.usecase.TrackingUseCase
import br.com.smdevelopment.rastreamentocorreios.domain.usecase.UpdateCacheUseCase
import br.com.smdevelopment.rastreamentocorreios.domain.usecase.impl.ChangePasswordUseCaseImpl
import br.com.smdevelopment.rastreamentocorreios.domain.usecase.impl.CreateUserUseCaseImpl
import br.com.smdevelopment.rastreamentocorreios.domain.usecase.impl.DeliveredUserCaseImpl
import br.com.smdevelopment.rastreamentocorreios.domain.usecase.impl.GetAllTrackingUseCase
import br.com.smdevelopment.rastreamentocorreios.domain.usecase.impl.GetPriceFeatureToggleUseCaseImpl
import br.com.smdevelopment.rastreamentocorreios.domain.usecase.impl.InProgressUseCaseImpl
import br.com.smdevelopment.rastreamentocorreios.domain.usecase.impl.LoginUseCaseImpl
import br.com.smdevelopment.rastreamentocorreios.domain.usecase.impl.TrackingUseCaseImpl
import br.com.smdevelopment.rastreamentocorreios.domain.usecase.impl.UpdateCacheUseCaseImpl
import br.com.smdevelopment.rastreamentocorreios.notification.DeliveryNotificationChannel
import br.com.smdevelopment.rastreamentocorreios.presentation.MainViewModel
import br.com.smdevelopment.rastreamentocorreios.presentation.screens.delivered.DeliveredViewModel
import br.com.smdevelopment.rastreamentocorreios.presentation.screens.home.LinkTrackViewModel
import br.com.smdevelopment.rastreamentocorreios.presentation.screens.login.LoginViewModel
import br.com.smdevelopment.rastreamentocorreios.presentation.screens.pending.PendingScreenViewModel
import com.google.firebase.auth.FirebaseAuth
import com.sborges.core.push.router.NotificationHandler
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    // Api
    single<CorreiosRapidApiKtor> { CorreiosRapidApiKtorImpl(get()) }

    // Mappers
    single { LinkTrackDomainMapper(FirebaseAuth.getInstance()) }
    single { CorreiosRapidApiMapper(FirebaseAuth.getInstance()) }

    // Room
    single {
        Room.databaseBuilder(
            androidContext(),
            DeliveryRoomDatabase::class.java, "delivery_database"
        ).fallbackToDestructiveMigration().build()
    }
    single { get<DeliveryRoomDatabase>().deliveryDao() }

    // Repositories
    factory<LinkTrackRepository> { 
        LinkTrackRepositoryImpl(
            api = get(),
            linkTrackDao = get(),
            firebaseAuth = FirebaseAuth.getInstance(),
            mapper = get()
        ) 
    }
    factory<AuthRepository> { AuthRepositoryImpl(firebaseAuth = FirebaseAuth.getInstance()) }

    // View models
    viewModel {
        LinkTrackViewModel(
            trackingUseCase = get(),
            getAllTrackingUseCase = get(),
            appLaunchCounterUseCase = get()
        )
    }
    viewModel {
        DeliveredViewModel(
            deliveredUseCase = get()
        )
    }
    viewModel {
        PendingScreenViewModel(
            useCase = get()
        )
    }
    viewModel {
        LoginViewModel(
            authRepository = get(),
            createUserUseCase = get(),
            loginUseCase = get(),
            changePasswordUseCase = get()
        )
    }
    viewModel {
        MainViewModel(getPriceFeatureToggleUseCase = get())
    }

    // use cases
    factory<TrackingUseCase> { TrackingUseCaseImpl(linkTrackRepository = get()) }
    factory<GetAllTrackingUseCase> { GetAllTrackingUseCase(repository = get()) }
    factory<DeliveredUseCase> { DeliveredUserCaseImpl(repository = get()) }
    factory<InProgressUseCase> { InProgressUseCaseImpl(repository = get()) }
    factory<UpdateCacheUseCase> { UpdateCacheUseCaseImpl(repository = get()) }
    factory<CreateUserUseCase> { CreateUserUseCaseImpl(auth = FirebaseAuth.getInstance()) }
    factory<LoginUseCase> { LoginUseCaseImpl(firebaseAuth = FirebaseAuth.getInstance()) }
    factory<ChangePasswordUseCase> { ChangePasswordUseCaseImpl(firebaseAuth = FirebaseAuth.getInstance()) }
    factory<GetPriceFeatureToggleUseCase> { GetPriceFeatureToggleUseCaseImpl(featureToggle = get()) }

    //Helps
    factory<DeliveryNotificationChannel> { DeliveryNotificationChannel(androidContext()) }

    // Routers
    factory<NotificationHandler> { NotificationRouterImpl() }
}