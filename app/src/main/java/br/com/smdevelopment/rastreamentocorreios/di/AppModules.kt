package br.com.smdevelopment.rastreamentocorreios.di

import androidx.room.Room
import br.com.smdevelopment.rastreamentocorreios.api.LinkTrackApi
import br.com.smdevelopment.rastreamentocorreios.notification.DeliveryNotificationChannel
import br.com.smdevelopment.rastreamentocorreios.presentation.MainViewModel
import br.com.smdevelopment.rastreamentocorreios.presentation.screens.delivered.DeliveredViewModel
import br.com.smdevelopment.rastreamentocorreios.presentation.screens.home.LinkTrackViewModel
import br.com.smdevelopment.rastreamentocorreios.presentation.screens.login.LoginViewModel
import br.com.smdevelopment.rastreamentocorreios.presentation.screens.pending.PendingScreenViewModel
import br.com.smdevelopment.rastreamentocorreios.repositories.AuthRepository
import br.com.smdevelopment.rastreamentocorreios.repositories.LinkTrackRepository
import br.com.smdevelopment.rastreamentocorreios.repositories.impl.AuthRepositoryImpl
import br.com.smdevelopment.rastreamentocorreios.repositories.impl.LinkTrackRepositoryImpl
import br.com.smdevelopment.rastreamentocorreios.room.DeliveryRoomDatabase
import br.com.smdevelopment.rastreamentocorreios.room.dao.DeliveryDao
import br.com.smdevelopment.rastreamentocorreios.routers.NotificationRouterImpl
import br.com.smdevelopment.rastreamentocorreios.usecase.ChangePasswordUseCase
import br.com.smdevelopment.rastreamentocorreios.usecase.CreateUserUseCase
import br.com.smdevelopment.rastreamentocorreios.usecase.DeliveredUseCase
import br.com.smdevelopment.rastreamentocorreios.usecase.InProgressUseCase
import br.com.smdevelopment.rastreamentocorreios.usecase.LoginUseCase
import br.com.smdevelopment.rastreamentocorreios.usecase.TrackingUseCase
import br.com.smdevelopment.rastreamentocorreios.usecase.UpdateCacheUseCase
import br.com.smdevelopment.rastreamentocorreios.usecase.impl.ChangePasswordUseCaseImpl
import br.com.smdevelopment.rastreamentocorreios.usecase.impl.CreateUserUseCaseImpl
import br.com.smdevelopment.rastreamentocorreios.usecase.impl.DeliveredUserCaseImpl
import br.com.smdevelopment.rastreamentocorreios.usecase.impl.GetAllTrackingUseCase
import br.com.smdevelopment.rastreamentocorreios.usecase.impl.InProgressUseCaseImpl
import br.com.smdevelopment.rastreamentocorreios.usecase.impl.LoginUseCaseImpl
import br.com.smdevelopment.rastreamentocorreios.usecase.impl.TrackingUseCaseImpl
import br.com.smdevelopment.rastreamentocorreios.usecase.impl.UpdateCacheUseCaseImpl
import com.google.firebase.auth.FirebaseAuth
import com.sborges.core.push.router.NotificationHandler
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
            linkTrackDao = get(),
            firebaseAuth = FirebaseAuth.getInstance()
        )
    }
    factory<AuthRepository> {
        AuthRepositoryImpl(firebaseAuth = FirebaseAuth.getInstance())
    }

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
            firebaseUserCase = get(),
            firebaseLoginUseCase = get(),
            changePasswordUseCase = get(),
        )
    }
    viewModel {
        MainViewModel()
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

    //Helps
    factory<DeliveryNotificationChannel> { DeliveryNotificationChannel(androidContext()) }

    // Routers
    factory<NotificationHandler> { NotificationRouterImpl() }
}