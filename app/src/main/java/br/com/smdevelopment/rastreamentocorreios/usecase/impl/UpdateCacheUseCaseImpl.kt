package br.com.smdevelopment.rastreamentocorreios.usecase.impl

import br.com.smdevelopment.rastreamentocorreios.repositories.LinkTrackRepository
import br.com.smdevelopment.rastreamentocorreios.usecase.UpdateCacheUseCase

class UpdateCacheUseCaseImpl(private val repository: LinkTrackRepository): UpdateCacheUseCase {
    override suspend fun updateCache() {
        repository.updateCache()
    }
}