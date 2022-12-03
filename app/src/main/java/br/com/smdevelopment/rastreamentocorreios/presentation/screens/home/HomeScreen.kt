package br.com.smdevelopment.rastreamentocorreios.presentation.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import br.com.smdevelopment.rastreamentocorreios.R
import br.com.smdevelopment.rastreamentocorreios.entities.retrofit.Resource
import br.com.smdevelopment.rastreamentocorreios.entities.view.DeliveryData
import br.com.smdevelopment.rastreamentocorreios.presentation.components.DeliveryTextField
import br.com.smdevelopment.rastreamentocorreios.presentation.components.PrimaryButton
import br.com.smdevelopment.rastreamentocorreios.presentation.components.SessionHeader
import coil.compose.rememberAsyncImagePainter

@Composable
fun HomeScreen() {
    val viewModel: HomeViewModel = hiltViewModel()
    val state by viewModel.state.collectAsState()

    // objects to be remembered
    var deliveryItem: DeliveryData? = null
    var loading by remember { mutableStateOf(false) }
    var hasError by remember { mutableStateOf(false) }
    var deliveryCode by remember { mutableStateOf("") }

    when (state) {
        is Resource.Success -> {
            deliveryItem = state.data
            loading = false
            hasError = false
        }
        is Resource.Error -> {
            loading = false
            hasError = true
        }
        is Resource.Loading -> {
            loading = true
            hasError = false
        }
        is Resource.Initial -> {
            loading = false
            hasError = false
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(colorResource(id = R.color.white))
            .wrapContentSize(Alignment.Center)
    ) {
        var buttonEnabled: Boolean by remember { mutableStateOf(false) }

        // text field
        DeliveryTextField(
            hasError = hasError,
            errorMessage = stringResource(id = R.string.error_message)
        ) { value, isValid ->
            deliveryCode = value
            buttonEnabled = isValid
            viewModel.resource = Resource.Initial()
        }

        // code button
        PrimaryButton(
            title = stringResource(id = R.string.home_title),
            enabled = buttonEnabled,
            loading = loading
        ) {
            viewModel.fetchDelivery(deliveryCode)
        }

        // session header
        SessionHeader(title = stringResource(id = R.string.home_my_products))

        // delivery list
        AllDeliveries(deliveryList = getDeliveryList(deliveryItem))
    }
}

private fun getDeliveryList(delivery: DeliveryData?): List<DeliveryData> {
    return if (delivery == null) {
        emptyList()
    } else {
        listOf(delivery)
    }
}

@Composable
fun AllDeliveries(deliveryList: List<DeliveryData>) {
    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(start = 16.dp, end = 16.dp, bottom = 16.dp)
    ) {
        items(deliveryList) { delivery ->
            DeliveryCard(deliveryItem = delivery)
        }
    }
}

@Composable
private fun DeliveryCard(deliveryItem: DeliveryData) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(top = 8.dp),
        shape = MaterialTheme.shapes.medium,
        elevation = 5.dp,
        backgroundColor = MaterialTheme.colors.surface
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = rememberAsyncImagePainter(deliveryItem.imageRes),
                contentDescription = null,
                modifier = Modifier
                    .size(87.dp)
                    .padding(8.dp)
                    .clip(RoundedCornerShape(10.dp)),
                contentScale = ContentScale.Fit,
            )
            Column(
                modifier = Modifier
                    .align(Alignment.Top)
                    .padding(8.dp)
            ) {
                Text(
                    text = deliveryItem.code,
                    style = MaterialTheme.typography.h6,
                    color = MaterialTheme.colors.onSurface,
                    fontSize = 16.sp
                )
                Text(
                    text = deliveryItem.destination,
                    style = MaterialTheme.typography.body2,
                    fontSize = 13.sp,
                    modifier = Modifier.padding(top = 3.dp),
                    fontFamily = FontFamily.SansSerif
                )
                Text(
                    text = deliveryItem.description,
                    style = MaterialTheme.typography.body2,
                    fontSize = 13.sp
                )
            }
        }
    }
}
