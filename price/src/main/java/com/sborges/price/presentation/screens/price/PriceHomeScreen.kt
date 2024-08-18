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
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sborges.price.R
import com.sborges.price.presentation.components.buttons.PriceButton
import com.sborges.price.presentation.components.dropdown.DropdownMenu
import com.sborges.price.presentation.components.textfield.TextFieldComponent
import com.sborges.price.presentation.components.textfield.TextType
import com.sborges.price.presentation.screens.price.utils.CreateWeightList

@Composable
fun PriceHomeScreen(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
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
            maxFieldLength = 8
        ) {

        }

        DropdownMenu(
            modifier = Modifier.padding(horizontal = 16.dp),
            options = CreateWeightList.weightList
        ) {

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
                    hint = "3",
                    maxFieldLength = 8
                ) {

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
                    hint = "11",
                    maxFieldLength = 8
                ) {

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
                    hint = "3",
                    maxFieldLength = 8
                ) {

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
            maxFieldLength = 8
        ) {

        }

        Spacer(modifier = Modifier.weight(1f))
        PriceButton(
            text = stringResource(R.string.calculate_value_label),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {

        }

//        viewModel.getPrices(
//            originZipCode = "13098426",
//            destinationZipCode = "37560000",
//            weight = "0.3",
//            height = "2cm",
//            width = "11cm",
//            length = "11cm"
//        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PriceHomeScreenPreview() {
    PriceHomeScreen()
}