package br.com.smdevelopment.rastreamentocorreios.data.api

import br.com.smdevelopment.rastreamentocorreios.BuildConfig
import br.com.smdevelopment.rastreamentocorreios.BuildConfig.SERVER_URL
import br.com.smdevelopment.rastreamentocorreios.data.entities.TrackingResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter

class LinkTrackApiKtorImpl(
    private val httpClient: HttpClient
) : LinkTrackApiKtor {

    override suspend fun fetchTrackByCode(
        user: String,
        token: String,
        code: String
    ): TrackingResponse {
        val response = httpClient.get("$SERVER_URL/track/json")
        {
            parameter(USER, BuildConfig.USER)
            parameter(TOKEN, BuildConfig.TOKEN)
            parameter(CODE, code)
        }
        return response.body()
    }

    private companion object {
        const val USER = "user"
        const val TOKEN = "token"
        const val CODE = "codigo"
    }

}