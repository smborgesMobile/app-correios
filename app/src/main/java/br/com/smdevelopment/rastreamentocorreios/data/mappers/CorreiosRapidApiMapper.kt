// Mapper for RapidAPI response
package br.com.smdevelopment.rastreamentocorreios.data.mappers

import br.com.smdevelopment.rastreamentocorreios.R
import br.com.smdevelopment.rastreamentocorreios.data.entities.CorreiosRapidApiResponse
import br.com.smdevelopment.rastreamentocorreios.data.entities.DataHoraCriado
import br.com.smdevelopment.rastreamentocorreios.data.entities.view.EventModel
import br.com.smdevelopment.rastreamentocorreios.data.entities.view.TrackingModel
import com.google.firebase.auth.FirebaseAuth
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class CorreiosRapidApiMapper(
    private val firebaseAuth: FirebaseAuth
) {
    fun mapToTrackModel(response: CorreiosRapidApiResponse): TrackingModel {
        return TrackingModel(
            userId = firebaseAuth.currentUser?.uid.orEmpty(),
            code = response.codObjeto,
            host = HOST_VALUE,
            last = response.eventos.firstOrNull()?.descricao.orEmpty(),
            quantity = response.eventos.size,
            service = response.tipoPostal?.descricao.orEmpty(),
            time = 0.0, // No equivalent field in RapidAPI response, using default
            events = response.eventos.mapIndexed { index, evento ->
                EventModel(
                    date = formatEventDate(evento.dtHrCriado),
                    time = extractTimeFromDateTime(evento.dtHrCriado),
                    location = buildLocationString(evento.unidade),
                    status = evento.descricao ?: FAILED_TO_LOCATE,
                    subStatus = listOfNotNull(evento.detalhe),
                    icon = when {
                        evento.descricao == OBJECT_DONE -> R.drawable.delivered_icon
                        index == 0 -> R.drawable.package_delivery
                        else -> R.drawable.delivered_start_icon
                    },
                    isDelivered = evento.descricao == OBJECT_DONE || evento.finalizador == "S"
                )
            },
            icon = when {
                response.eventos.any { it.descricao == OBJECT_DONE || it.finalizador == "S" } -> R.drawable.delivered_icon
                else -> R.drawable.delivered_start_icon
            },
            isDelivered = response.eventos.any { it.descricao == OBJECT_DONE || it.finalizador == "S" }
        )
    }

    private fun buildLocationString(unidade: br.com.smdevelopment.rastreamentocorreios.data.entities.Unidade?): String {
        return if (unidade != null) {
            val cidade = unidade.endereco?.cidade ?: ""
            val uf = unidade.endereco?.uf ?: ""
            val tipo = unidade.tipo ?: ""
            
            if (cidade.isNotEmpty() && uf.isNotEmpty()) {
                "$cidade/$uf - $tipo"
            } else {
                tipo
            }
        } else {
            ""
        }
    }

    private fun formatEventDate(dtHrCriado: DataHoraCriado?): String {
        return dtHrCriado?.date?.let {
            try {
                val dateTime = parseDateTimeString(it)
                val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm")
                dateTime.format(formatter)
            } catch (e: Exception) {
                it
            }
        } ?: ""
    }
    
    private fun extractTimeFromDateTime(dtHrCriado: DataHoraCriado?): String {
        return dtHrCriado?.date?.let {
            try {
                val dateTime = parseDateTimeString(it)
                val formatter = DateTimeFormatter.ofPattern("HH:mm")
                dateTime.format(formatter)
            } catch (e: Exception) {
                ""
            }
        } ?: ""
    }
    
    private fun parseDateTimeString(dateString: String): LocalDateTime {
        // Expected format: "2025-03-03 23:30:03.000000"
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS")
        return LocalDateTime.parse(dateString, formatter)
    }

    private companion object {
        const val OBJECT_DONE = "Objeto entregue ao destinatário"
        const val FAILED_TO_LOCATE = "Objeto em trânsito"
        const val HOST_VALUE = "rapidapi.com"
    }
}