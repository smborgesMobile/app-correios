package br.com.smdevelopment.rastreamentocorreios.domain.usecase.impl

import br.com.smdevelopment.rastreamentocorreios.domain.abstraction.LinkTrackRepository
import br.com.smdevelopment.rastreamentocorreios.domain.usecase.UpdateCacheUseCase

class UpdateCacheUseCaseImpl(private val repository: LinkTrackRepository): UpdateCacheUseCase {
    override suspend fun updateCache() {
        repository.updateCache()
    }
}