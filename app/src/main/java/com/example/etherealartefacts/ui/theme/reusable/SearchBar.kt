package com.example.etherealartefacts.ui.theme.reusable

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import com.example.etherealartefacts.R
import com.example.etherealartefacts.ui.theme.ColorPalette
import com.example.etherealartefacts.ui.theme.Typography
import com.example.etherealartefacts.ui.theme.searchPlaceholder

@Composable
fun SearchBar(searchQuery: MutableState<TextFieldValue>) {
    val cornerSize = dimensionResource(id = R.dimen.search_corner)

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(cornerSize))
    ) {
        TextField(modifier = Modifier.fillMaxWidth(),
            shape = RectangleShape,
            colors = TextFieldDefaults.textFieldColors(
                textColor = ColorPalette.BW.Black,
                focusedIndicatorColor = Color.Transparent,
                focusedLabelColor = Color.Transparent,
                backgroundColor = ColorPalette.Purple.PaleLavender,
                unfocusedIndicatorColor = Color.Transparent,
                unfocusedLabelColor = Color.Transparent
            ),
            singleLine = true,
            value = searchQuery.value,
            onValueChange = { value ->
                searchQuery.value = value
            },
            placeholder = {
                Text(
                    text = stringResource(id = R.string.search_placeholder),
                    style = Typography.searchPlaceholder
                )
            },
            leadingIcon = {
                Icon(
                    Icons.Default.Search,
                    contentDescription = stringResource(id = R.string.search_placeholder),
                    modifier = Modifier
                        .padding(dimensionResource(id = R.dimen.searchIcon_px))
                        .size(dimensionResource(id = R.dimen.searchIcon_size))
                )
            },
            trailingIcon = {
                if (searchQuery.value != TextFieldValue("")) {
                    IconButton(onClick = {
                        searchQuery.value = TextFieldValue("")
                    }) {
                        Icon(
                            Icons.Default.Close,
                            contentDescription = "",
                            modifier = Modifier
                                .padding(dimensionResource(id = R.dimen.searchIcon_px))
                                .size(dimensionResource(id = R.dimen.searchIcon_size))
                        )
                    }
                }
            })
    }
}