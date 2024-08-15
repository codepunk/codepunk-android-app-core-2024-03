package com.codepunk.skeleton.ui.component

import android.graphics.drawable.Drawable
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.codepunk.skeleton.domain.model.Image
import com.codepunk.skeleton.ui.util.previewPainter

@Composable
fun ImageWithGradient(
    modifier: Modifier = Modifier,
    contentDescription: String,
    image: Image?,
    contentScale: ContentScale,
    onSuccess: (Drawable) -> Unit = {}
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomCenter
    ) {
        AsyncImage(
            modifier = modifier
                .fillMaxSize(),
            model = ImageRequest.Builder(LocalContext.current)
                .data(image?.uri ?: "")
                .build(),
            placeholder = previewPainter(value = image?.uri),
            contentDescription = contentDescription, //stringResource(R.string.artist_image, artist.name),
            contentScale = contentScale,
            onSuccess = { onSuccess(it.result.drawable) }
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colorStops = arrayOf(
                            0f to Color.Transparent,
                            0.6f to Color.Transparent,
                            1f to Color.Black.copy(alpha = 0.8f)
                        )
                    )
                )
        )
    }
}