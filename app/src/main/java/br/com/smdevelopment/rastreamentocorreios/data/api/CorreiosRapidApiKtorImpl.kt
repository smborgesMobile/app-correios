package br.com.smdevelopment.rastreamentocorreios.data.api

import br.com.smdevelopment.rastreamentocorreios.data.entities.CorreiosRapidApiFullResponse
import br.com.smdevelopment.rastreamentocorreios.data.entities.CorreiosRapidApiResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.serialization.json.Json

class CorreiosRapidApiKtorImpl(
    private val httpClient: HttpClient
) : CorreiosRapidApiKtor {

    override suspend fun fetchTrackByCode(code: String): CorreiosRapidApiResponse {
        val rawResponse = httpClient.post(BASE_URL) {
            header(
                AUTHORIZATION,
                HEAD_AUTHORIZATION_KEY.format("U3sxzZ2LdQCCz9xD3RHWRGO28HeGw4ePrMb0Ku3RdvI")
            )
            contentType(ContentType.Application.Json)
            setBody("""{"code": "$code"}""")
        }
        val jsonFormat = Json {
            ignoreUnknownKeys = true
        }
        val fullResponse = rawResponse.body<CorreiosRapidApiFullResponse>()
        val decodedJson =
            jsonFormat.decodeFromString<CorreiosRapidApiResponse>(fullResponse.response)
        return decodedJson
    }


    private companion object {
        const val BASE_URL = "https://api-labs.wonca.com.br/wonca.labs.v1.LabsService/Track"
        const val AUTHORIZATION = "Authorization"
        const val HEAD_AUTHORIZATION_KEY = "Apikey %s"
    }
}