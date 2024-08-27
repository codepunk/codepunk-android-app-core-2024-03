package com.codepunk.skeleton.ui.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.codepunk.skeleton.R
import com.codepunk.skeleton.domain.model.RelatedRelease
import com.codepunk.skeleton.ui.preview.RelatedReleasePreviewParameterProvider
import com.codepunk.skeleton.ui.theme.SkeletonTheme
import com.codepunk.skeleton.ui.theme.smallPadding
import com.codepunk.skeleton.util.previewPainter

@Composable
fun RelatedRelease(
    thumbnailSize: Dp,
    relatedRelease: RelatedRelease
) {
    val textMeasurer = rememberTextMeasurer()
    var textWidth by remember { mutableIntStateOf(0) }
    var titleLineCount by remember { mutableIntStateOf(0) }

    Box(modifier = Modifier.fillMaxHeight()) {
        Column {
            AsyncImage(
                modifier = Modifier
                    .width(thumbnailSize)
                    .aspectRatio(1f),
                model = ImageRequest.Builder(LocalContext.current)
                    .data(relatedRelease.thumb)
                    .build(),
                placeholder = previewPainter(value = relatedRelease.thumb),
                contentDescription = stringResource(R.string.image_of, relatedRelease.title),
                contentScale = ContentScale.Fit
            )

            Spacer(modifier = Modifier.height(smallPadding))

            Box {
                Column {
                    Text(
                        modifier = Modifier
                            .width(thumbnailSize)
                            .onGloballyPositioned { layoutCoordinates ->
                                textWidth = layoutCoordinates.size.width
                                titleLineCount = textMeasurer.measure(
                                    text = relatedRelease.title,
                                    constraints = Constraints(maxWidth = textWidth)
                                ).lineCount.coerceIn(1, 2)
                            },
                        style = MaterialTheme.typography.bodyMedium,
                        text = relatedRelease.title,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )

                    if (relatedRelease.year > 0) {
                        val yearLineCount = 3 - titleLineCount
                        Text(
                            modifier = Modifier.width(thumbnailSize),
                            style = MaterialTheme.typography.bodyMedium,
                            text = relatedRelease.year.toString(),
                            minLines = yearLineCount,
                            maxLines = yearLineCount
                        )
                    }
                }
            }

        }
    }
}

@Preview(showSystemUi = false)
@Composable
fun RelatedReleasePreviewDark(
    @PreviewParameter(
        RelatedReleasePreviewParameterProvider::class
    ) relatedRelease: RelatedRelease
) {
    SkeletonTheme(darkTheme = true) {
        Surface(
            modifier = Modifier.height(200.dp)
        ) {
            RelatedRelease(
                thumbnailSize = 128.dp,
                relatedRelease = relatedRelease
            )
        }
    }
}
