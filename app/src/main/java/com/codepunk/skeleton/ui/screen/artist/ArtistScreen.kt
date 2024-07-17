package com.codepunk.skeleton.ui.screen.artist

import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.codepunk.skeleton.R
import com.codepunk.skeleton.domain.model.Artist
import com.codepunk.skeleton.domain.type.ImageType
import com.codepunk.skeleton.ui.MARILLION
import com.codepunk.skeleton.ui.composeables.collapsingtoolbar.ToolbarState
import com.codepunk.skeleton.ui.composeables.collapsingtoolbar.rememberToolbarState
import com.codepunk.skeleton.ui.theme.SkeletonTheme
import com.codepunk.skeleton.ui.theme.smallPadding
import com.codepunk.skeleton.ui.theme.tinyPadding

@OptIn(ExperimentalMaterial3Api::class)
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

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
    val artistName = state.artist?.name ?: stringResource(id = R.string.artist)
    val primaryImage = state.artist?.images?.firstOrNull { it.type == ImageType.PRIMARY }
    val toolbarMaxHeight = dimensionResource(id = R.dimen.collapsing_toolbar_max_height)
    val toolbarMinHeight = dimensionResource(id = R.dimen.collapsing_toolbar_min_height)

    Scaffold(
        modifier = modifier
    ) { paddingValues ->

        val toolbarStateRange = with(LocalDensity.current) {
            toolbarMinHeight.roundToPx()..toolbarMaxHeight.roundToPx()
        }
        val toolbarState = rememberToolbarState(toolbarStateRange)
        val scrollState: ScrollState = rememberScrollState()

        toolbarState.scrollValue = scrollState.value

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // The vertical artist details
            ArtistDetails(
                scrollState = scrollState,
                state = state
            )

            // The collapsing toolbar
            ArtistTitleBar(
                scrollState = scrollState,
                state = state,
                toolbarState = toolbarState
            )
        }
    }
}

@Composable
fun ArtistDetails(
    modifier: Modifier = Modifier,
    scrollState: ScrollState,
    state: ArtistScreenState
) {
    val toolbarMaxHeight = dimensionResource(id = R.dimen.collapsing_toolbar_max_height)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
    ) {
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(toolbarMaxHeight)
        )

        repeat(25) {
            Text(
                modifier = Modifier.padding(
                    horizontal = smallPadding,
                    vertical = tinyPadding
                ),
                text = state.artist?.profile ?: "",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }

}

@Composable
fun ArtistTitleBar(
    modifier: Modifier = Modifier,
    scrollState: ScrollState,
    state: ArtistScreenState,
    toolbarState: ToolbarState
) {
    val toolbarMaxHeight = dimensionResource(id = R.dimen.collapsing_toolbar_max_height)
    val artistName = state.artist?.name ?: stringResource(id = R.string.artist)
    val primaryImage = state.artist?.images?.firstOrNull { it.type == ImageType.PRIMARY }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(with(LocalDensity.current) { toolbarState.height.toDp() })
            .graphicsLayer { translationY = toolbarState.offset }
    ) {
        if (LocalInspectionMode.current) {
            // We are in preview mode
            Image(
                modifier = Modifier.fillMaxSize(),
                painter = painterResource(id = R.mipmap.img_preview_landscape),
                contentDescription = stringResource(R.string.artist_image, artistName),
                contentScale = ContentScale.Crop
            )
        } else {
            // We are in "live" mode
            AsyncImage(
                modifier = Modifier.fillMaxSize(),
                model = ImageRequest.Builder(LocalContext.current)
                    .data(primaryImage?.uri ?: "")
                    .build(),
                contentDescription = stringResource(R.string.artist_image, artistName),
                contentScale = ContentScale.Crop
            )
        }
    }
}

@Preview(showSystemUi = true)
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
