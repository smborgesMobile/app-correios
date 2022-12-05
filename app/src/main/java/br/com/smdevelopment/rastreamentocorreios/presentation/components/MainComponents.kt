package br.com.smdevelopment.rastreamentocorreios.presentation.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.smdevelopment.rastreamentocorreios.R
import br.com.smdevelopment.rastreamentocorreios.ui.theme.disabledButton
import br.com.smdevelopment.rastreamentocorreios.ui.theme.primary700
import br.com.smdevelopment.rastreamentocorreios.utils.alphaNumericOnly
import kotlinx.coroutines.delay

private const val MAX_FIELD_SIZE = 13

//#region --- text delivery

@Composable
fun DeliveryTextField(hasError: Boolean, errorMessage: String, clearState: Boolean, onValueChanged: (String, Boolean) -> Unit) {
    var code by remember { mutableStateOf(TextFieldValue(String())) }
    if (clearState) {
        code = TextFieldValue(String())
    }
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
                    onValueChanged.invoke(code.text, code.text.length == MAX_FIELD_SIZE)
                }
            },
            modifier = Modifier.fillMaxWidth(),
            label = { Text(text = stringResource(id = R.string.input_code_label)) },
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

    if (hasError && code.text.length == MAX_FIELD_SIZE) {
        Text(
            text = errorMessage,
            textAlign = TextAlign.Start,
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = 16.dp,
                    end = 16.dp,
                    bottom = 8.dp
                ),
            color = Color.Red,
            style = MaterialTheme.typography.caption,
        )
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

//#endregion --- text delivery

//#region --- session header

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
@Preview
fun CustomTopAppBar(hasBackButton: Boolean = false, endMargin: Dp = 0.dp, closeActivityListener: (() -> Unit)? = null) {
    TopAppBar(
        elevation = 0.dp,
        modifier = Modifier.fillMaxWidth(),
        title = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = endMargin)
            ) {
                Text(
                    text = stringResource(id = R.string.app_title),
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
        navigationIcon = if (hasBackButton) {
            {
                IconButton(
                    onClick = {
                        closeActivityListener?.invoke()
                    }) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Back"
                    )
                }
            }
        } else {
            null
        }
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

//#endregion --- session header

//#region --- primary button

@Composable
fun PrimaryButton(
    title: String,
    enabled: Boolean = true,
    loading: Boolean = false,
    onCodeClick: (() -> Unit?)? = null
) {
    Button(
        enabled = enabled,
        onClick = {
            onCodeClick?.invoke()
        }, modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp)
            .height(48.dp)
            .background(chooseButtonBackground(enabled))
    ) {
        Box(contentAlignment = Alignment.Center) {
            LoadingAnimation(loading = loading)
        }

        if (!loading) {
            Text(text = title)
        }
    }
}

@Composable
fun LoadingAnimation(
    circleColor: Color = Color.White,
    circleSize: Dp = 5.dp,
    animationDelay: Int = 400,
    initialAlpha: Float = 0.3f,
    loading: Boolean
) {

    val circles = listOf(
        remember {
            Animatable(initialValue = initialAlpha)
        },
        remember {
            Animatable(initialValue = initialAlpha)
        },
        remember {
            Animatable(initialValue = initialAlpha)
        }
    )

    circles.forEachIndexed { index, animate ->
        LaunchedEffect(key1 = loading) {
            if (loading) {
                delay(timeMillis = (animationDelay / circles.size).toLong() * index)
                animate.animateTo(
                    targetValue = 1f,
                    animationSpec = infiniteRepeatable(
                        animation = tween(
                            durationMillis = animationDelay
                        ),
                        repeatMode = RepeatMode.Reverse
                    )
                )
            }
        }
    }

    Row(modifier = Modifier) {
        if (loading) {
            circles.forEachIndexed { index, animate ->
                if (index != 0) {
                    Spacer(modifier = Modifier.width(width = 6.dp))
                }

                Box(
                    modifier = Modifier
                        .size(size = circleSize)
                        .clip(shape = CircleShape)
                        .background(
                            color = circleColor
                                .copy(alpha = animate.value)
                        )
                ) {
                }
            }
        }
    }
}

private fun chooseButtonBackground(enabled: Boolean) = if (enabled) {
    primary700
} else {
    disabledButton
}

//#endregion --- primary button

