package com.codepunk.skeleton.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.codepunk.skeleton.R
import com.codepunk.skeleton.domain.model.Entity
import com.codepunk.skeleton.domain.model.Product
import com.codepunk.skeleton.domain.model.Resource
import com.codepunk.skeleton.ui.theme.mediumPadding

@Composable
fun ImagesSection(
    modifier: Modifier = Modifier,
    resource: Resource
) {
    if (resource.images.isNotEmpty()) {
        Text(
            text = stringResource(id = R.string.images),
            style = MaterialTheme.typography.titleLarge
        )
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .height(176.dp),
            horizontalArrangement = Arrangement.spacedBy(mediumPadding)
        ) {
            items(
                items = resource.images
            ) { image ->
                val placeholder: Painter? = if (LocalInspectionMode.current) {
                    image.uri.toIntOrNull()?.let { painterResource(id = it) }
                } else null

                AsyncImage(
                    modifier = Modifier.height(128.dp),
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(image.uri ?: "")
                        .build(),
                    placeholder = placeholder,
                    contentDescription = stringResource(
                        R.string.image_of,
                        resource.nameOrTitle
                    ),
                    contentScale = ContentScale.Inside
                )
            }
        }
    }
}

private val Resource.nameOrTitle: String
    get() = when (this) {
        is Entity -> name
        is Product -> title
    }
