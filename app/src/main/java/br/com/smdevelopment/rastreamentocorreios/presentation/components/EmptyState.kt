package br.com.smdevelopment.rastreamentocorreios.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.smdevelopment.rastreamentocorreios.R

@Composable
fun EmptyState() {
    Column(
        Modifier
            .fillMaxSize()
            .padding(top = 36.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Image(
            painter = painterResource(R.drawable.empty_image),
            contentDescription = null,
            modifier = Modifier
                .size(150.dp)
                .padding(8.dp, top = 16.dp)
                .align(Alignment.CenterHorizontally)
                .clip(RoundedCornerShape(10.dp)),
            contentScale = ContentScale.Fit
        )

        Text(
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier
                .padding(horizontal = 36.dp)
                .padding(top = 16.dp)
                .align(Alignment.CenterHorizontally),
            textAlign = TextAlign.Center,
            text = stringResource(id = R.string.empty_state_message),
            fontWeight = FontWeight.Bold
        )

        Text(
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier
                .padding(vertical = 16.dp)
                .padding(horizontal = 36.dp)
                .align(Alignment.CenterHorizontally),
            textAlign = TextAlign.Center,
            text = stringResource(id = R.string.empty_state_description)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun EmptyStatePreview() {
    MaterialTheme {
        EmptyState()
    }
}