package com.uesurei.theiconicapp.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.accompanist.glide.rememberGlidePainter
import com.uesurei.theiconicapp.R
import com.uesurei.theiconicapp.utils.Constants

@Composable
fun NavigationTopBar(
    brand: String,
    navController : NavController
) {
    TopAppBar(
        title = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            )
            {
                Text(
                    text = brand,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    style = TextStyle(
                        fontSize = 18.sp,
                    )
                )
            }
        },
        navigationIcon = {
            IconButton(
                onClick = {
                    navController.navigate(
                        R.id.action_detailFragment_to_catalogFragment
                    )
                }
            ) {
                Image(
                    painter = rememberGlidePainter(request = Constants.ICONIC_BACK_ICON),
                    contentDescription = null
                )
            }
        },
        actions = {
            Box(
                Modifier
                    .wrapContentSize(Alignment.TopEnd)
            ) {
                IconButton(onClick = {}) {
                    Icon(
                        Icons.Filled.MoreVert,
                        contentDescription = "Localized description"
                    )
                }
            }
        },
        backgroundColor = colorResource(R.color.white),
        contentColor = colorResource(Constants.ICONIC_TEXT)
    )
}