package br.com.smdevelopment.rastreamentocorreios.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.smdevelopment.rastreamentocorreios.R

@Composable
fun TextFieldComponent(
    modifier: Modifier = Modifier,
    hint: String = "",
    isPasswordField: Boolean = false,
    onValueChange: (String) -> Unit
) {
    var textState by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    TextField(
        value = textState,
        onValueChange = {
            textState = it
            onValueChange(it)
        },
        modifier = modifier
            .fillMaxWidth()
            .background(Color(0xFFF0F2F5), shape = RoundedCornerShape(8.dp)),
        textStyle = TextStyle(color = Color.Black, fontSize = 16.sp),
        placeholder = {
            Text(
                text = hint,
                color = Color(0xFF637587)
            )
        },

        colors = TextFieldDefaults.textFieldColors(
            textColor = Color.Gray,
            disabledTextColor = Color.Transparent,
            backgroundColor = Color(0xFFF0F2F5),
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        ),
        shape = RoundedCornerShape(8.dp),
        singleLine = true,
        visualTransformation = if (isPasswordField && !passwordVisible)
            PasswordVisualTransformation()
        else VisualTransformation.None,
        trailingIcon = {
            if (isPasswordField) {
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(
                        painter = painterResource(id = R.drawable.eyes_icon),
                        contentDescription = if (passwordVisible) "Hide password" else "Show password",
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        }
    )
}

@Composable
@Preview
private fun TextFieldPreview() {
    Column {
        TextFieldComponent(hint = "Digite sua senha", isPasswordField = true) {}
        Spacer(modifier = Modifier.size(24.dp))
        TextFieldComponent(hint = "Digite sua senha", isPasswordField = false) {}
    }
}
