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
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import br.com.smdevelopment.rastreamentocorreios.R
import br.com.smdevelopment.rastreamentocorreios.entities.DeliveryItem
import br.com.smdevelopment.rastreamentocorreios.presentation.components.DeliveryTextField
import br.com.smdevelopment.rastreamentocorreios.presentation.components.PrimaryButton
import br.com.smdevelopment.rastreamentocorreios.presentation.components.SessionHeader

@Composable
fun HomeScreen() {
    val viewModel: HomeViewModel = hiltViewModel()
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(colorResource(id = R.color.white))
            .wrapContentSize(Alignment.Center)
    ) {
        var code by remember { mutableStateOf("") }

        // text field
        DeliveryTextField {
            code = it
        }

        // code button
        PrimaryButton(title = stringResource(id = R.string.home_title)) {
            viewModel.fetchDelivery(code)
        }

        // session header
        SessionHeader(title = stringResource(id = R.string.home_my_products))

        // delivery list
        AllDeliveries(deliveryList = getList())
    }
}

private fun getList() = listOf(
    DeliveryItem(
        code = " SL123456789BR",
        imageRes = R.drawable.delivery_icon,
        description = "Objeto em transito para Jaguariuna"
    ),
    DeliveryItem(
        code = " SL123456789BR",
        imageRes = R.drawable.delivery_icon,
        description = "Objeto em transito para Campinas"
    ),
    DeliveryItem(
        code = " SL123456789BR",
        imageRes = R.drawable.delivery_icon,
        description = "Objeto em transito para Casa"
    ),
    DeliveryItem(
        code = " SL123456780BR",
        imageRes = R.drawable.delivery_icon,
        description = "Objeto em transito para Casa"
    ),
    DeliveryItem(
        code = " SL123456779BR",
        imageRes = R.drawable.delivery_icon,
        description = "Objeto em transito para Casa"
    )
)

@Composable
fun AllDeliveries(deliveryList: List<DeliveryItem>) {
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
private fun DeliveryCard(deliveryItem: DeliveryItem) {
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
                painter = painterResource(id = deliveryItem.imageRes),
                contentDescription = null,
                modifier = Modifier
                    .size(100.dp)
                    .padding(8.dp),
                contentScale = ContentScale.Fit
            )
            Column {
                Text(
                    text = deliveryItem.code,
                    style = MaterialTheme.typography.h6,
                    color = MaterialTheme.colors.onSurface
                )
                Text(
                    text = deliveryItem.description,
                    style = MaterialTheme.typography.body2
                )
            }
        }
    }
}
