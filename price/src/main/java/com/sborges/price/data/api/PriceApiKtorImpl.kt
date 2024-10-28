package com.sborges.price.data.api

import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.sborges.price.data.entities.PriceResponseItem
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.forms.submitForm
import io.ktor.http.Parameters

class PriceApiKtorImpl(
    private val httpClient: HttpClient
) : PriceApiKtor {

    override suspend fun getPrices(
        originZipCode: String,
        destinationZipCode: String,
        weight: Double,
        height: String,
        width: String,
        length: String
    ): List<PriceResponseItem> =
        try {
            val response = httpClient.submitForm(
                url = "${BASE_URL}index.php",
                formParameters = Parameters.build {
                    append(KEY_CEP_ORIGEM, originZipCode)
                    append(KEY_CEP_DESTINO, destinationZipCode)
                    append(KEY_PESO, weight.toString())
                    append(KEY_ALTURA, height)
                    append(KEY_LARGURA, width)
                    append(KEY_COMPRIMENTO, length)
                }
            )

            val json = response.body<String>()
            Log.d(TAG, "Response JSON: $json")

            val deliveryOptionListType = object : TypeToken<List<PriceResponseItem>>() {}.type
            Gson().fromJson(json, deliveryOptionListType)
        } catch (e: Exception) {
            Log.e(TAG, "Error fetching prices: ${e.message}", e)
            emptyList()
        }


    private companion object {
        private const val BASE_URL = "https://web.correiosprecoseprazos.com/"
        private const val TAG = "PriceApiKtorImpl"

        // Form parameter keys
        private const val KEY_CEP_ORIGEM = "cepOrigem"
        private const val KEY_CEP_DESTINO = "cepDestino"
        private const val KEY_PESO = "peso"
        private const val KEY_ALTURA = "altura"
        private const val KEY_LARGURA = "largura"
        private const val KEY_COMPRIMENTO = "comprimento"
    }
}