package br.com.smdevelopment.rastreamentocorreios.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.smdevelopment.rastreamentocorreios.R

@Composable
fun Toolbar(
    modifier: Modifier = Modifier,
    onBackButtonClick: (() -> Unit)
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.White)
    ) {
        Icon(
            painter = painterResource(
                id = R.drawable.ic_back_button
            ),
            contentDescription = null,
            modifier = Modifier
                .padding(vertical = 16.dp)
                .padding(start = 16.dp)
                .clickable { onBackButtonClick() }
        )

    }
}

@Composable
@Preview(showBackground = false)
private fun ToolbarPreview() {
    Toolbar {}
}