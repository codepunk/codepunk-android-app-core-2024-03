package com.codepunk.skeleton.ui.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.codepunk.skeleton.R
import com.codepunk.skeleton.domain.model.RelatedRelease
import com.codepunk.skeleton.ui.preview.RelatedReleasePreviewParameterProvider
import com.codepunk.skeleton.ui.theme.SkeletonTheme
import com.codepunk.skeleton.ui.theme.smallPadding
import com.codepunk.skeleton.ui.util.previewPainter

@Composable
fun RelatedReleaseView(
    modifier: Modifier = Modifier,
    relatedRelease: RelatedRelease
) {
    val density = LocalContext.current.resources.displayMetrics.density

    var imageWidthDp by remember {
        mutableStateOf(0.dp)
    }

    Box(
        modifier = modifier
            .height(intrinsicSize = IntrinsicSize.Min)
    ) {
        Column {
            AsyncImage(
                modifier = Modifier
                    .aspectRatio(1f)
                    .weight(1f)
                    .onSizeChanged { size -> imageWidthDp = (size.width / density).dp },
                model = ImageRequest.Builder(LocalContext.current)
                    .data(relatedRelease.thumb)
                    .build(),
                placeholder = previewPainter(value = relatedRelease.thumb),
                contentDescription = stringResource(R.string.artist_image, relatedRelease.title),
                contentScale = ContentScale.Fit
            )

            Spacer(modifier = Modifier.height(smallPadding))

            Box {
                Text(
                    text = "",
                    minLines = 3
                )
                Column {
                    Text(
                        modifier = Modifier.width(imageWidthDp),
                        style = MaterialTheme.typography.bodyMedium,
                        text = relatedRelease.title,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )

                    if (relatedRelease.year > 0) {
                        Text(
                            modifier = Modifier.width(imageWidthDp),
                            style = MaterialTheme.typography.bodyMedium,
                            text = relatedRelease.year.toString(),
                            maxLines = 1
                        )
                    }
                }
            }

        }
    }
}

@Preview(showSystemUi = false)
@Composable
fun RelatedReleaseViewPreviewDark(
    @PreviewParameter(
        RelatedReleasePreviewParameterProvider::class
    ) relatedRelease: RelatedRelease
) {
    SkeletonTheme(darkTheme = true) {
        Surface {
            RelatedReleaseView(
                modifier = Modifier.height(168.dp),
                relatedRelease = relatedRelease
            )
        }
    }
}
