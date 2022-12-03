package br.com.smdevelopment.rastreamentocorreios.di.room

import android.content.Context
import androidx.room.Room
import br.com.smdevelopment.rastreamentocorreios.room.DeliveryRoomDatabase
import br.com.smdevelopment.rastreamentocorreios.room.dao.DeliveryDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DeliveryDataBaseModule {

    @Provides
    fun provideChannelDao(appDatabase: DeliveryRoomDatabase): DeliveryDao {
        return appDatabase.deliveryDao()
    }

    @Provides
    @Singleton
    fun provideAppDataBase(@ApplicationContext appContext: Context): DeliveryRoomDatabase {
        return Room.databaseBuilder(
            appContext,
            DeliveryRoomDatabase::class.java,
            ROOM_DATA_BASE
        ).build()
    }

    private companion object {
        const val ROOM_DATA_BASE = "DELIVERY_DATA_BASE"
    }
}