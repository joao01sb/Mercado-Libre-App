package com.joao01sb.mercadolibreapp.presentation.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.joao01sb.mercadolibreapp.R
import com.joao01sb.mercadolibreapp.util.CurrencyUtils
import com.joao01sb.mercadolibreapp.domain.model.Product
import com.joao01sb.mercadolibreapp.presentation.theme.Green

@Composable
fun ProductItem(
    product: Product,
    onClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    Card(
        modifier = modifier
            .height(320.dp)
            .clickable { onClick(product.id) },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RectangleShape
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp)
                    .clip(RoundedCornerShape(8.dp))
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(product.thumbnail)
                        .crossfade(true)
                        .build(),
                    contentDescription = product.title,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Fit,
                    placeholder = painterResource(android.R.drawable.ic_menu_gallery),
                    error = painterResource(android.R.drawable.ic_menu_report_image)
                )

                IconButton(
                    onClick = {},
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .size(28.dp)
                        .padding(2.dp)
                ) {
                    Icon(
                        Icons.Default.FavoriteBorder,
                        contentDescription = stringResource(R.string.add_to_favorites),
                        tint = Color.Black,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                Text(
                    text = product.title,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Normal,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis,
                    color = Color.Black,
                    lineHeight = 16.sp,
                    modifier = Modifier.height(32.dp)
                )

                Spacer(modifier = Modifier.height(4.dp))

                Column {
                    if (CurrencyUtils.hasDiscount(product.price, product.originalPrice)) {
                        product.originalPrice?.let { originalPrice ->
                            Text(
                                text = CurrencyUtils.formatPrice(
                                    context,
                                    originalPrice,
                                    product.currencyId
                                ),
                                fontSize = 11.sp,
                                color = Color.Gray,
                                textDecoration = TextDecoration.LineThrough
                            )
                        }
                    }

                    Text(
                        text = CurrencyUtils.formatPrice(
                            context,
                            product.price,
                            product.currencyId
                        ),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        maxLines = 1
                    )

                    if (CurrencyUtils.hasDiscount(product.price, product.originalPrice)) {
                        product.originalPrice?.let { originalPrice ->
                            val discount = CurrencyUtils.calculateDiscountPercentage(
                                product.price,
                                originalPrice
                            )
                            Text(
                                text = CurrencyUtils.formatDiscount(context, discount),
                                fontSize = 14.sp,
                                color = Green,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    val installmentValue = product.price / product.installmentsQuantity
                    Text(
                        text = CurrencyUtils.formatInstallments(
                            context,
                            product.installmentsQuantity,
                            installmentValue,
                            product.currencyId
                        ),
                        fontSize = 14.sp,
                        color = Green,
                        maxLines = 1
                    )

                    Text(
                        text = stringResource(R.string.free_shipping),
                        fontSize = 14.sp,
                        color = Green,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1
                    )
                }
            }

        }
    }
}