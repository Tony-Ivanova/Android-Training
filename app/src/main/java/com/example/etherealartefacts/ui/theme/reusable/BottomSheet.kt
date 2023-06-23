package com.example.etherealartefacts.ui.theme.reusable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.IconButton
import androidx.compose.material.RangeSlider
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.sharp.Star
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.etherealartefacts.R
import com.example.etherealartefacts.ui.theme.BW
import com.example.etherealartefacts.ui.theme.Purple
import com.example.etherealartefacts.ui.theme.home.HomeViewModel
import com.ramcosta.composedestinations.annotation.Destination
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class, ExperimentalMaterial3Api::class)
@Destination
@Composable
fun BottomSheet(sheetState: SheetState) {
    val scope = rememberCoroutineScope()
    val viewModel: HomeViewModel = hiltViewModel()
    val minPrice = viewModel.minPrice.collectAsState()
    val maxPrice = viewModel.maxPrice.collectAsState()
    val selectedStars = viewModel.selectedStars.collectAsState()
    val updatedRange = viewModel.updatedRange.collectAsState()
    val categories = viewModel.categories.collectAsState()

    ModalBottomSheet(
        modifier = Modifier.fillMaxHeight(),
        onDismissRequest = { scope.launch { sheetState.hide() } },
        sheetState = sheetState as SheetState
    ) {
        Column(
            modifier = Modifier.padding(
                start = dimensionResource(id = R.dimen.row_px), end = dimensionResource(
                    id = R.dimen.row_px
                )
            )
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()
            ) {
                IconButton(onClick = { scope.launch { sheetState.hide() } }) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = stringResource(id = R.string.close)
                    )
                }

                Text(
                    text = stringResource(id = R.string.filters), modifier = Modifier.weight(1f)
                )

                TextButton(
                    onClick = {
                        scope.launch {
                            viewModel.onFiltersApplied(
                                selectedStars.value,
                                minPrice.value.toInt(),
                                maxPrice.value.toInt(),
                                categories.value
                            )
                            sheetState.hide()
                        }
                    },
                    modifier = Modifier.weight(1f),
                ) {
                    Text(stringResource(id = R.string.save))
                }
            }

            Row() {
                ExpandableList(categories.value)
            }

            Column() {
                RangeSlider(value = updatedRange.value,
                    onValueChange = { viewModel.onRangeChanged(it) },
                    onValueChangeFinished = {
                        viewModel.onRangeChanged(updatedRange.value)
                    },
                    valueRange = 0f..100f
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("$${updatedRange.value.start.toInt()}")
                    Text("$${updatedRange.value.endInclusive.toInt()}")
                }
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = stringResource(id = R.string.product_rating))

                for (i in 1..5) {
                    val isSelected = i <= selectedStars.value
                    IconButton(onClick = {
                        viewModel.onUpdateStarsNumber(i)
                    }) {
                        Icon(
                            imageVector = Icons.Sharp.Star,
                            contentDescription = stringResource(id = R.string.star),
                            tint = if (isSelected) Purple.DarkIndigo else BW.DisabledGray
                        )
                    }
                }
            }


        }


    }
}