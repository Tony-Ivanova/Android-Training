import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.etherealartefacts.R
import com.example.etherealartefacts.models.request.ProductWithCategory
import com.example.etherealartefacts.ui.theme.Typography
import com.example.etherealartefacts.ui.theme.home.HomeViewModel
import com.example.etherealartefacts.ui.theme.home.ProductList
import com.example.etherealartefacts.ui.theme.newProductLabel
import com.example.etherealartefacts.ui.theme.reusable.SearchBar
import com.example.etherealartefacts.ui.theme.utils.showToastMessage

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun HomePage(navController: NavHostController) {
    val viewModel: HomeViewModel = hiltViewModel()
    val response by viewModel.response.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val context = LocalContext.current

    val productState = remember { mutableStateOf<List<ProductWithCategory>?>(emptyList()) }

    val searchQuery = remember { mutableStateOf(TextFieldValue("")) }

    LaunchedEffect(Unit) {
        viewModel.getAllProducts()
    }

    if (isLoading) {
        Box(
            contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()
        ) {
            CircularProgressIndicator()
        }
    }

    response?.let { result ->
        result.onSuccess { products ->
            productState.value = products
        }
        result.onFailure {
            showToastMessage(context, stringResource(id = R.string.fetch_error))
        }
    }

    productState?.value?.let { products ->
        Scaffold(
            topBar = {
                val isBack = false;
                TopBar(
                    isBack,
                    title = stringResource(id = R.string.home),
                    navController = navController
                )
            },
        ) {
            Column(
                modifier = Modifier
                    .padding(
                        start = dimensionResource(id = R.dimen.row_px),
                        end = dimensionResource(id = R.dimen.row_px)
                    )
                    .fillMaxSize()
            ) {
                SearchBar(searchQuery)
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
                    IconButton(modifier = Modifier.align(Alignment.Top),
                        onClick = { /* Handle snack button click */ }) {
                        Icon(
                            Icons.Default.Menu,
                            contentDescription = stringResource(id = R.string.snack_btn)
                        )
                    }
                }

                ProductList(products, searchQuery, navController)
            }
        }

    }
}


