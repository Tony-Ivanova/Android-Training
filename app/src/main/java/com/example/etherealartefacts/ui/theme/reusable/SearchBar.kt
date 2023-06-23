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
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import com.example.etherealartefacts.R
import com.example.etherealartefacts.ui.theme.BW
import com.example.etherealartefacts.ui.theme.Purple
import com.example.etherealartefacts.ui.theme.Typography
import com.example.etherealartefacts.ui.theme.searchPlaceholder

@Composable
fun SearchBar(filterCriteria: String, onChange: (String) -> Unit, clear: () -> Unit) {
    val cornerSize = dimensionResource(id = R.dimen.search_corner)

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(cornerSize))
    ) {
        TextField(modifier = Modifier.fillMaxWidth(),
            shape = RectangleShape,
            colors = TextFieldDefaults.textFieldColors(
                textColor = BW.Black,
                focusedIndicatorColor = Color.Transparent,
                focusedLabelColor = Color.Transparent,
                backgroundColor = Purple.PaleLavender,
                unfocusedIndicatorColor = Color.Transparent,
                unfocusedLabelColor = Color.Transparent
            ),
            singleLine = true,
            value = filterCriteria,
            onValueChange = onChange,
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
                if (filterCriteria != "") {
                    IconButton(onClick = clear) {
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