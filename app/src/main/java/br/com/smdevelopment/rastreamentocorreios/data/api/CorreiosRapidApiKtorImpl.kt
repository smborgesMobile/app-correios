// New API implementation for RapidAPI
package br.com.smdevelopment.rastreamentocorreios.data.api

import br.com.smdevelopment.rastreamentocorreios.data.entities.CorreiosRapidApiResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.parameter

class CorreiosRapidApiKtorImpl(
    private val httpClient: HttpClient
) : CorreiosRapidApiKtor {

    override suspend fun fetchTrackByCode(code: String): CorreiosRapidApiResponse {
        val response = httpClient.get("$BASE_URL/objeto") {
            header(HEADER_HOST, RAPIDAPI_HOST)
            header(HEADER_KEY, RAPIDAPI_KEY)
            header(
                AUTHORIZATION,
                HEAD_AUTHORIZATION_KEY.format("U3sxzZ2LdQCCz9xD3RHWRGO28HeGw4ePrMb0Ku3RdvI")
            )
            parameter(CODE_PARAM, code)
        }
        return response.body()
    }

    private companion object {
        const val BASE_URL = "https://correios-rastreamento-de-encomendas.p.rapidapi.com"
        const val RAPIDAPI_HOST = "correios-rastreamento-de-encomendas.p.rapidapi.com"
        const val RAPIDAPI_KEY = "f3c96a234amsh976c53ea391b0b5p1ea1afjsnf22b0ead3f9e"
        const val HEADER_HOST = "x-rapidapi-host"
        const val HEADER_KEY = "x-rapidapi-key"
        const val AUTHORIZATION = "Authorization"
        const val HEAD_AUTHORIZATION_KEY = "Apikey %s"
        const val CODE_PARAM = "codigo"
    }
}