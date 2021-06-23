package com.uesurei.theiconicapp.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.glide.rememberGlidePainter
import com.uesurei.theiconicapp.R
import com.uesurei.theiconicapp.utils.Constants

@Composable
fun DetailView(
    sku: String,
    price: Double,
    final_price: Double?,
    description: String,
    name: String,
    image: String
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Image(
            painter = rememberGlidePainter(
                request = image,
                previewPlaceholder = Constants.DEFAULT_IMAGE,
                fadeIn = true
            ),
            contentDescription = null,

            )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            Column {
                Text(
                    text = name,
                    modifier = Modifier
                        .fillMaxWidth(.75f)
                        .wrapContentWidth(Alignment.Start),
                    maxLines = 1,
                    style = TextStyle(
                        fontSize = 14.sp,
                        color = colorResource(Constants.ICONIC_TEXT)
                    )
                )
                Row(
                    modifier = Modifier.padding(top = 3.dp)
                ) {
                    if (final_price != null && price > final_price) {
                        Text(
                            text = "$$price",
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
                            text = "$$final_price",
                            modifier = Modifier
                                .wrapContentWidth(Alignment.Start),
                            style = TextStyle(
                                fontSize = 14.sp,
                                color = colorResource(Constants.ICONIC_DISCOUNT)
                            )
                        )
                    } else {
                        Text(
                            text = "$$price",
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
            Image(
                painter = painterResource(id = Constants.WISHLIST_DEFAULT),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentWidth(Alignment.End)
                    .padding(end = 6.dp)
                    .align(Alignment.CenterVertically)
            )
        }
        Button(
            onClick = { },
            modifier = Modifier
                .padding(start = 12.dp, end = 12.dp, bottom = 12.dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = colorResource(id = Constants.ICONIC_BLUE),
                contentColor = Color.White
            )
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            )
            {
                Text(
                    text = stringResource(R.string.add_to_bag),
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    style = TextStyle(
                        fontSize = 14.sp,
                    )
                )
            }
        }
        Divider(
            thickness = 1.dp,
            modifier = Modifier.padding(start = 12.dp, end = 12.dp, bottom = 12.dp)
        )

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(R.string.details_size_fit),
                modifier = Modifier.padding(start = 12.dp, end = 12.dp, bottom = 12.dp),
                style = TextStyle(
                    fontSize = 18.sp
                )
            )
        }
        Text(
            text = description.parse(),
            modifier = Modifier.padding(start = 12.dp, end = 12.dp, bottom = 12.dp),
            lineHeight = 18.sp
        )

        Text(
            text = "SKU: $sku",
            modifier = Modifier.padding(start = 12.dp, end = 12.dp, bottom = 12.dp),
            style = TextStyle(
                fontSize = 16.sp,
                color = colorResource(Constants.ICONIC_REGULAR)
            )
        )
    }
}

private fun String.parse(): AnnotatedString {
    val br = this.replace("<br>", "\n")
    val space = br.replace("&nbsp;", " ")
    val paragraph = space.replace("<p>", "")
    val paragraphClose = paragraph.replace("</p>", "")
    var parts = paragraphClose.split("<b>", "</b>")
    val bold = buildAnnotatedString {
        var bold = false
        for (part in parts) {
            if (bold) {
                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                    append(part)
                }
            } else {
                append(part)
            }
            bold = !bold
        }
    }
    parts = bold.split("<strong>", "</strong>")
    return buildAnnotatedString {
        var strong = false
        for (part in parts) {
            if (strong) {
                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                    append(part)
                }
            } else {
                append(part)
            }
            strong = !strong
        }
    }
}