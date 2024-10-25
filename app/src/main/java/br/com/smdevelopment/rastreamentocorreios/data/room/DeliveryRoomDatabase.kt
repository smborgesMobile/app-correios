package br.com.smdevelopment.rastreamentocorreios.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import br.com.smdevelopment.rastreamentocorreios.data.entities.view.TrackingModel
import br.com.smdevelopment.rastreamentocorreios.data.mappers.DeliveryDataConverter
import br.com.smdevelopment.rastreamentocorreios.data.room.dao.DeliveryDao

@Database(entities = [TrackingModel::class], version = 5)
@TypeConverters(DeliveryDataConverter::class)
abstract class DeliveryRoomDatabase : RoomDatabase() {
    abstract fun deliveryDao(): DeliveryDao
}