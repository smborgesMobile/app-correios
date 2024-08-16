package br.com.smdevelopment.rastreamentocorreios.presentation.components

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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.smdevelopment.rastreamentocorreios.R

@Composable
fun NewButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    isPrimary: Boolean = true,
    icon: Painter? = null
) {
    val buttonColors = if (isPrimary) {
        ButtonDefaults.buttonColors(containerColor = Color(0xFF1A80E5)) // Primary blue
    } else {
        ButtonDefaults.buttonColors(containerColor = Color(0xFFF0F2F5)) // Secondary gray
    }

    val textColor = if (isPrimary) {
        Color.White // White text for primary button
    } else {
        Color.Black // Black text for secondary button
    }

    Button(
        onClick = onClick,
        modifier = modifier.padding(8.dp),
        colors = buttonColors,
        shape = RoundedCornerShape(8.dp) // Rounded corners
    ) {
        if (icon != null) {
            Icon(
                painter = icon,
                contentDescription = null,
                tint = textColor, // Use the same color as the text
                modifier = Modifier
                    .padding(end = 8.dp) // Icon size and spacing
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
        horizontalArrangement = Arrangement.spacedBy(16.dp) // Espaço entre os botões
    ) {
        NewButton(
            text = "Sign In",
            onClick = { /* TODO: Implement click action */ },
            modifier = Modifier.weight(1f), // Faz o botão preencher metade do espaço disponível
            isPrimary = true,
            painterResource(id = R.drawable.eyes_icon)
        )
        NewButton(
            text = "Sign Up",
            onClick = { /* TODO: Implement click action */ },
            modifier = Modifier.weight(1f), // Faz o botão preencher metade do espaço disponível
            isPrimary = false,
            painterResource(id = R.drawable.eyes_icon)
        )
    }
}