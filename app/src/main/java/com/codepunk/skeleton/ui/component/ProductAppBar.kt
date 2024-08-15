package com.codepunk.skeleton.ui.component

import android.graphics.drawable.Drawable
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.codepunk.skeleton.R
import com.codepunk.skeleton.domain.model.Product
import com.codepunk.skeleton.domain.type.ImageType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductAppBar(
    modifier: Modifier = Modifier,
    scrollBehavior: TopAppBarScrollBehavior,
    product: Product?,
    title: @Composable () -> Unit
) {
    val expandedHeight = dimensionResource(id = R.dimen.product_appbar_max_height)
    val heightOffsetDp = (scrollBehavior.state.heightOffset / LocalDensity.current.density).dp
    var primaryImage: Drawable? by remember { mutableStateOf(null) }
    val imagePainter: Painter = rememberAsyncImagePainter(model = primaryImage)

    Image(
        modifier = modifier
            .fillMaxWidth()
            .height(expandedHeight + heightOffsetDp)
            .blur(32.dp)
            .alpha(0.75f),
        painter = imagePainter,
        contentDescription = "",
        contentScale = ContentScale.Crop
    )

    ImageWithGradient(
        modifier = modifier
            .fillMaxWidth()
            .height(expandedHeight + heightOffsetDp),
        contentDescription = stringResource(
            id = R.string.image_of,
            product?.title ?: stringResource(id = R.string.album).lowercase()
        ),
        image = product?.images?.firstOrNull { it.type == ImageType.PRIMARY },
        contentScale = ContentScale.Fit,
        onSuccess = { primaryImage = it }
    )

    val color = lerp(
        start = Color.Transparent,
        stop = MaterialTheme.colorScheme.primaryContainer,
        fraction = scrollBehavior.state.collapsedFraction
    )

    LargeTopAppBar(
        modifier = modifier,
        title = title,
        expandedHeight = expandedHeight,
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = color,
            titleContentColor = MaterialTheme.colorScheme.primary,
            scrolledContainerColor = MaterialTheme.colorScheme.primaryContainer,
        ),
        scrollBehavior = scrollBehavior
    )
}