package br.com.smdevelopment.rastreamentocorreios.domain.usecase.impl

import br.com.smdevelopment.rastreamentocorreios.domain.usecase.GetPriceFeatureToggleUseCase
import com.sborges.core.remoteconfig.abstractions.FeatureToggle

class GetPriceFeatureToggleUseCaseImpl(
    private val featureToggle: FeatureToggle
) : GetPriceFeatureToggleUseCase {

    override fun isPriceToggleEnabled(): Boolean =
        featureToggle.isFeatureEnabled(PRICE_FEATURE_TOGGLE)

    private companion object {
        const val PRICE_FEATURE_TOGGLE = "ft_price_tab"
    }
}