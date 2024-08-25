package br.com.smdevelopment.rastreamentocorreios.presentation.screens.home

import android.Manifest
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import br.com.smdevelopment.rastreamentocorreios.R
import br.com.smdevelopment.rastreamentocorreios.presentation.components.AllDeliveries
import br.com.smdevelopment.rastreamentocorreios.presentation.components.DeliveryTextField
import br.com.smdevelopment.rastreamentocorreios.presentation.components.PrimaryButton
import br.com.smdevelopment.rastreamentocorreios.presentation.components.SessionHeader
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeScreen() {
    // Create view model and init it.
    val linkTrackViewModel: LinkTrackViewModel = koinViewModel()

    // Observe the UI state
    val uiState by linkTrackViewModel.uiState.collectAsState()

    // objects to be remembered
    val pullRefreshState = rememberPullRefreshState(uiState.isRefreshing, {
        linkTrackViewModel.onEvent(LinkTrackEvent.FetchAllLinkTrackItems)
    })

    SideEffect {
        linkTrackViewModel.onEvent(LinkTrackEvent.InAppReviewCheck)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .pullRefresh(pullRefreshState)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(colorResource(id = R.color.white))
                .wrapContentSize(Alignment.Center)
        ) {

            // Check for permission
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
                FeatureThatRequiresNotificationPermission()

            // text field
            DeliveryTextField(
                hasError = uiState.errorState,
                errorMessage = stringResource(id = R.string.error_message),
                clearState = uiState.deliveryCode.isEmpty()
            ) { value ->
                linkTrackViewModel.onEvent(LinkTrackEvent.CodeChanged(value))
            }

            // code button
            PrimaryButton(
                title = stringResource(id = R.string.home_title),
                enabled = uiState.buttonEnabled,
                loading = uiState.loadingState
            ) {
                linkTrackViewModel.onEvent(LinkTrackEvent.FindForCode(uiState.deliveryCode))
            }

            // session header
            SessionHeader(
                title = stringResource(id = R.string.home_my_products),
                fontSize = 16.sp
            )

            // delivery list
            AllDeliveries(deliveryList = uiState.trackingInfo) {
                linkTrackViewModel.onEvent(LinkTrackEvent.DeleteItem(it))
            }
        }

        // Pull to refresh indicator
        PullRefreshIndicator(
            refreshing = uiState.isRefreshing,
            state = pullRefreshState,
            modifier = Modifier
                .align(Alignment.TopCenter)
        )
    }
}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@OptIn(ExperimentalPermissionsApi::class)
@Composable
private fun FeatureThatRequiresNotificationPermission() {
    var showDialog by remember { mutableStateOf(true) }

    // Notification permission state
    val notificationPermission = rememberPermissionState(
        Manifest.permission.POST_NOTIFICATIONS
    )

    if (!notificationPermission.status.isGranted) {
        Column {
            CustomAlertDialog(showDialog = showDialog, onDismiss = {
                showDialog = false
            }, onExit = {
                showDialog = false
                notificationPermission.launchPermissionRequest()
            })
        }
    }
}

@Composable
fun CustomAlertDialog(
    showDialog: Boolean, onDismiss: () -> Unit,
    onExit: () -> Unit,
    titleRes: Int = R.string.permission_title,
    descriptionRes: Int = R.string.permission_description
) {
    if (showDialog) {
        Dialog(
            onDismissRequest = { onDismiss() }, properties = DialogProperties(
                dismissOnBackPress = false, dismissOnClickOutside = false
            )
        ) {
            Card(
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                elevation = 8.dp
            ) {
                Column(
                    Modifier
                        .fillMaxWidth()
                        .background(Color.White)
                ) {
                    Text(
                        text = stringResource(id = titleRes),
                        modifier = Modifier.padding(16.dp),
                        fontSize = 20.sp
                    )

                    Text(
                        text = stringResource(id = descriptionRes),
                        modifier = Modifier.padding(16.dp)
                    )

                    Row(Modifier.padding(top = 10.dp)) {
                        OutlinedButton(
                            onClick = { onDismiss() },
                            Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                                .weight(1F)
                        ) {
                            Text(text = stringResource(id = R.string.permission_leave_button))
                        }

                        Button(
                            onClick = { onExit() },
                            Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                                .weight(1F)
                        ) {
                            Text(text = stringResource(id = R.string.permission_enable_button))
                        }
                    }
                }
            }
        }
    }
}
