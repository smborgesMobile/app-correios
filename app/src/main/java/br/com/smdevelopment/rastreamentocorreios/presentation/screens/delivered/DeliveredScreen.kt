package br.com.smdevelopment.rastreamentocorreios.presentation.screens.delivered

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import br.com.smdevelopment.rastreamentocorreios.R
import br.com.smdevelopment.rastreamentocorreios.presentation.components.AllDeliveries
import br.com.smdevelopment.rastreamentocorreios.presentation.components.EmptyState

@Composable
fun DeliveredScreen() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(colorResource(id = R.color.white))
            .wrapContentSize(Alignment.Center)
    ) {

        val viewModel: DeliveredViewModel = hiltViewModel()
        LaunchedEffect(Unit, block = {
            viewModel.getDeliveredList()
        })

        if (viewModel.deliveredList.isEmpty()) {
            EmptyState()
        } else {
            Text(
                style = MaterialTheme.typography.body1,
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.CenterHorizontally),
                textAlign = TextAlign.Start,
                text = stringResource(id = R.string.delivered_label),
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
            AllDeliveries(deliveryList = viewModel.deliveredList)
        }
    }
}