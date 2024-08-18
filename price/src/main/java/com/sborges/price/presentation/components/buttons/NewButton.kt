package com.sborges.price.presentation.components.buttons

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun PriceButton(
    modifier: Modifier = Modifier,
    text: String,
    isPrimary: Boolean = true,
    icon: Painter? = null,
    isButtonEnabled: Boolean = false,
    isLoading: Boolean = false,
    onClick: () -> Unit
) {
    val buttonColors = if (isPrimary) {
        ButtonDefaults.buttonColors(
            containerColor = if (isButtonEnabled) Color(0xFF1A80E5) else Color(0xFFB0C4DE)
        )
    } else {
        ButtonDefaults.buttonColors(
            containerColor = if (isButtonEnabled) Color(0xFFF0F2F5) else Color(0xFFD3D3D3)
        )
    }

    val textColor = if (isPrimary) {
        Color.White
    } else {
        Color.Black
    }

    Button(
        onClick = onClick,
        enabled = isButtonEnabled && !isLoading,
        modifier = modifier.padding(8.dp),
        colors = buttonColors,
        shape = RoundedCornerShape(8.dp)
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                color = textColor,
                strokeWidth = 2.dp,
                modifier = Modifier.size(20.dp)
            )
        } else {
            if (icon != null) {
                Icon(
                    painter = icon,
                    contentDescription = null,
                    tint = textColor,
                    modifier = Modifier.padding(end = 8.dp)
                )
            }
            Text(
                text = text,
                color = if (isButtonEnabled) textColor else Color.Gray,
                fontSize = 16.sp
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSideBySide() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        PriceButton(
            text = "Enabled",
            onClick = { },
            modifier = Modifier.weight(1f),
            isPrimary = true,
            isButtonEnabled = true
        )
        PriceButton(
            text = "Loading",
            onClick = { },
            modifier = Modifier.weight(1f),
            isPrimary = true,
            isButtonEnabled = true,
            isLoading = true
        )
    }
}