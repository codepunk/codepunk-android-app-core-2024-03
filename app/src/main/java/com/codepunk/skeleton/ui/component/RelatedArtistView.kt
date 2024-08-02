package com.codepunk.skeleton.ui.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.codepunk.skeleton.R
import com.codepunk.skeleton.domain.model.RelatedArtist
import com.codepunk.skeleton.ui.preview.RelatedArtistPreviewParameterProvider
import com.codepunk.skeleton.ui.theme.SkeletonTheme
import com.codepunk.skeleton.ui.theme.smallPadding
import com.codepunk.skeleton.ui.util.previewPainter

@Composable
fun RelatedArtistView(
    thumbnailSize: Dp,
    relatedArtist: RelatedArtist
) {
    Box {
        Column {
            val artistPlaceholderPainter = painterResource(id = R.drawable.placeholder_artist)
            val placeholder = previewPainter(value = relatedArtist.thumbnailUrl) {
                artistPlaceholderPainter
            }

            AsyncImage(
                modifier = Modifier
                    .width(thumbnailSize)
                    .aspectRatio(1f)
                    .clip(CircleShape),
                model = ImageRequest.Builder(LocalContext.current)
                    .data(relatedArtist.thumbnailUrl)
                    .build(),
                placeholder = placeholder,
                error = artistPlaceholderPainter,
                contentDescription = stringResource(R.string.artist_image, relatedArtist.name),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(smallPadding))

            Text(
                modifier = Modifier.width(thumbnailSize),
                minLines = 2,
                maxLines = 2,
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
                text = relatedArtist.name,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Preview(showSystemUi = false)
@Composable
fun RelatedArtistViewPreviewDark(
    @PreviewParameter(
        RelatedArtistPreviewParameterProvider::class
    ) relatedArtist: RelatedArtist
) {
    SkeletonTheme(darkTheme = true) {
        Surface {
            RelatedArtistView(
                thumbnailSize = 128.dp,
                relatedArtist = relatedArtist
            )
        }
    }
}
