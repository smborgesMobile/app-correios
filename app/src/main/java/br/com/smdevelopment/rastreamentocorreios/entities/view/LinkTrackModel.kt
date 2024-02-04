package br.com.smdevelopment.rastreamentocorreios.entities.view

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "delivery")
data class TrackingModel(
    @PrimaryKey
    val code: String,
    @ColumnInfo(name = "userId")
    val userId: String,
    @ColumnInfo(name = "link")
    val host: String,
    @ColumnInfo(name = "events")
    val events: List<EventModel>,
    @ColumnInfo(name = "time")
    val time: Double,
    @ColumnInfo(name = "quantity")
    val quantity: Int,
    @ColumnInfo(name = "service")
    val service: String,
    @ColumnInfo(name = "last")
    val last: String,
    @ColumnInfo(name = "icon")
    val icon: Int,
    @ColumnInfo(name = "isDelivered")
    val isDelivered: Boolean
) : Parcelable

@Parcelize
data class EventModel(
    val date: String,
    val time: String,
    val location: String,
    val status: String,
    val subStatus: List<String>,
    val icon: Int,
    val isDelivered: Boolean
) : Parcelable
