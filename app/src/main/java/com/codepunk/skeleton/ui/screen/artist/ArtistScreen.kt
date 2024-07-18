package com.codepunk.skeleton.ui.screen.artist

import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.codepunk.skeleton.R
import com.codepunk.skeleton.core.loginator.Loginator
import com.codepunk.skeleton.domain.model.Artist
import com.codepunk.skeleton.domain.type.ImageType
import com.codepunk.skeleton.ui.theme.SkeletonTheme
import com.codepunk.skeleton.ui.theme.smallPadding

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

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val scrollState: ScrollState = rememberScrollState()

    Scaffold(
        modifier = Modifier
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            ArtistAppBar(
                state = state,
                scrollBehavior = scrollBehavior
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(scrollState),
            verticalArrangement = Arrangement.spacedBy(smallPadding)
        ) {
            repeat(10) {
                Text(text = state.artist?.profile ?: "This is an artist.")
            }
        }
    }

    /*
    As per https://medium.com/kotlin-and-kotlin-for-android/collapsing-toolbar-in-jetpack-compose-problem-solutions-and-alternatives-34c9c5986ea0
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
     */
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArtistAppBar(
    state: ArtistScreenState,
    scrollBehavior: TopAppBarScrollBehavior
) {
    PreviewableAsyncImage(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp),
        scrollBehavior = scrollBehavior,
        artist = state.artist
    )
    LargeTopAppBar(
        title = {
            Text(text = state.artist?.name ?: "[Unknown Artist]")
        },
        /*
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.primary,
        ),
         */
        scrollBehavior = scrollBehavior
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Suppress("SpellCheckingInspection")
@Composable
fun PreviewableAsyncImage(
    modifier: Modifier = Modifier,
    scrollBehavior: TopAppBarScrollBehavior,
    artist: Artist?
) {
    val msg = buildString {
        // return "${javaClass.simpleName}(httpStatus=$httpStatus, message=$message)"
        append(scrollBehavior.state.javaClass.simpleName)
        append("(")
        append("heightOffset=")
        append(scrollBehavior.state.heightOffset)
        append(", contentOffset=")
        append(scrollBehavior.state.contentOffset)
        append(", collapsedFraction=")
        append(scrollBehavior.state.collapsedFraction)
        append(", heightOffsetLimit=")
        append(scrollBehavior.state.heightOffsetLimit)
        append(", overlappedFraction=")
        append(scrollBehavior.state.overlappedFraction)
        append(")")
    }
    Loginator.d { msg }
    val artistName = artist?.name ?: "Artist Name"
    val primaryImage = artist?.images?.firstOrNull { it.type == ImageType.PRIMARY }
    if (LocalInspectionMode.current) {
        // We are in preview mode
        Image(
            modifier = modifier
                .fillMaxWidth()
                .graphicsLayer {
                    translationY = scrollBehavior.state.heightOffset
                },
            painter = painterResource(id = R.mipmap.img_preview_landscape),
            contentDescription = stringResource(R.string.artist_image, artistName),
            contentScale = ContentScale.Crop
        )
    } else {
        // We are in "live" mode
        AsyncImage(
            modifier = modifier
                .fillMaxWidth()
                .graphicsLayer {
                    translationY = scrollBehavior.state.heightOffset
                }.nestedScroll(scrollBehavior.nestedScrollConnection),
            model = ImageRequest.Builder(LocalContext.current)
                .data(primaryImage?.uri ?: "")
                .build(),
            contentDescription = stringResource(R.string.artist_image, artistName),
            contentScale = ContentScale.Crop
        )
    }
}



/*
    As per https://medium.com/kotlin-and-kotlin-for-android/collapsing-toolbar-in-jetpack-compose-problem-solutions-and-alternatives-34c9c5986ea0

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
 */


@Preview(showSystemUi = true)
@Composable
fun ArtistScreenPreviewDark() {
    SkeletonTheme(darkTheme = true) {
        ArtistScreen(
            artistId = 1,
            state = ArtistScreenState(
                artistId = 1,
                artist = Artist(
                    name = "Artist Name",
                    profile = "This is an artist"
                )
            )
        )
    }
}
