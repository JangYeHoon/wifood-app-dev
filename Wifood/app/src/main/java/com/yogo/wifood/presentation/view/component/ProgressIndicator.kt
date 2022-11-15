package com.yogo.wifood.presentation.view.component

import android.os.Build.VERSION.SDK_INT
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.request.ImageRequest
import coil.size.Size
import com.ehsanmsz.mszprogressindicator.progressindicator.BallPulseProgressIndicator
import com.yogo.wifood.R
import com.yogo.wifood.view.ui.theme.Gray03Color

@Composable
fun ProgressIndicator(
) {
    val context = LocalContext.current
    val imgLoader = ImageLoader.Builder(context)
        .components {
            if (SDK_INT >= 28) {
                add(ImageDecoderDecoder.Factory())
            } else {
                add(GifDecoder.Factory())
            }
        }.build()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Gray03Color.copy(0.5f))
            .zIndex(1f),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = rememberAsyncImagePainter(
                    ImageRequest.Builder(context).data(data = R.raw.loading)
                        .apply(block = {
                            size(Size.ORIGINAL)
                        }).build(),
                    imageLoader = imgLoader
                ),
                contentDescription = "Loading...",
                alpha = 1f
            )
            Spacer(modifier = Modifier.height(10.dp))
            BallPulseProgressIndicator(
                color = Color.White
            )
        }
    }

}