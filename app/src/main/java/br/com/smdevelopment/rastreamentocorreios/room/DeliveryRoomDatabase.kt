package br.com.smdevelopment.rastreamentocorreios.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import br.com.smdevelopment.rastreamentocorreios.converters.DeliveryDataConverter
import br.com.smdevelopment.rastreamentocorreios.entities.room.LocalDeliveryData
import br.com.smdevelopment.rastreamentocorreios.room.dao.DeliveryDao

@TypeConverters(DeliveryDataConverter::class)
@Database(entities = [LocalDeliveryData::class], version = 1)
abstract class DeliveryRoomDatabase : RoomDatabase() {
    abstract fun deliveryDao(): DeliveryDao
}