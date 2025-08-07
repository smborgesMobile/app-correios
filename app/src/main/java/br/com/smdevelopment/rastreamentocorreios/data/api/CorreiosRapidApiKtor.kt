// New file for Correios RapidAPI interface
package br.com.smdevelopment.rastreamentocorreios.data.api

import br.com.smdevelopment.rastreamentocorreios.data.entities.CorreiosRapidApiResponse

interface CorreiosRapidApiKtor {
    suspend fun fetchTrackByCode(code: String): CorreiosRapidApiResponse
}