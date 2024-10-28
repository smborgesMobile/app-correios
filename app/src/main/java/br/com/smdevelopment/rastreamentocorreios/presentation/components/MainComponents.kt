package br.com.smdevelopment.rastreamentocorreios.presentation.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.smdevelopment.rastreamentocorreios.R
import br.com.smdevelopment.rastreamentocorreios.data.entities.view.TrackingModel
import br.com.smdevelopment.rastreamentocorreios.presentation.DetailActivity
import br.com.smdevelopment.rastreamentocorreios.presentation.navigation.sidemenu.NavDrawerItem
import br.com.smdevelopment.rastreamentocorreios.presentation.utils.alphaNumericOnly
import br.com.smdevelopment.rastreamentocorreios.ui.theme.disabledButton
import br.com.smdevelopment.rastreamentocorreios.ui.theme.primary700

private const val MAX_FIELD_SIZE = 13

//#region --- text delivery

@Composable
fun DeliveryTextField(
    hasError: Boolean,
    errorMessage: String,
    clearState: Boolean,
    onValueChanged: (String) -> Unit
) {
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
                    onValueChanged.invoke(code.text)
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
fun SessionHeader(title: String, fontSize: TextUnit = 12.sp) {
    Text(
        style = MaterialTheme.typography.body1,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        textAlign = TextAlign.Start,
        text = title,
        fontSize = fontSize
    )
}

@Composable
fun CustomTopAppBar(
    hasBackButton: Boolean = false,
    endMargin: Dp = 0.dp,
    onNavigationClick: (() -> Unit)? = null,
    closeActivityListener: (() -> Unit)? = null
) {
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
            {
                IconButton(
                    onClick = {
                        onNavigationClick?.invoke()
                    }) {
                    Icon(
                        imageVector = Icons.Default.Menu,
                        contentDescription = "Menu"
                    )
                }
            }
        }
    )
}

//#endregion --- session header

//#region --- primary button

@Composable
fun PrimaryButton(
    modifier: Modifier = Modifier,
    title: String,
    enabled: Boolean = true,
    loading: Boolean = false,
    icon: Int? = null,
    primaryColor: Color = primary700,
    onCodeClick: (() -> Unit?)? = null
) {
    Button(
        enabled = enabled,
        onClick = {
            onCodeClick?.invoke()
        },
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp)
            .height(48.dp)
            .background(chooseButtonBackground(enabled, primaryColor))
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            icon?.let {
                Box(
                    modifier = Modifier
                        .size(24.dp)
                        .padding(end = 8.dp)
                ) {
                    Image(
                        painter = painterResource(id = it),
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        }
        LoadingAnimation(loading = loading)

        if (!loading) {
            Text(text = title)
        }
    }
}

@Composable
fun LoadingAnimation(
    circleColor: Color = Color.White,
    circleSize: Dp = 5.dp,
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

    circles.forEachIndexed { _, animate ->
        LaunchedEffect(key1 = loading) {
            if (loading) {
                animate.animateTo(
                    targetValue = 1f,
                    animationSpec = infiniteRepeatable(tween(700), RepeatMode.Reverse)
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

private fun chooseButtonBackground(enabled: Boolean, primaryColor: Color) =
    if (enabled) {
        primaryColor
    } else {
        disabledButton
    }

//#endregion --- primary button

//#region -- delivery list

@Composable
fun AllDeliveries(
    deliveryList: List<TrackingModel>,
    onDeleteClick: ((TrackingModel) -> Unit)
) {
    val context = LocalContext.current
    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(start = 16.dp, end = 16.dp, bottom = 16.dp)
    ) {
        items(deliveryList) { delivery ->
            DeliveryCard(
                deliveryItem = delivery,
                onDeleteClick = onDeleteClick
            ) {
                context.startActivity(DetailActivity.getLaunchIntent(context, delivery.events))
            }
        }
    }
}

//#endregion

//#region --- navigation drawer

@Composable
fun DrawerHeader(modifier: Modifier) {
    Box(modifier = modifier) {
        Column {
            Text(
                text = stringResource(id = R.string.app_title),
                style = MaterialTheme.typography.h6,
                fontFamily = FontFamily(Font((R.font.josefin_sans_semibold_italic))),
                color = Color.White,
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}

@Composable
fun DrawerFooter(text: String, modifier: Modifier) {
    Text(
        text = text,
        style = MaterialTheme.typography.body1,
        color = Color.Black,
        modifier = modifier,
        textAlign = TextAlign.Center
    )
}

@Composable
fun DrawerBody(
    modifier: Modifier = Modifier,
    itemTextStyle: TextStyle = TextStyle(fontSize = 18.sp),
    onItemClick: (NavDrawerItem) -> Unit
) {
    val items = listOf(
        NavDrawerItem.About,
        NavDrawerItem.DeleteAccount,
        NavDrawerItem.SignOut
    )

    LazyColumn(modifier) {
        items(items) { item ->
            Row(modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    onItemClick(item)
                }
                .padding(16.dp)
            ) {
                Icon(
                    painter = painterResource(id = item.icon),
                    contentDescription = null
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = item.title,
                    style = itemTextStyle,
                    modifier = Modifier.weight(1f)
                )
                Icon(
                    modifier = Modifier.size(20.dp),
                    painter = painterResource(id = R.drawable.icon_arrow_right),
                    contentDescription = null
                )
            }
            Column {
                Divider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(colorResource(id = R.color.gray_color_75))
                )
            }
        }
    }
}
//#endregion --- navigation drawer

