// New file for Correios RapidAPI response model
package br.com.smdevelopment.rastreamentocorreios.data.entities

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CorreiosRapidApiFullResponse(
    @SerialName("json")
    val response: CorreiosRapidApiResponse,

    @SerialName("carrier")
    val carrier: String
)

@Serializable
data class CorreiosRapidApiResponse(
    @SerialName("codObjeto")
    val codObjeto: String,
    @SerialName("tipoPostal")
    val tipoPostal: TipoPostal?,
    @SerialName("dtPrevista")
    val dtPrevista: String?,
    @SerialName("modalidade")
    val modalidade: String?,
    @SerialName("eventos")
    val eventos: List<EventoRastreio>,
    @SerialName("situacao")
    val situacao: String?,
    @SerialName("autoDeclaracao")
    val autoDeclaracao: Boolean?,
    @SerialName("encargoImportacao")
    val encargoImportacao: Boolean?,
    @SerialName("percorridaCarteiro")
    val percorridaCarteiro: Boolean?,
    @SerialName("bloqueioObjeto")
    val bloqueioObjeto: Boolean?,
    @SerialName("arEletronico")
    val arEletronico: Boolean?,
    @SerialName("arImagem")
    val arImagem: String?,
    @SerialName("locker")
    val locker: Boolean?,
    @SerialName("atrasado")
    val atrasado: Boolean?,
    @SerialName("urlFaleComOsCorreios")
    val urlFaleComOsCorreios: String?,
    @SerialName("temEventoEntrega")
    val temEventoEntrega: Boolean?
)

@Serializable
data class TipoPostal(
    @SerialName("sigla")
    val sigla: String?,
    @SerialName("descricao")
    val descricao: String?,
    @SerialName("categoria")
    val categoria: String?,
    @SerialName("tipo")
    val tipo: String?
)

@Serializable
data class EventoRastreio(
    @SerialName("codigo")
    val codigo: String?,
    @SerialName("tipo")
    val tipo: String?,
    @SerialName("dtHrCriado")
    val dtHrCriado: DataHoraCriado?,
    @SerialName("descricao")
    val descricao: String?,
    @SerialName("unidade")
    val unidade: Unidade?,
    @SerialName("unidadeDestino")
    val unidadeDestino: Unidade?,
    @SerialName("comentario")
    val comentario: String?,
    @SerialName("icone")
    val icone: String?,
    @SerialName("descricaoFrontEnd")
    val descricaoFrontEnd: String?,
    @SerialName("finalizador")
    val finalizador: String?,
    @SerialName("rota")
    val rota: String?,
    @SerialName("descricaoWeb")
    val descricaoWeb: String?,
    @SerialName("detalhe")
    val detalhe: String?,
    @SerialName("destinatario")
    val destinatario: String?,
    @SerialName("cached")
    val cached: Boolean?
)

@Serializable
data class DataHoraCriado(
    @SerialName("date")
    val date: String?,
    @SerialName("timezone_type")
    val timezoneType: Int?,
    @SerialName("timezone")
    val timezone: String?
)

@Serializable
data class Unidade(
    @SerialName("nome")
    val nome: String?,
    @SerialName("codSro")
    val codSro: String?,
    @SerialName("codMcu")
    val codMcu: String?,
    @SerialName("tipo")
    val tipo: String?,
    @SerialName("endereco")
    val endereco: Endereco?
)

@Serializable
data class Endereco(
    @SerialName("identificacao")
    val identificacao: String?,
    @SerialName("principal")
    val principal: String?,
    @SerialName("numero")
    val numero: String?,
    @SerialName("logradouro")
    val logradouro: String?,
    @SerialName("complemento")
    val complemento: String?,
    @SerialName("bairro")
    val bairro: String?,
    @SerialName("cidade")
    val cidade: String?,
    @SerialName("uf")
    val uf: String?,
    @SerialName("codigoPostal")
    val codigoPostal: String?,
    @SerialName("siglaPais")
    val siglaPais: String?
)