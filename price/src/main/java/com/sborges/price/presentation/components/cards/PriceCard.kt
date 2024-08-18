package com.sborges.price.presentation.components.cards

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sborges.price.R
import com.sborges.price.domain.entity.PriceDomainEntity

@Composable
fun PriceCard(
    modifier: Modifier = Modifier,
    priceEntity: PriceDomainEntity
) {
    Card(
        modifier = modifier
            .fillMaxWidth(),
        colors = CardDefaults.cardColors().copy(
            containerColor = Color.White
        ),
        elevation = CardDefaults.elevatedCardElevation()
    ) {
        Row {
            Column {
                Image(
                    painter = painterResource(id = R.drawable.correios_icon),
                    contentDescription = null,
                    modifier = Modifier
                        .size(80.dp)
                        .padding(8.dp)
                )
            }
            Column(
                modifier = modifier.fillMaxWidth()
            ) {
                Text(
                    text = stringResource(id = R.string.shipping_type, priceEntity.type),
                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                    modifier = Modifier.padding(8.dp)
                )
                Text(
                    text = stringResource(id = R.string.shipping_value, priceEntity.price),
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                )
                Text(
                    text = stringResource(id = R.string.delivery_time, priceEntity.deliveryTime),
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                        .padding(top = 4.dp, bottom = 8.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PriceCardPreview() {
    PriceCard(
        priceEntity = PriceDomainEntity(
            type = "SEDEX",
            price = "R$ 300,00",
            deliveryTime = "6 Dias",
            imageUrl = "https://storage.googleapis.com/sandbox-api-superfrete.appspot.com/logos/correios.png"
        )
    )
}