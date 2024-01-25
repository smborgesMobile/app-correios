package br.com.smdevelopment.rastreamentocorreios.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import br.com.smdevelopment.rastreamentocorreios.converters.DeliveryDataConverter
import br.com.smdevelopment.rastreamentocorreios.entities.view.DeliveryData
import br.com.smdevelopment.rastreamentocorreios.room.dao.DeliveryDao

@Database(entities = [DeliveryData::class], version = 1)
@TypeConverters(DeliveryDataConverter::class)
abstract class DeliveryRoomDatabase : RoomDatabase() {
    abstract fun deliveryDao(): DeliveryDao
}