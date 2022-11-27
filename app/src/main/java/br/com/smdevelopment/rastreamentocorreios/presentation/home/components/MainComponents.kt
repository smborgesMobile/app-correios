package br.com.smdevelopment.rastreamentocorreios.presentation.home.components

import android.media.Image
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.smdevelopment.rastreamentocorreios.R
import br.com.smdevelopment.rastreamentocorreios.entities.DeliveryItem
import br.com.smdevelopment.rastreamentocorreios.utils.alphaNumericOnly

private const val MAX_FIELD_SIZE = 13

@Composable
@Preview
fun DeliveryTextField() {
    var code by remember { mutableStateOf(TextFieldValue(String())) }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, top = 16.dp)
    ) {
        OutlinedTextField(
            value = code,
            onValueChange = { newCode ->
                if (newCode.text.length <= MAX_FIELD_SIZE) {
                    code = newCode.copy(text = newCode.text.uppercase().alphaNumericOnly())
                }
            },
            modifier = Modifier.fillMaxWidth(),
            label = { Text(text = "Digite o código da sua encomenda") },
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.White,
                focusedIndicatorColor = colorResource(id = R.color.text_field_border_color),
                cursorColor = colorResource(id = R.color.text_field_border_color),
                focusedLabelColor = colorResource(id = R.color.text_field_border_color),
                textColor = colorResource(id = R.color.text_field_editable_color)
            )
        )
        CharacterCounter(newValue = code.text.length.toString())
    }
}

@Composable
fun CharacterCounter(newValue: String) {
    Text(
        style = MaterialTheme.typography.caption,
        text = "$newValue / $MAX_FIELD_SIZE",
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        textAlign = TextAlign.End
    )
}

@Composable
fun SessionHeader(title: String) {
    Text(
        style = MaterialTheme.typography.body1,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        textAlign = TextAlign.Start,
        text = title,
        fontSize = 12.sp
    )
}

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