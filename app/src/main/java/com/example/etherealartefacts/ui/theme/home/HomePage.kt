package com.example.etherealartefacts.ui.theme.home

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.material.Badge
import androidx.compose.material.BadgedBox
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.etherealartefacts.R
import com.example.etherealartefacts.ui.theme.Typography
import com.example.etherealartefacts.ui.theme.newProductLabel
import com.example.etherealartefacts.ui.theme.reusable.BottomSheet
import com.example.etherealartefacts.ui.theme.reusable.SearchBar
import com.example.etherealartefacts.ui.theme.topbar.TopBar
import com.example.etherealartefacts.ui.theme.utils.showToastMessage
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class, ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@RootNavGraph(start = true)
@Destination
@Composable
fun HomePage(destinationsNavigator: DestinationsNavigator) {

    val viewModel: HomeViewModel = hiltViewModel()
    val filterCriteria by viewModel.filterCriteria.collectAsState()
    val displayedProducts by viewModel.displayedProducts.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val isError by viewModel.isError.collectAsState()
    val errorMessage = stringResource(id = R.string.fetch_error)
    val context = LocalContext.current

    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    val filtersNumber = viewModel.filtersNumber.collectAsState()

    LaunchedEffect(isError) {
        if (isError) {
            showToastMessage(context, errorMessage)
        }
    }

    if (isLoading) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            CircularProgressIndicator()
        }
    }


    Scaffold(
        topBar = {
            val isBack = false
            TopBar(
                isBack,
                title = stringResource(id = R.string.home),
                destinationsNavigator = destinationsNavigator,
            )
        }
    ) {
        Column(
            modifier = Modifier
                .padding(
                    start = dimensionResource(id = R.dimen.row_px),
                    end = dimensionResource(id = R.dimen.row_px),
                )
                .fillMaxSize()
        ) {
            SearchBar(
                filterCriteria = filterCriteria,
                onChange = { value -> viewModel.onChange(value) },
                clear = { viewModel.clear() }
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        top = dimensionResource(id = R.dimen.row_pt),
                        bottom = dimensionResource(id = R.dimen.row_px)
                    ),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    modifier = Modifier.weight(1f),
                    text = stringResource(id = R.string.new_product),
                    style = Typography.newProductLabel,
                    textAlign = TextAlign.Start
                )

                BadgedBox(badge = {
                    Badge(
                        content = {
                            Text(
                                text = filtersNumber.value.toString(),
                                style = TextStyle(),
                                textAlign = TextAlign.Center
                            )
                        }
                    )
                }) {
                    IconButton(
                        onClick = {
                            viewModel.resetFiltersNumber()
                            scope.launch { sheetState.show() }
                        }
                    ) {
                        Icon(
                            Icons.Default.Menu,
                            contentDescription = stringResource(id = R.string.snack_btn)
                        )
                    }
                }
            }

            ProductList(displayedProducts, destinationsNavigator)

            if (sheetState.isVisible) {
                BottomSheet(sheetState = sheetState)
            }
        }
    }
}
