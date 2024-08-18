package com.sborges.price.presentation.screens.price.utils

object CreateWeightList {

    private const val MAX_WEIGHT_KG = 19
    private const val INITIAL_WEIGHT_GRAMS = "Até 300g"

    val weightList: List<String> by lazy {
        val weights = mutableListOf<String>()

        // Add the weight category "Até 300g"
        weights.add(INITIAL_WEIGHT_GRAMS)

        // Add weights from 1kg to MAX_WEIGHT_KG
        for (i in 1..MAX_WEIGHT_KG) {
            weights.add("${i}kg")
        }

        weights
    }
}
