package br.com.smdevelopment.rastreamentocorreios.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.smdevelopment.rastreamentocorreios.R
import br.com.smdevelopment.rastreamentocorreios.data.entities.view.TrackingModel
import br.com.smdevelopment.rastreamentocorreios.presentation.components.MockData.getMockTrackingModel

@Composable
fun DeliveryCard(
    deliveryItem: TrackingModel,
    onDeleteClick: ((TrackingModel) -> Unit),
    onClick: ((TrackingModel) -> Unit)
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(top = 8.dp)
            .clickable {
                onClick(deliveryItem)
            },
        shape = MaterialTheme.shapes.medium,
        elevation = 5.dp,
        backgroundColor = MaterialTheme.colors.surface
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Image(
                painter = painterResource(id = deliveryItem.icon),
                contentDescription = null,
                modifier = Modifier
                    .size(87.dp)
                    .padding(8.dp)
                    .clip(RoundedCornerShape(10.dp)),
                contentScale = ContentScale.Fit,
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(8.dp)
            ) {
                Text(
                    text = deliveryItem.code,
                    style = MaterialTheme.typography.h6,
                    color = MaterialTheme.colors.onSurface,
                    fontSize = 16.sp,
                    maxLines = 1, // Prevents overflow with ellipsis
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = deliveryItem.events.firstOrNull()?.status
                        ?: stringResource(id = R.string.no_data_found_message),
                    style = MaterialTheme.typography.body2,
                    fontSize = 13.sp,
                    modifier = Modifier.padding(top = 4.dp),
                    fontFamily = FontFamily.SansSerif,
                    maxLines = 1, // Prevents overflow with ellipsis
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = deliveryItem.events.firstOrNull()?.date ?: deliveryItem.service,
                    style = MaterialTheme.typography.body2,
                    modifier = Modifier.padding(top = 8.dp),
                    fontSize = 13.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
            Icon(
                painter = painterResource(id = R.drawable.ic_delete_account),
                contentDescription = null,
                tint = Color.Gray,
                modifier = Modifier
                    .padding(end = 16.dp)
                    .clickable { onDeleteClick(deliveryItem) }
                    .align(Alignment.CenterVertically)
            )
        }
    }
}

@Composable
@Preview
private fun DeliveryCardPreview() {
    DeliveryCard(
        deliveryItem = getMockTrackingModel(),
        onDeleteClick = {},
        onClick = {}
    )
}
