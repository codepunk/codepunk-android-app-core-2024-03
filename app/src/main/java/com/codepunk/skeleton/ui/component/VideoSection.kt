package com.codepunk.skeleton.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.codepunk.skeleton.domain.model.Product
import com.codepunk.skeleton.ui.theme.mediumPadding

@Composable
fun VideoSection(
    modifier: Modifier = Modifier,
    product: Product
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = mediumPadding),
        verticalArrangement = Arrangement.spacedBy(mediumPadding)
    ) {
        product.videos.forEach { video ->
            Video(video = video)
        }
    }
}
