package com.sborges.price.presentation.screens.price

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import org.koin.androidx.compose.koinViewModel

@Composable
fun PriceHomeScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {

        val viewModel: PriceViewModel = koinViewModel()

        viewModel.getPrices(
            originZipCode = "13098426",
            destinationZipCode = "37560000",
            weight = "0.3",
            height = "2cm",
            width = "11cm",
            length = "11cm"
        )
    }
}