package br.com.smdevelopment.rastreamentocorreios.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import br.com.smdevelopment.rastreamentocorreios.entities.view.DeliveryData

@Dao
interface DeliveryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNewDelivery(delivery: DeliveryData)

    @Query("SELECT * FROM delivery")
    fun getAllDeliveries(): List<DeliveryData>?
}