package br.com.smdevelopment.rastreamentocorreios.data.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import br.com.smdevelopment.rastreamentocorreios.data.entities.view.TrackingModel

@Dao
interface DeliveryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNewDelivery(delivery: TrackingModel)

    @Query("SELECT * FROM delivery WHERE userId = :userId")
    fun getAllDeliveries(userId: String): List<TrackingModel>?

    @Delete
    fun deleteDelivery(delivery: TrackingModel)
}