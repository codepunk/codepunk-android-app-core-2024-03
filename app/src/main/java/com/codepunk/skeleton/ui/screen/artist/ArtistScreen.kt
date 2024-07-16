package com.codepunk.skeleton.ui.screen.artist

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.codepunk.skeleton.R
import com.codepunk.skeleton.domain.model.Artist
import com.codepunk.skeleton.domain.type.ImageType
import com.codepunk.skeleton.ui.MARILLION
import com.codepunk.skeleton.ui.theme.SkeletonTheme
import com.codepunk.skeleton.ui.theme.headerImageRatio
import com.codepunk.skeleton.ui.theme.smallPadding
import com.codepunk.skeleton.ui.theme.tinyPadding

@Composable
fun ArtistScreen(
    modifier: Modifier = Modifier,
    artistId: Long,
    state: ArtistScreenState,
    onEvent: (ArtistScreenEvent) -> Unit = {}
) {
    LaunchedEffect(Unit) {
        onEvent(ArtistScreenEvent.LoadArtist(artistId))
    }

    val scrollState = rememberScrollState()

    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState),
        ) {
            val artist = state.artist
            val artistName = artist?.name ?: "Artist"

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(ratio = headerImageRatio)
                    .graphicsLayer {
                        alpha = 1f - (scrollState.value.toFloat() / scrollState.maxValue)
                        translationY = 0.5f * scrollState.value
                    }
                    .background(Color.Cyan) // TODO
            ) {
                val primaryImage = artist?.images
                    ?.firstOrNull { it.type == ImageType.PRIMARY }
                if (primaryImage != null) {
                    AsyncImage(
                        modifier = Modifier.fillMaxSize(),
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(primaryImage.uri)
                            .build(),
                        contentDescription = stringResource(R.string.artist_image, artistName),
                        contentScale = ContentScale.Crop
                    )
                }
            }

            Text(
                modifier = Modifier.padding(smallPadding),
                text = artistName,
                style = MaterialTheme.typography.headlineLarge
            )

            Text(
                modifier = Modifier.padding(
                    horizontal = smallPadding,
                    vertical = tinyPadding
                ),
                text = artist?.profile ?: "",
                style = MaterialTheme.typography.bodyMedium
            )

            // TODO Temp for scrolling/parallax test
            Text(
                modifier = Modifier.padding(
                    horizontal = smallPadding,
                    vertical = tinyPadding
                ),
                text = artist?.profile ?: "",
                style = MaterialTheme.typography.bodyMedium
            )

            Text(
                modifier = Modifier.padding(
                    horizontal = smallPadding,
                    vertical = tinyPadding
                ),
                text = artist?.profile ?: "",
                style = MaterialTheme.typography.bodyMedium
            )
            // TODO End temp
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ArtistScreenPreviewDark() {
    SkeletonTheme(darkTheme = true) {
        ArtistScreen(
            artistId = MARILLION,
            state = ArtistScreenState(
                artistId = MARILLION,
                artist = Artist(
                    name = "Artist Name",
                    profile = "This is an artist"
                )
            )
        )
    }
}
