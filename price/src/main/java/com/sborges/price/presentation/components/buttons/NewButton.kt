package com.sborges.price.presentation.components.buttons

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
    onClick: () -> Unit
) {
    val buttonColors = if (isPrimary) {
        ButtonDefaults.buttonColors(containerColor = Color(0xFF1A80E5)) // Primary blue
    } else {
        ButtonDefaults.buttonColors(containerColor = Color(0xFFF0F2F5)) // Secondary gray
    }

    val textColor = if (isPrimary) {
        Color.White
    } else {
        Color.Black
    }

    Button(
        onClick = onClick,
        modifier = modifier.padding(8.dp),
        colors = buttonColors,
        shape = RoundedCornerShape(8.dp)
    ) {
        if (icon != null) {
            Icon(
                painter = icon,
                contentDescription = null,
                tint = textColor,
                modifier = Modifier
                    .padding(end = 8.dp)
            )
        }
        Text(
            text = text,
            color = textColor,
            fontSize = 16.sp
        )
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
            text = "Button",
            onClick = { },
            modifier = Modifier.weight(1f),
            isPrimary = true
        )
        PriceButton(
            text = "Button One",
            onClick = { },
            modifier = Modifier.weight(1f),
            isPrimary = false
        )
    }
}