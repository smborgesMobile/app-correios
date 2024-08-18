package com.sborges.price.presentation.screens.price

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.sborges.price.R
import com.sborges.price.presentation.components.buttons.PriceButton
import com.sborges.price.presentation.components.dropdown.DropdownMenu
import com.sborges.price.presentation.components.textfield.TextFieldComponent
import com.sborges.price.presentation.components.textfield.TextType
import com.sborges.price.presentation.screens.price.utils.CreateWeightList
import org.koin.androidx.compose.koinViewModel

@Composable
fun PriceHomeScreen(
    modifier: Modifier = Modifier
) {

    val viewModel: PriceViewModel = koinViewModel()
    val state: PriceUIState by viewModel.uiState.collectAsState()

    Column(
        modifier = modifier.fillMaxSize()
    ) {
        Text(
            text = stringResource(R.string.extract_origin_title),
            modifier = Modifier
                .padding(top = 16.dp)
                .padding(horizontal = 16.dp),
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Bold
            )
        )

        TextFieldComponent(
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 16.dp),
            hint = stringResource(R.string.cep_start_label),
            type = TextType.NUMBER,
            maxFieldLength = 8,
            initialText = state.startCepValue,
            onValueChange = {
                viewModel.onEvent(PriceEvent.OnChangeStartCep(it))
            }
        )

        DropdownMenu(
            modifier = Modifier.padding(horizontal = 16.dp),
            options = CreateWeightList.weightList,
        ) {
            viewModel.onEvent(PriceEvent.OnWeightChange(it.value))
        }

        Row(
            modifier = Modifier
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(5.dp)
        ) {

            Column(
                modifier = Modifier
                    .weight(1f)
            ) {
                Text(
                    text = stringResource(R.string.package_height_label),
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(start = 4.dp)
                )
                TextFieldComponent(
                    maxFieldLength = 3,
                    initialText = state.heightValue,
                    type = TextType.NUMBER
                ) {
                    viewModel.onEvent(PriceEvent.OnHeightChange(it))
                }
            }
            Column(
                modifier = Modifier
                    .weight(1f)
            ) {
                Text(
                    text = stringResource(R.string.package_width_label),
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(start = 4.dp)
                )
                TextFieldComponent(
                    maxFieldLength = 3,
                    initialText = state.widthValue,
                    type = TextType.NUMBER
                ) {
                    viewModel.onEvent(PriceEvent.OnWidthChange(it))
                }
            }
            Column(
                modifier = Modifier
                    .weight(1f)
            ) {
                Text(
                    text = stringResource(R.string.package_deep_label),
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(start = 4.dp)
                )
                TextFieldComponent(
                    maxFieldLength = 3,
                    initialText = state.deepValue,
                    type = TextType.NUMBER
                ) {
                    viewModel.onEvent(PriceEvent.OnDeepChange(it))
                }
            }
        }

        Text(
            text = stringResource(R.string.extract_origin_target),
            modifier = Modifier
                .padding(top = 16.dp)
                .padding(horizontal = 16.dp),
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Bold
            )
        )

        TextFieldComponent(
            modifier = Modifier
                .padding(top = 8.dp)
                .padding(horizontal = 16.dp),
            hint = stringResource(R.string.cep_end_label),
            type = TextType.NUMBER,
            maxFieldLength = 8,
            initialText = state.endCepValue
        ) {
            viewModel.onEvent(PriceEvent.OnChangeEndCep(it))
        }

        val errorState = state.error
        if (errorState != null) {
            Text(
                text = stringResource(id = errorState),
                style = MaterialTheme.typography.titleSmall,
                modifier = Modifier.padding(20.dp),
                textAlign = TextAlign.Center,
                color = Color.Red
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        // The button will move above the keyboard
        PriceButton(
            isButtonEnabled = state.isButtonEnabled,
            text = stringResource(R.string.calculate_value_label),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            isLoading = state.loading
        ) {
            viewModel.onEvent(PriceEvent.OnPriceButtonClick)
        }
    }
}
