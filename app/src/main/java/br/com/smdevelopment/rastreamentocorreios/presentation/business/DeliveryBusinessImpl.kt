package br.com.smdevelopment.rastreamentocorreios.presentation.business

import br.com.smdevelopment.rastreamentocorreios.presentation.repositories.DeliveryRepository
import javax.inject.Inject

class DeliveryBusinessImpl @Inject constructor(private val deliveryRepository: DeliveryRepository) : DeliveryBusiness {

}