package com.codepunk.skeleton.ui.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
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
    Box(
        modifier = modifier
    ) {
        Column {
            AsyncImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f),
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
                        modifier = Modifier.fillMaxWidth(),
                        style = MaterialTheme.typography.bodyMedium,
                        text = relatedRelease.title,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )

                    if (relatedRelease.year > 0) {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
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
                modifier = Modifier.width(72.dp),
                relatedRelease = relatedRelease
            )
        }
    }
}
