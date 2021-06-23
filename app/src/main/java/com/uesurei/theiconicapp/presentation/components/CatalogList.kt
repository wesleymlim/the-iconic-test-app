package com.uesurei.theiconicapp.presentation.components

import android.os.Bundle
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyGridScope
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.google.accompanist.glide.rememberGlidePainter
import com.uesurei.theiconicapp.R
import com.uesurei.theiconicapp.network.model.Product
import com.uesurei.theiconicapp.utils.Constants
import kotlinx.coroutines.flow.Flow

@ExperimentalMaterialApi
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CatalogList(
    pager: Flow<PagingData<Product>>,
    navController: NavController
) {
    val lazyPagingItems: LazyPagingItems<Product> = pager.collectAsLazyPagingItems()

    LazyVerticalGrid(cells = GridCells.Fixed(2)) {
        items(lazyPagingItems) { product ->
            product?.let {
                CatalogCard(product = product,
                    onClick = {
                        val bundle = Bundle()
                        bundle.putString("productSKU", product.sku)
                        bundle.putString("productName", product.name)
                        bundle.putDouble("productPrice", product.price)
                        bundle.putDouble("productFinalPrice", product.final_price!!)
                        bundle.putString("productDesc", product.description)
                        bundle.putString("productImage", product.embedded!!.images[0].url)
                        bundle.putString("productBrand", product.embedded!!.brand.brand_name)
                        navController.navigate(
                            R.id.action_catalogFragment_to_detailFragment,
                            bundle
                        )
                    })
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
private fun LazyGridScope.items(
    lazyPagingItems: LazyPagingItems<Product>,
    itemContent: @Composable LazyItemScope.(value: Product?) -> Unit
) {
    items(lazyPagingItems.itemCount) { index ->
        itemContent(lazyPagingItems.getAsState(index).value)
    }
}

@ExperimentalMaterialApi
@Composable
fun CatalogCard(
    product: Product,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        shape = MaterialTheme.shapes.small,
        modifier = Modifier
            .padding(bottom = 6.dp, top = 6.dp, start = 6.dp, end = 6.dp),
        indication = rememberRipple(true),
        elevation = 3.dp
    ) {
        Column {
            Image(
                painter = rememberGlidePainter(
                    request = product.embedded!!.images[0].url,
                    previewPlaceholder = Constants.DEFAULT_IMAGE,
                    fadeIn = true
                ),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp),
                contentScale = ContentScale.Crop
            )
            product.embedded.brand.brand_name.let { brandName ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 12.dp, bottom = 12.dp, start = 8.dp, end = 8.dp)
                ) {
                    Column {
                        Text(
                            text = brandName,
                            modifier = Modifier
                                .fillMaxWidth(.75f)
                                .wrapContentWidth(Alignment.Start),
                            style = TextStyle(
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp,
                                color = colorResource(Constants.ICONIC_TEXT)
                            )
                        )
                        Text(
                            text = product.name,
                            modifier = Modifier
                                .fillMaxWidth(.75f)
                                .wrapContentWidth(Alignment.Start),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            style = TextStyle(
                                fontSize = 14.sp,
                                color = colorResource(Constants.ICONIC_TEXT)
                            )
                        )
                        Row {
                            if (product.final_price != null && product.price > product.final_price) {
                                Text(
                                    text = "$" + product.price,
                                    modifier = Modifier
                                        .wrapContentWidth(Alignment.Start)
                                        .padding(end = 3.dp),
                                    style = TextStyle(
                                        fontSize = 14.sp,
                                        textDecoration = TextDecoration.LineThrough,
                                        color = colorResource(Constants.ICONIC_REGULAR)
                                    )
                                )
                                Text(
                                    text = "$" + product.final_price.toString(),
                                    modifier = Modifier
                                        .wrapContentWidth(Alignment.Start),
                                    style = TextStyle(
                                        fontSize = 14.sp,
                                        color = colorResource(Constants.ICONIC_DISCOUNT)
                                    )
                                )
                            } else {
                                Text(
                                    text = "$" + product.price.toString(),
                                    modifier = Modifier
                                        .wrapContentWidth(Alignment.Start)
                                        .padding(end = 3.dp),
                                    style = TextStyle(
                                        fontSize = 14.sp,
                                        color = colorResource(Constants.ICONIC_REGULAR)
                                    )
                                )
                            }
                        }
                    }
                    val liked = remember { mutableStateOf(true) }
                    Image(
                        painter = painterResource(id = Constants.WISHLIST_DEFAULT),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentWidth(Alignment.End)
                            .align(Alignment.CenterVertically)
                    )
                }
            }
        }
    }
}