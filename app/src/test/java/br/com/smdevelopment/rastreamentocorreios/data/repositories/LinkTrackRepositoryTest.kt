package br.com.smdevelopment.rastreamentocorreios.data.repositories

import br.com.smdevelopment.rastreamentocorreios.data.api.CorreiosRapidApiKtor
import br.com.smdevelopment.rastreamentocorreios.data.entities.CorreiosRapidApiResponse
import br.com.smdevelopment.rastreamentocorreios.data.entities.DataHoraCriado
import br.com.smdevelopment.rastreamentocorreios.data.entities.EventoRastreio
import br.com.smdevelopment.rastreamentocorreios.data.entities.Unidade
import br.com.smdevelopment.rastreamentocorreios.data.entities.view.TrackingModel
import br.com.smdevelopment.rastreamentocorreios.data.mappers.CorreiosRapidApiMapper
import br.com.smdevelopment.rastreamentocorreios.data.repositories.impl.LinkTrackRepositoryImpl
import br.com.smdevelopment.rastreamentocorreios.data.room.dao.DeliveryDao
import com.google.firebase.auth.FirebaseAuth
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test

class LinkTrackRepositoryTest {

    private lateinit var repository: LinkTrackRepositoryImpl
    private val api: CorreiosRapidApiKtor = mockk()
    private val deliveryDao: DeliveryDao = mockk()
    private val firebaseAuth: FirebaseAuth = mockk()
    private val mapper: CorreiosRapidApiMapper = mockk()

    @Before
    fun setup() {
        repository = LinkTrackRepositoryImpl(api, deliveryDao, firebaseAuth, mapper)
        every { firebaseAuth.currentUser?.uid } returns "testUserId"
    }

    @Test
    fun `Given tracking code, When fetchTrackByCode is called, Then return flow with tracking data`() = runBlocking {
        // Given
        val code = "ABC123"
        val apiResponse = CorreiosRapidApiResponse(
            codObjeto = code,
            tipoPostal = null,
            dtPrevista = "20/03/2025",
            modalidade = "F",
            eventos = listOf(
                EventoRastreio(
                    codigo = "BDE",
                    tipo = "01",
                    dtHrCriado = DataHoraCriado(
                        date = "2025-03-03 23:30:03.000000",
                        timezoneType = 3,
                        timezone = "America/Sao_Paulo"
                    ),
                    descricao = "Objeto entregue ao destinatário",
                    unidade = Unidade(
                        nome = "",
                        codSro = "50630977",
                        codMcu = "",
                        tipo = "Unidade de Tratamento",
                        endereco = null
                    ),
                    unidadeDestino = null,
                    comentario = "",
                    icone = "novos/caixa-visto-stroke.svg",
                    descricaoFrontEnd = "ENTREGUE",
                    finalizador = "S",
                    rota = "CONTEXTO",
                    descricaoWeb = "ENTREGUE",
                    detalhe = "Nossa entrega atendeu às suas expectativas?",
                    destinatario = null,
                    cached = true
                )
            ),
            situacao = "E",
            autoDeclaracao = false,
            encargoImportacao = false,
            percorridaCarteiro = false,
            bloqueioObjeto = false,
            arEletronico = false,
            arImagem = null,
            locker = false,
            atrasado = false,
            urlFaleComOsCorreios = "",
            temEventoEntrega = false
        )
        val mappedResponse = mockk<TrackingModel>()
        val daoResponse = listOf(mappedResponse)

        coEvery { api.fetchTrackByCode(code = code) } returns apiResponse
        every { mapper.mapToTrackModel(apiResponse) } returns mappedResponse
        every { deliveryDao.insertNewDelivery(mappedResponse) } returns Unit
        every { deliveryDao.getAllDeliveries("testUserId") } returns daoResponse

        // When
        val result = repository.fetchTrackByCode(code).first()

        // Then
        assertNotNull(result)
        assertEquals(daoResponse, result)
        coVerify { api.fetchTrackByCode(code = code) }
        coVerify { deliveryDao.insertNewDelivery(mappedResponse) }
    }

    @Test
    fun `When getAllDeliveries is called, Then return flow with cached deliveries`() = runBlocking {
        // Given
        val cachedDeliveries = listOf(mockk<TrackingModel>())
        every { deliveryDao.getAllDeliveries("testUserId") } returns cachedDeliveries

        // When
        val result = repository.getAllDeliveries().first()

        // Then
        assertEquals(cachedDeliveries, result)
        coVerify { deliveryDao.getAllDeliveries("testUserId") }
    }

    @Test
    fun `When deleteDelivery is called, Then delete from database`() = runBlocking {
        // Given
        val trackingModel = mockk<TrackingModel>()
        coEvery { deliveryDao.deleteDelivery(trackingModel) } returns Unit

        // When
        repository.deleteDelivery(trackingModel)

        // Then
        coVerify { deliveryDao.deleteDelivery(trackingModel) }
    }
}