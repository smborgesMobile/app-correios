package br.com.smdevelopment.rastreamentocorreios.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import br.com.smdevelopment.rastreamentocorreios.entities.room.LocalDeliveryData

@Dao
interface DeliveryDao {

    @Insert
    fun insertNewDelivery(delivery: LocalDeliveryData)

    @Query("SELECT * FROM delivery")
    fun getAllDeliveries(): List<LocalDeliveryData>
}