package br.com.smdevelopment.rastreamentocorreios.presentation.home.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.smdevelopment.rastreamentocorreios.R
import br.com.smdevelopment.rastreamentocorreios.ui.theme.primary700
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
fun CustomTopAppBar() {
    TopAppBar(
        elevation = 0.dp,
        modifier = Modifier.fillMaxWidth(),
        title = {
            Box(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "Rastreamento",
                    modifier = Modifier.align(Alignment.Center),
                    color = Color.White,
                    style = TextStyle(
                        fontStyle = FontStyle.Italic,
                        fontSize = 22.sp,
                        fontFamily = FontFamily(Font((R.font.josefin_sans_semibold_italic)))
                    ),
                )
            }
        },
        backgroundColor = primary700,
    )
}

@Composable
fun ScreenHeader(title: String, modifier: Modifier) {
    Text(
        text = title,
        fontWeight = FontWeight.Bold,
        color = Color.Black,
        modifier = modifier,
        textAlign = TextAlign.Center,
        fontSize = 20.sp
    )
}

@Composable
fun PrimaryButton(title: String) {
    Button(
        onClick = {

        }, modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp)
            .height(48.dp)
    ) {
        Text(text = title)
    }
}