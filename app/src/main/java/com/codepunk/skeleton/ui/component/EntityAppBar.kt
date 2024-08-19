package com.codepunk.skeleton.ui.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.codepunk.skeleton.R
import com.codepunk.skeleton.domain.model.Entity
import com.codepunk.skeleton.domain.type.ImageType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntityAppBar(
    modifier: Modifier = Modifier,
    scrollBehavior: TopAppBarScrollBehavior,
    entity: Entity?,
    title: @Composable () -> Unit
) {
    val expandedHeight = dimensionResource(id = R.dimen.entity_appbar_max_height)
    val heightOffsetDp = (scrollBehavior.state.heightOffset / LocalDensity.current.density).dp

    ImageWithGradient(
        modifier = modifier
            .fillMaxWidth()
            .height(expandedHeight + heightOffsetDp),
        contentDescription = stringResource(
            id = R.string.image_of,
            entity?.name ?: stringResource(id = R.string.artist).lowercase()
        ),
        image = entity?.images?.firstOrNull { it.type == ImageType.PRIMARY },
        contentScale = ContentScale.Crop
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
