package br.com.smdevelopment.rastreamentocorreios.converters

import androidx.room.TypeConverter
import br.com.smdevelopment.rastreamentocorreios.entities.retrofit.Event
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class DeliveryDataConverter {

    @TypeConverter
    fun fromDeliveryList(countryLang: List<Event?>?): String? {
        if (countryLang == null) {
            return null
        }
        val gson = Gson()
        val type: Type = object : TypeToken<List<Event?>?>() {}.type
        return gson.toJson(countryLang, type)
    }

    @TypeConverter
    fun toDeliveryList(countryLangString: String?): List<Event>? {
        if (countryLangString == null) {
            return null
        }
        val gson = Gson()
        val type: Type = object : TypeToken<List<Event?>?>() {}.type
        return gson.fromJson<List<Event>>(countryLangString, type)
    }
}