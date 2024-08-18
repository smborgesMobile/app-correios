package com.sborges.price.presentation.screens.price.utils

import com.sborges.price.presentation.components.dropdown.DropDownModel

object CreateWeightList {

    private const val MAX_WEIGHT_KG = 19
    private const val INITIAL_WEIGHT_KG = 0.3 // 300 grams is 0.3 kg

    val weightList: List<DropDownModel> by lazy {
        val weights = mutableListOf<DropDownModel>()

        // Add the weight category "Até 300g"
        weights.add(DropDownModel("Até 300g", INITIAL_WEIGHT_KG))

        // Add weights from 1kg to MAX_WEIGHT_KG
        for (i in 1..MAX_WEIGHT_KG) {
            weights.add(DropDownModel("${i}kg", i.toDouble()))
        }

        weights
    }
}
