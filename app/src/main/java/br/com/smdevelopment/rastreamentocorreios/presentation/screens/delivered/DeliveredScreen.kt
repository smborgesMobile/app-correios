package br.com.smdevelopment.rastreamentocorreios.presentation.screens.delivered

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import br.com.smdevelopment.rastreamentocorreios.R
import br.com.smdevelopment.rastreamentocorreios.presentation.components.EmptyState

@Composable
fun DeliveredScreen() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(colorResource(id = R.color.white))
            .wrapContentSize(Alignment.Center)
    ) {

        LaunchedEffect(Unit, block = {

        })
        EmptyState()
    }
}