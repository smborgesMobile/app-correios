package br.com.smdevelopment.rastreamentocorreios.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun LoginButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    isPrimary: Boolean = true // Configuração para diferenciar o estilo
) {
    val buttonColors = if (isPrimary) {
        ButtonDefaults.buttonColors(containerColor = Color(0xFF1A80E5)) // Azul primário
    } else {
        ButtonDefaults.buttonColors(containerColor = Color(0xFFF0F2F5)) // Cinza secundário
    }

    val textColor = if (isPrimary) {
        Color.White // Texto branco para o botão primário
    } else {
        Color.Black // Texto preto para o botão secundário
    }

    Button(
        onClick = onClick,
        modifier = modifier
            .padding(8.dp),
        colors = buttonColors,
        shape = RoundedCornerShape(8.dp) // Cantos arredondados
    ) {
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
        LoginButton(
            text = "Sign In",
            onClick = { /* TODO: Implement click action */ },
            modifier = Modifier.weight(1f), // Faz o botão preencher metade do espaço disponível
            isPrimary = true
        )
        LoginButton(
            text = "Sign Up",
            onClick = { /* TODO: Implement click action */ },
            modifier = Modifier.weight(1f), // Faz o botão preencher metade do espaço disponível
            isPrimary = false
        )
    }
}