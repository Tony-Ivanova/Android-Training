package com.example.etherealartefacts.ui.theme.reusable

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.dimensionResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.etherealartefacts.R
import com.example.etherealartefacts.models.request.Category
import com.example.etherealartefacts.ui.theme.home.HomeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpandableList(categories: List<Category>) {
    var expanded = remember { mutableStateOf(false) }
    val viewModel: HomeViewModel = hiltViewModel()
    val rotateState = animateFloatAsState(
        targetValue = if (expanded.value) 180F else 0F,
    )

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "Category",
            modifier = Modifier.fillMaxWidth(0.92F),
            style = MaterialTheme.typography.labelLarge
        )
        Card(onClick = { expanded.value = !expanded.value }) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = CenterVertically
            ) {
                Text(
                    text = "All categories",
                    modifier = Modifier.fillMaxWidth(0.92F),
                    style = MaterialTheme.typography.labelLarge
                )
                Icon(
                    Icons.Default.ArrowDropDown, "", modifier = Modifier.rotate(rotateState.value)
                )
            }
        }

        AnimatedVisibility(
            visible = expanded.value,
        ) {

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
            ) {
                items(categories) { category ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Checkbox(checked = category.isChecked, onCheckedChange = { isChecked ->
                            viewModel.updateCategory(category.id, isChecked)
                        })
                        Text(
                            text = category.name,
                            modifier = Modifier.padding(start = dimensionResource(id = R.dimen.isCheck_pl)),
                        )
                    }
                }
            }
        }
    }
}