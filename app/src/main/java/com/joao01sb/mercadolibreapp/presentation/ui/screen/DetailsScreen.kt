package com.joao01sb.mercadolibreapp.presentation.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.joao01sb.mercadolibreapp.R
import com.joao01sb.mercadolibreapp.domain.model.Category
import com.joao01sb.mercadolibreapp.domain.model.Product
import com.joao01sb.mercadolibreapp.domain.model.ProductAttribute
import com.joao01sb.mercadolibreapp.domain.model.ProductDetail
import com.joao01sb.mercadolibreapp.presentation.theme.Blue
import com.joao01sb.mercadolibreapp.presentation.theme.Green
import com.joao01sb.mercadolibreapp.presentation.theme.Red
import com.joao01sb.mercadolibreapp.presentation.theme.Yellow
import com.joao01sb.mercadolibreapp.presentation.ui.components.CustomPagerIndicator
import com.joao01sb.mercadolibreapp.presentation.ui.components.SearchResultsBar
import com.joao01sb.mercadolibreapp.presentation.ui.state.ProductDetailUiData
import com.joao01sb.mercadolibreapp.presentation.util.UiState
import com.joao01sb.mercadolibreapp.util.CurrencyUtils


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailScreen(
    state: UiState<ProductDetailUiData>,
    onBackClick: () -> Unit,
    onSearchClick: () -> Unit,
    onRetryClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        SearchResultsBar(
            searchQuery = stringResource(R.string.search_mercado_libre),
            onBackClick = onBackClick,
            onSearchClick = onSearchClick
        )

        when (state) {
            is UiState.Loading -> {
                LoadingContent()
            }

            is UiState.Success -> {
                ProductDetailContent(data = state.data)
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
private fun ProductDetailContent(
    data: ProductDetailUiData
) {
    val product = data.baseProduct ?: return
    val productDetail = data.productDetail
    val description = data.description
    val category = data.category

    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        category?.let {
            item {
                CategorySection(category = it)
            }
        }

        item {
            ProductStatusSection(
                product = product,
                productDetail = productDetail
            )
        }

        item {
            ProductImagePager(
                images = productDetail?.pictures ?: listOf(product.thumbnail)
            )
        }

        item {
            ProductInfoSection(
                product = product,
                productDetail = productDetail
            )
        }

        item {
            ShippingSection(
                product = product,
                productDetail = productDetail
            )
        }

        if (product.availableQuantity <= 5) {
            item {
                StockSection(product = product)
            }
        }

        item {
            ActionButtonsSection()
        }

        item {
            if (description.isNullOrBlank()) {
                ProductBasicInfoSection(product = product)
            } else {
                DescriptionSection(description = description)
            }
        }

        item {
            if (productDetail?.attributes?.isNotEmpty() == true) {
                ProductCharacteristicsSection(
                    attributes = productDetail.attributes
                )
            } else {
                ProductFallbackSpecsSection(product = product)
            }
        }

        productDetail?.let { detail ->
            if (detail.sellerId != null) {
                item {
                    SellerInfoSection(sellerId = detail.sellerId)
                }
            }
        }
    }
}

@Composable
private fun CategorySection(category: Category) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = category.name,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
    }
}

@Composable
private fun ProductStatusSection(
    product: Product,
    productDetail: ProductDetail?
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = product.condition.uppercase(),
            fontSize = 14.sp,
            color = Color.Gray
        )

        productDetail?.let { detail ->
            if (detail.rating != null && detail.reviewsCount > 0) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = String.format("%.1f", detail.rating),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.Black
                    )
                    Text(
                        text = "(${detail.reviewsCount})",
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                }
            }
        }
    }
}

@Composable
private fun ShippingSection(
    product: Product,
    productDetail: ProductDetail?
) {
    if (product.freeShipping || productDetail?.freeShipping == true) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = null,
                        tint = Green,
                        modifier = Modifier.size(16.dp)
                    )
                    Text(
                        text = stringResource(R.string.free_shipping),
                        color = Green,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}

@Composable
private fun StockSection(product: Product) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFEBE6))
    ) {
        Text(
            text = stringResource(R.string.quantity_format, product.availableQuantity),
            modifier = Modifier.padding(16.dp),
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = Red
        )
    }
}


@Composable
private fun ProductCharacteristicsSection(
    attributes: List<ProductAttribute>
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = stringResource(R.string.product_characteristics),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(16.dp))

            attributes.take(5).forEach { attribute ->
                CharacteristicRow(
                    label = attribute.name,
                    value = attribute.valueName ?: "-"
                )
            }

            if (attributes.size > 5) {
                Spacer(modifier = Modifier.height(12.dp))

                TextButton(
                    onClick = { },
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = stringResource(R.string.view_all_characteristics),
                            color = Blue,
                            fontSize = 14.sp
                        )
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                            contentDescription = null,
                            tint = Blue,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun CharacteristicRow(
    label: String,
    value: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Icon(
            imageVector = Icons.Default.CheckCircle,
            contentDescription = null,
            tint = Green,
            modifier = Modifier.size(20.dp)
        )

        Text(
            text = "$label: $value",
            fontSize = 14.sp,
            color = Color.Black,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
private fun SellerInfoSection(sellerId: Long) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = stringResource(R.string.seller_id_format, sellerId),
                fontSize = 16.sp,
                color = Color.Gray
            )
        }
    }
}

@Composable
private fun LoadingContent() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator(
                color = Yellow,
                modifier = Modifier.size(48.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = stringResource(R.string.loading_product_details),
                color = Color.Gray
            )
        }
    }
}

@Composable
private fun ProductImagePager(images: List<String>) {
    val pagerState = rememberPagerState(pageCount = { images.size })

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f)
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize()
        ) { page ->
            AsyncImage(
                model = images[page],
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Fit
            )
        }

        Row(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            IconButton(
                onClick = { },
                modifier = Modifier
                    .size(40.dp)
                    .background(Color.White.copy(alpha = 0.8f), CircleShape)
            ) {
                Icon(
                    imageVector = Icons.Default.FavoriteBorder,
                    contentDescription = stringResource(R.string.add_to_favorites),
                    tint = Color.Black
                )
            }

            IconButton(
                onClick = { },
                modifier = Modifier
                    .size(40.dp)
                    .background(Color.White.copy(alpha = 0.8f), CircleShape)
            ) {
                Icon(
                    imageVector = Icons.Default.Share,
                    contentDescription = stringResource(R.string.share),
                    tint = Color.Black
                )
            }
        }

        if (images.size > 1) {
            CustomPagerIndicator(
                pagerState = pagerState,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(16.dp)
            )
        }
    }
}

@Composable
private fun ProductInfoSection(
    product: Product,
    productDetail: ProductDetail?
) {
    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        Text(
            text = product.title,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(16.dp))

        PriceSection(product = product, productDetail = productDetail)

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = stringResource(R.string.quantity_format, product.availableQuantity),
            fontSize = 14.sp,
            color = Color.Gray
        )
    }
}

@Composable
private fun PriceSection(
    product: Product,
    productDetail: ProductDetail? = null
) {
    val context = LocalContext.current

    Column {
        val originalPrice = productDetail?.originalPrice ?: product.originalPrice
        originalPrice?.let { original ->
            if (original > product.price) {
                Text(
                    text = CurrencyUtils.formatPrice(context, original, product.currencyId),
                    fontSize = 16.sp,
                    color = Color.Gray,
                    textDecoration = TextDecoration.LineThrough
                )
                Spacer(modifier = Modifier.height(4.dp))
            }
        }

        Text(
            text = CurrencyUtils.formatPrice(context, product.price, product.currencyId),
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )

        val installmentCount = productDetail?.installmentsQuantity ?: product.installmentsQuantity
        val installmentPrice = if (installmentCount > 1) product.price / installmentCount else product.price / 12
        val finalInstallmentCount = if (installmentCount > 1) installmentCount else 12

        Text(
            text = CurrencyUtils.formatInstallments(context, finalInstallmentCount, installmentPrice, product.currencyId),
            fontSize = 14.sp,
            color = Green
        )
    }
}

@Composable
private fun ActionButtonsSection() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Button(
            onClick = { },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Blue),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text(
                text = stringResource(R.string.buy_now),
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedButton(
            onClick = { },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.outlinedButtonColors(contentColor = Blue),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text(
                text = stringResource(R.string.add_to_cart),
                fontSize = 16.sp,
                color = Blue,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Composable
private fun DescriptionSection(description: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = stringResource(R.string.description),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = description,
                fontSize = 14.sp,
                color = Color.Gray,
                lineHeight = 20.sp
            )
        }
    }
}

@Composable
private fun ProductBasicInfoSection(product: Product) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = stringResource(R.string.product_information),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(12.dp))

            ProductInfoRow(
                label = stringResource(R.string.condition),
                value = product.condition.uppercase()
            )

            ProductInfoRow(
                label = stringResource(R.string.availability),
                value = stringResource(R.string.quantity_format, product.availableQuantity)
            )

            if (product.freeShipping) {
                ProductInfoRow(
                    label = stringResource(R.string.shipping),
                    value = stringResource(R.string.free_shipping),
                    valueColor = Green
                )
            }

            product.originalPrice?.let { originalPrice ->
                if (originalPrice > product.price) {
                    val discount = ((originalPrice - product.price) / originalPrice * 100).toInt()
                    ProductInfoRow(
                        label = stringResource(R.string.discount),
                        value = stringResource(R.string.discount_format, discount),
                        valueColor = Green
                    )
                }
            }
        }
    }
}

@Composable
private fun ProductFallbackSpecsSection(product: Product) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = stringResource(R.string.product_characteristics),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(16.dp))

            CharacteristicRow(
                label = stringResource(R.string.product_id_label),
                value = product.id
            )

            CharacteristicRow(
                label = stringResource(R.string.condition_label),
                value = product.condition.uppercase()
            )

            CharacteristicRow(
                label = stringResource(R.string.availability_label),
                value = stringResource(R.string.units_format, product.availableQuantity)
            )

            CharacteristicRow(
                label = stringResource(R.string.currency_label),
                value = product.currencyId
            )

            if (product.freeShipping) {
                CharacteristicRow(
                    label = stringResource(R.string.shipping_label),
                    value = stringResource(R.string.free_label)
                )
            }

            if (product.rating != null) {
                CharacteristicRow(
                    label = stringResource(R.string.rating_label),
                    value = stringResource(R.string.rating_format, product.rating)
                )
            }
        }
    }
}

@Composable
private fun ProductInfoRow(
    label: String,
    value: String,
    valueColor: Color = Color.Black
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            fontSize = 14.sp,
            color = Color.Gray,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = value,
            fontSize = 14.sp,
            color = valueColor,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.End
        )
    }
}

@Composable
private fun ErrorContent(
    message: String,
    onRetryClick: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(R.string.error_loading_product_details),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = message,
                fontSize = 16.sp,
                color = Color.Gray,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 32.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            TextButton(onClick = onRetryClick) {
                Text(
                    text = stringResource(R.string.try_again),
                    color = Yellow,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}