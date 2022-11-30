package br.com.smdevelopment.rastreamentocorreios.presentation.repositories

import br.com.smdevelopment.rastreamentocorreios.presentation.api.DeliveryApi
import javax.inject.Inject

class DeliveryRepositoryImpl @Inject constructor(private val api: DeliveryApi) : DeliveryRepository {

}