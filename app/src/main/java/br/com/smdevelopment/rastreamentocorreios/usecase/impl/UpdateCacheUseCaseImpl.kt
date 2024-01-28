package br.com.smdevelopment.rastreamentocorreios.usecase.impl

import br.com.smdevelopment.rastreamentocorreios.repositories.LinkTrackRepository

class UpdateCacheUseCaseImpl(private val repository: LinkTrackRepository): UpdateCacheUseCase {
    override suspend fun updateCache() {
        repository.updateCache()
    }
}