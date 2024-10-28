package br.com.smdevelopment.rastreamentocorreios.data.mappers

import androidx.room.TypeConverter
import br.com.smdevelopment.rastreamentocorreios.data.entities.view.EventModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class DeliveryDataConverter {

    @TypeConverter
    fun fromDeliveryList(countryLang: List<EventModel?>?): String? {
        if (countryLang == null) {
            return null
        }
        val gson = Gson()
        val type: Type = object : TypeToken<List<EventModel?>?>() {}.type
        return gson.toJson(countryLang, type)
    }

    @TypeConverter
    fun toDeliveryList(countryLangString: String?): List<EventModel>? {
        if (countryLangString == null) {
            return null
        }
        val gson = Gson()
        val type: Type = object : TypeToken<List<EventModel?>?>() {}.type
        return gson.fromJson<List<EventModel>>(countryLangString, type)
    }
}