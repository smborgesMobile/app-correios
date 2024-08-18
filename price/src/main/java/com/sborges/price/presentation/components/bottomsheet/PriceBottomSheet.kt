package com.sborges.price.presentation.components.bottomsheet

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sborges.price.R
import com.sborges.price.domain.entity.PriceDomainEntity
import com.sborges.price.presentation.components.cards.PriceCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PriceResultBottomSheet(
    modifier: Modifier = Modifier,
    items: List<PriceDomainEntity>,
    onDismiss: () -> Unit = {}
) {
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )
    Column(
        modifier = modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        ModalBottomSheet(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .navigationBarsPadding(),
            sheetState = sheetState,
            onDismissRequest = { onDismiss() },
            shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
            containerColor = Color.White
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = stringResource(R.string.price_result_title),
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .fillMaxWidth(),
                    textAlign = TextAlign.Start,
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
                )

                Spacer(modifier = Modifier.height(30.dp))

                LazyColumn {
                    items(items.size) {
                        PriceCard(
                            priceEntity = items[it],
                            modifier = Modifier.padding(
                                horizontal = 16.dp,
                                vertical = 3.dp
                            )
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun PriceResultBottomSheetPreview() {
    MaterialTheme {
        PriceResultBottomSheet(
            items = listOf(
                PriceDomainEntity(
                    type = "SEDEX",
                    price = "R$ 500,00",
                    deliveryTime = "3 dias",
                    imageUrl = "https://storage.googleapis.com/sandbox-api-superfrete.appspot.com/logos/correios.png"
                ),
                PriceDomainEntity(
                    type = "PAC",
                    price = "R$ 500,00",
                    deliveryTime = "3 dias",
                    imageUrl = "https://storage.googleapis.com/sandbox-api-superfrete.appspot.com/logos/correios.png"
                ),
                PriceDomainEntity(
                    type = "MINI",
                    price = "R$ 500,00",
                    deliveryTime = "3 dias",
                    imageUrl = "https://storage.googleapis.com/sandbox-api-superfrete.appspot.com/logos/correios.png"
                )
            )
        )
    }
}