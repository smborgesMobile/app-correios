package br.com.smdevelopment.rastreamentocorreios.entities.view

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import br.com.smdevelopment.rastreamentocorreios.entities.retrofit.Event

@Entity(tableName = "delivery")
data class DeliveryData(

    @PrimaryKey
    @ColumnInfo(name = "code")
    val code: String,

    @ColumnInfo(name = "eventList")
    val eventList: List<Event>,

    @ColumnInfo(name = "type")
    val type: String? = null,

    @ColumnInfo(name = "description")
    val description: String,

    @ColumnInfo(name = "destination")
    val destination: String,

    @ColumnInfo(name = "imageRes")
    val imageRes: Int
)