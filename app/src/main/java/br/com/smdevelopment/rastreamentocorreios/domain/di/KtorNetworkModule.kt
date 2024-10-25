package br.com.smdevelopment.rastreamentocorreios.domain.di

import android.util.Log
import br.com.smdevelopment.rastreamentocorreios.data.api.LinkTrackApiKtor
import br.com.smdevelopment.rastreamentocorreios.data.api.LinkTrackApiKtorImpl
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.observer.ResponseObserver
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.dsl.module

private const val TIME_OUT = 6000

val networkModule = module {
    // ktor
    factory<LinkTrackApiKtor> { LinkTrackApiKtorImpl(get()) }

    single {
        HttpClient(Android) {
            expectSuccess = true

            install(ContentNegotiation) {
                json(
                    Json {
                        prettyPrint = true
                        isLenient = true
                        ignoreUnknownKeys = true
                        explicitNulls = false
                    })
            }

            // Engine configuration
            engine {
                connectTimeout = TIME_OUT
                socketTimeout = TIME_OUT
            }

            // Logging
            install(Logging) {
                logger = object : Logger {
                    override fun log(message: String) {
                        Log.d("HttpLogging:", message)
                    }
                }
            }

            // HTTP Response
            install(ResponseObserver) {
                onResponse { response ->
                    Log.d("HTTP status:", "${response.status.value}")
                }
            }

            // Headers
            install(DefaultRequest) {
                header(HttpHeaders.ContentType, ContentType.Application.Json)
            }
        }
    }
}