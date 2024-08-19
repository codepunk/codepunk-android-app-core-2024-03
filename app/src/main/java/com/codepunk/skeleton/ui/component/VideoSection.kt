package com.codepunk.skeleton.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.codepunk.skeleton.R
import com.codepunk.skeleton.domain.model.Product

@Composable
fun VideoSection(
    modifier: Modifier = Modifier,
    product: Product
) {
    if (product.videos.isNotEmpty()) {
        Text(
            text = stringResource(id = R.string.videos),
            style = MaterialTheme.typography.titleLarge
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
            //verticalArrangement = Arrangement.spacedBy(mediumPadding)
        ) {
            product.videos.forEach { video ->
                Video(video = video)
            }
        }
    }
}
