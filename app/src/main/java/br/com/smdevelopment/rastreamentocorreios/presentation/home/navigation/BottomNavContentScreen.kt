package br.com.smdevelopment.rastreamentocorreios.presentation.home.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.smdevelopment.rastreamentocorreios.R
import br.com.smdevelopment.rastreamentocorreios.entities.DeliveryItem
import br.com.smdevelopment.rastreamentocorreios.presentation.home.components.AllDeliveries
import br.com.smdevelopment.rastreamentocorreios.presentation.home.components.DeliveryTextField
import br.com.smdevelopment.rastreamentocorreios.presentation.home.components.SessionHeader

@Composable
fun CarScreen() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(colorResource(id = R.color.white))
            .wrapContentSize(Alignment.Center)
    ) {
        ScreenHeader(title = "Car Screen", modifier = Modifier.align(Alignment.CenterHorizontally))
    }
}

@Composable
fun HomeScreen() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(colorResource(id = R.color.white))
            .wrapContentSize(Alignment.Center)
    ) {
        DeliveryTextField()
        PrimaryButton(title = "Rastrear")
        SessionHeader(title = "Minhas encomendas:")
        AllDeliveries(deliveryList = getList())
    }
}

private fun getList() = listOf(
    DeliveryItem(
        code = "0SJJDJDJDBHH000BR",
        title = "TV",
        imageRes = R.drawable.delivery_icon,
        description = "Objeto em transito para Jaguariuna"
    ),
    DeliveryItem(
        code = "0SJJDJDJDBHH000SR",
        title = "Microondas",
        imageRes = R.drawable.delivery_icon,
        description = "Objeto em transito para Campinas"
    ),
    DeliveryItem(
        code = "0SJJDJDJDBHH000BR",
        title = "TV",
        imageRes = R.drawable.delivery_icon,
        description = "Objeto em transito para Casa"
    )
)

@Composable
fun StarScreen() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(colorResource(id = R.color.white))
            .wrapContentSize(Alignment.Center)
    ) {
        ScreenHeader(title = "Star Screen", modifier = Modifier.align(Alignment.CenterHorizontally))
    }
}

@Composable
fun ScreenHeader(title: String, modifier: Modifier) {
    Text(
        text = title,
        fontWeight = FontWeight.Bold,
        color = Color.Black,
        modifier = modifier,
        textAlign = TextAlign.Center,
        fontSize = 20.sp
    )
}

@Composable
fun PrimaryButton(title: String) {
    Button(
        onClick = {

        }, modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp)
            .height(48.dp)
    ) {
        Text(text = title)
    }
}