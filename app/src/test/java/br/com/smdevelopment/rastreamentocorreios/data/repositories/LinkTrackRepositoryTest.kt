package br.com.smdevelopment.rastreamentocorreios.data.repositories

import br.com.smdevelopment.rastreamentocorreios.data.api.LinkTrackApiKtor
import br.com.smdevelopment.rastreamentocorreios.data.entities.Event
import br.com.smdevelopment.rastreamentocorreios.data.entities.TrackingResponse
import br.com.smdevelopment.rastreamentocorreios.data.entities.view.TrackingModel
import br.com.smdevelopment.rastreamentocorreios.data.mappers.LinkTrackDomainMapper
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
    private val api: LinkTrackApiKtor = mockk()
    private val deliveryDao: DeliveryDao = mockk()
    private val firebaseAuth: FirebaseAuth = mockk()
    private val mapper: LinkTrackDomainMapper = mockk()

    @Before
    fun setup() {
        repository = LinkTrackRepositoryImpl(api, deliveryDao, firebaseAuth, mapper)
        every { firebaseAuth.currentUser?.uid } returns "testUserId"
    }

    @Test
    fun `Given tracking code, When fetchTrackByCode is called, Then return flow with tracking data`() = runBlocking {
        // Given
        val code = "ABC123"
        val apiResponse = TrackingResponse(
            code = code,
            host = "https://api.linketrack.com",
            events = listOf(
                Event(
                    date = "2024-03-20",
                    time = "14:30",
                    location = "São Paulo, SP",
                    status = "In Transit",
                    subStatus = listOf("Package in transit")
                )
            ),
            time = System.currentTimeMillis().toDouble(),
            quantity = 1,
            service = "SEDEX",
            last = "In Transit"
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