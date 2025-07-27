package com.joao01sb.mercadolibreapp.presentation.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.joao01sb.mercadolibreapp.R
import com.joao01sb.mercadolibreapp.domain.model.Product
import com.joao01sb.mercadolibreapp.presentation.theme.Yellow
import com.joao01sb.mercadolibreapp.presentation.ui.components.ProductItem
import com.joao01sb.mercadolibreapp.presentation.ui.components.SearchResultsBar
import com.joao01sb.mercadolibreapp.presentation.ui.state.SearchResultsUiData
import com.joao01sb.mercadolibreapp.presentation.util.ProductSerializationUtil
import com.joao01sb.mercadolibreapp.presentation.util.UiState

@Composable
fun SearchResultsScreen(
    state: UiState<SearchResultsUiData>,
    query: String = "",
    onProductClick: (String, String) -> Unit,
    onBackClick: () -> Unit,
    onSearchClick: () -> Unit,
    onRetryClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {

        SearchResultsBar(
            searchQuery = query,
            onBackClick = onBackClick,
            onSearchClick = onSearchClick
        )

        when (state) {
            is UiState.Loading -> {
                LoadingContent()
            }

            is UiState.Success -> {
                if (state.data.products.isEmpty() && state.data.hasSearched) {
                    EmptyResultsContent()
                } else {
                    ProductsContent(
                        products = state.data.products,
                        onProductClick = onProductClick
                    )
                }
            }

            is UiState.Error -> {
                ErrorContent(
                    message = state.message,
                    onRetryClick = onRetryClick
                )
            }
        }
    }
}

@Composable
private fun LoadingContent() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            color = Yellow,
            modifier = Modifier.size(48.dp)
        )
    }
}

@Composable
private fun ProductsContent(
    products: List<Product>,
    onProductClick: (String, String) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.fillMaxSize()
    ) {
        items(products) { product ->
            ProductItem(
                product = product,
                onClick = {
                    val productJson = ProductSerializationUtil.serializeProduct(product)
                    onProductClick(product.id, productJson)
                }
            )
        }
    }
}

@Composable
private fun EmptyResultsContent() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .background(Color.Gray.copy(alpha = 0.1f), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = null,
                    tint = Color.Gray,
                    modifier = Modifier.size(40.dp)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = stringResource(R.string.no_results_title),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = stringResource(R.string.no_results_found),
                fontSize = 16.sp,
                color = Color.Gray,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 32.dp)
            )
        }
    }
}

@Composable
private fun ErrorContent(
    message: String,
    onRetryClick: () -> Unit
) {

    Column(
        modifier = Modifier
        .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .size(64.dp)
                .background(Color.Gray.copy(alpha = 0.2f), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                Icons.Default.Search,
                contentDescription = null,
                tint = Color.Gray
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = stringResource(R.string.search_error_title),
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = message,
            fontSize = 14.sp,
            color = Color.Gray,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(24.dp))

        TextButton(onClick = onRetryClick) {
            Text(stringResource(R.string.try_again), color = Color.Blue, fontSize = 18.sp)
        }
    }
}
