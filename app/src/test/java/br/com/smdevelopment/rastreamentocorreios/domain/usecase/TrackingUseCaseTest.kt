package br.com.smdevelopment.rastreamentocorreios.domain.usecase

import br.com.smdevelopment.rastreamentocorreios.domain.abstraction.LinkTrackRepository
import br.com.smdevelopment.rastreamentocorreios.domain.usecase.impl.TrackingUseCaseImpl
import br.com.smdevelopment.rastreamentocorreios.presentation.components.MockData
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test

class TrackingUseCaseTest {

    private lateinit var trackingUseCase: TrackingUseCase
    private lateinit var repository: LinkTrackRepository

    @Before
    fun setup() {
        repository = mockk()
        trackingUseCase = TrackingUseCaseImpl(repository)
    }

    @Test
    fun `getTrackingInfo should return flow of tracking models`() = runBlocking {
        // Given
        val trackingCode = "AB123456789CD"
        val expectedTrackingModels = listOf(MockData.getMockTrackingModel())
        
        coEvery { repository.fetchTrackByCode(trackingCode) } returns flowOf(expectedTrackingModels)

        // When
        val result = trackingUseCase.getTrackingInfo(trackingCode).first()

        // Then
        assertNotNull(result)
        assertEquals(expectedTrackingModels, result)
    }

    @Test
    fun `deleteTracking should call repository deleteDelivery method`() = runBlocking {
        // Given
        val trackingModel = MockData.getMockTrackingModel()
        
        coEvery { repository.deleteDelivery(trackingModel) } returns Unit

        // When
        trackingUseCase.deleteTracking(trackingModel)

        // Then
        coVerify(exactly = 1) { repository.deleteDelivery(trackingModel) }
    }
} 