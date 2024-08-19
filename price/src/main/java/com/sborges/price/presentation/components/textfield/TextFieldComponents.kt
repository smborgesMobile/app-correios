package com.sborges.price.presentation.components.textfield

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TextFieldComponent(
    modifier: Modifier = Modifier,
    hint: String = "",
    type: TextType = TextType.NORMAL,
    initialText: String = "",
    startIcon: ImageVector? = null,
    maxFieldLength: Int = 100,
    transformation: VisualTransformation = VisualTransformation.None,
    onValueChange: (String) -> Unit
) {
    var textState by remember { mutableStateOf(initialText) }

    TextField(
        value = textState,
        onValueChange = {
            if (it.length <= maxFieldLength) {
                textState = it
                onValueChange(it)
            }
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
        shape = RoundedCornerShape(8.dp),
        keyboardOptions = KeyboardOptions(
            keyboardType = when (type) {
                TextType.PASSWORD -> KeyboardType.Password
                TextType.NUMBER -> KeyboardType.Number
                else -> KeyboardType.Email
            }
        ),
        colors = TextFieldDefaults.colors(
            disabledTextColor = Color.Gray,
            unfocusedTextColor = Color.Gray,
            focusedContainerColor = Color(0xFFF0F2F5),
            disabledContainerColor = Color(0xFFF0F2F5),
            unfocusedContainerColor = Color(0xFFF0F2F5),
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        ),
        singleLine = true,
        leadingIcon = startIcon?.let {
            { Icon(imageVector = it, contentDescription = null) }
        },
        visualTransformation = transformation
    )
}

@Composable
@Preview
private fun TextFieldPreview() {
    Column {
        TextFieldComponent(
            hint = "Digite sua senha",
            type = TextType.EMAIL,
            startIcon = ImageVector.vectorResource(0)
        ) {}
        Spacer(modifier = Modifier.size(24.dp))
        TextFieldComponent(
            hint = "Digite sua senha",
            type = TextType.PASSWORD
        ) {}
    }
}
