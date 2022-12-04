package br.com.smdevelopment.rastreamentocorreios.presentation.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import br.com.smdevelopment.rastreamentocorreios.R
import br.com.smdevelopment.rastreamentocorreios.entities.retrofit.Resource
import br.com.smdevelopment.rastreamentocorreios.entities.view.DeliveryData
import br.com.smdevelopment.rastreamentocorreios.presentation.DetailActivity
import br.com.smdevelopment.rastreamentocorreios.presentation.components.DeliveryTextField
import br.com.smdevelopment.rastreamentocorreios.presentation.components.PrimaryButton
import br.com.smdevelopment.rastreamentocorreios.presentation.components.SessionHeader
import coil.compose.rememberAsyncImagePainter
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@Composable
fun HomeScreen() {
    // Create view model and init it.
    val viewModel: HomeViewModel = hiltViewModel()

    val state by viewModel.state.collectAsState()
    val deliveryState by viewModel.deliveryState.collectAsState()

    // objects to be remembered
    var loading by remember { mutableStateOf(false) }
    var hasError by remember { mutableStateOf(false) }
    var clearState by remember { mutableStateOf(false) }
    var deliveryCode by remember { mutableStateOf("") }
    var deliveryList: List<DeliveryData> by remember { mutableStateOf(emptyList()) }
    val isRefreshing = viewModel.isRefreshing.collectAsState().value
    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = isRefreshing)

    when (deliveryState) {
        is Resource.Success -> {
            deliveryList = deliveryState.data ?: emptyList()
        }
        else -> Unit
    }

    when (state) {
        is Resource.Success -> {
            loading = false
            hasError = false
            clearState = true
        }
        is Resource.Error -> {
            loading = false
            hasError = true
            clearState = false
        }
        is Resource.Loading -> {
            loading = true
            hasError = false
            clearState = false
        }
        is Resource.Initial -> {
            loading = false
            hasError = false
            clearState = false
        }
    }

    SwipeRefresh(
        state = swipeRefreshState,
        onRefresh = viewModel::refresh,
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(colorResource(id = R.color.white))
                .wrapContentSize(Alignment.Center)
        ) {
            var buttonEnabled: Boolean by remember { mutableStateOf(false) }
            if (clearState) buttonEnabled = false

            // text field
            DeliveryTextField(
                hasError = hasError,
                errorMessage = stringResource(id = R.string.error_message),
                clearState = clearState
            ) { value, isValid ->
                deliveryCode = value
                buttonEnabled = isValid
                viewModel.resource = Resource.Initial()
                clearState = false
            }

            // code button
            PrimaryButton(
                title = stringResource(id = R.string.home_title),
                enabled = buttonEnabled,
                loading = loading
            ) {
                viewModel.fetchDelivery(deliveryCode)
            }

            // session header
            SessionHeader(title = stringResource(id = R.string.home_my_products))

            // delivery list
            AllDeliveries(deliveryList = deliveryList)
        }
    }
}

@Composable
fun AllDeliveries(deliveryList: List<DeliveryData>) {
    val context = LocalContext.current
    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(start = 16.dp, end = 16.dp, bottom = 16.dp)
    ) {
        items(deliveryList) { delivery ->
            DeliveryCard(deliveryItem = delivery) {
                context.startActivity(DetailActivity.getLaunchIntent(context, delivery.eventList))
            }
        }
    }
}

@Composable
private fun DeliveryCard(deliveryItem: DeliveryData, onClick: ((DeliveryData) -> Unit)) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(top = 8.dp)
            .clickable {
                onClick(deliveryItem)
            },
        shape = MaterialTheme.shapes.medium,
        elevation = 5.dp,
        backgroundColor = MaterialTheme.colors.surface
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = rememberAsyncImagePainter(deliveryItem.imageRes),
                contentDescription = null,
                modifier = Modifier
                    .size(87.dp)
                    .padding(8.dp)
                    .clip(RoundedCornerShape(10.dp)),
                contentScale = ContentScale.Fit,
            )
            Column(
                modifier = Modifier
                    .align(Alignment.Top)
                    .padding(8.dp)
            ) {
                Text(
                    text = deliveryItem.code,
                    style = MaterialTheme.typography.h6,
                    color = MaterialTheme.colors.onSurface,
                    fontSize = 16.sp
                )
                Text(
                    text = deliveryItem.destination,
                    style = MaterialTheme.typography.body2,
                    fontSize = 13.sp,
                    modifier = Modifier.padding(top = 3.dp),
                    fontFamily = FontFamily.SansSerif
                )
                Text(
                    text = deliveryItem.description,
                    style = MaterialTheme.typography.body2,
                    fontSize = 13.sp
                )
            }
        }
    }
}
