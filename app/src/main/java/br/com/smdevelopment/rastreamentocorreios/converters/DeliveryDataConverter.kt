package br.com.smdevelopment.rastreamentocorreios.converters

import androidx.room.TypeConverter
import br.com.smdevelopment.rastreamentocorreios.entities.view.EventData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class DeliveryDataConverter {

    @TypeConverter
    fun fromDeliveryList(countryLang: List<EventData?>?): String? {
        if (countryLang == null) {
            return null
        }
        val gson = Gson()
        val type: Type = object : TypeToken<List<EventData?>?>() {}.type
        return gson.toJson(countryLang, type)
    }

    @TypeConverter
    fun toDeliveryList(countryLangString: String?): List<EventData>? {
        if (countryLangString == null) {
            return null
        }
        val gson = Gson()
        val type: Type = object : TypeToken<List<EventData?>?>() {}.type
        return gson.fromJson<List<EventData>>(countryLangString, type)
    }
}