package br.com.smdevelopment.rastreamentocorreios.entities.view

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "delivery")
data class DeliveryData(

    @PrimaryKey
    @ColumnInfo(name = "code")
    val code: String,

    @ColumnInfo(name = "deliveredType")
    val deliveredType: DeliveredType,

    @ColumnInfo(name = "eventList")
    val eventList: List<EventData>,

    @ColumnInfo(name = "type")
    val type: String? = null,

    @ColumnInfo(name = "description")
    val description: String,

    @ColumnInfo(name = "destination")
    val destination: String,

    @ColumnInfo(name = "imageRes")
    val imageRes: Int
)

@Parcelize
data class EventData(
    val code: String,
    val description: String,
    val date: String,
    val postLocation: LocationData? = null,
    val destinationLocation: LocationData? = null,
    val iconUrl: Int,
    val formattedDestination: String
) : Parcelable

enum class DeliveredType {
    DELIVERED,
    IN_PROGRESS
}

@Parcelize
data class LocationData(
    val address: AddressData
) : Parcelable

@Parcelize
data class AddressData(
    val city: String,
    val uf: String
) : Parcelable