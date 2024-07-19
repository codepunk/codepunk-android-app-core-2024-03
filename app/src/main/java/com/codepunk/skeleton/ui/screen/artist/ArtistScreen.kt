package com.codepunk.skeleton.ui.screen.artist

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.integerResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.codepunk.skeleton.R
import com.codepunk.skeleton.domain.model.Artist
import com.codepunk.skeleton.domain.type.ImageType
import com.codepunk.skeleton.ui.theme.SkeletonTheme
import com.codepunk.skeleton.ui.theme.largePadding
import com.codepunk.skeleton.ui.theme.mediumPadding
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
    ) { innerPaddingggg ->
        val collapsedLines = integerResource(id = R.integer.artist_profile_collapsed_lines)
        var expanded by remember {mutableStateOf(false)}
        var textWidth by remember { mutableStateOf(0) }
        val textMeasurer = rememberTextMeasurer()
        var expandable by remember { mutableStateOf(false) }

        val profile = state.artist?.profile.orEmpty()

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPaddingggg)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        horizontal = largePadding,
                        vertical = mediumPadding
                    )
                    .verticalScroll(scrollState),
                verticalArrangement = Arrangement.spacedBy(smallPadding)
            ) {
                Text(
                    modifier = Modifier
                        .animateContentSize()
                        .onGloballyPositioned { text ->
                            textWidth = text.size.width
                            val expandedLines = textMeasurer.measure(
                                text = profile,
                                constraints = Constraints(maxWidth = textWidth)
                            ).lineCount
                            expandable = (expandedLines > collapsedLines)
                        },
                    text = profile,
                    maxLines = if (expanded) Int.MAX_VALUE else collapsedLines
                )
                if (expandable) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        TextButton(
                            onClick = { expanded = !expanded }
                        ) {
                            Text(text = if (expanded) "Show less" else "Show more")
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArtistAppBar(
    modifier: Modifier = Modifier,
    state: ArtistScreenState,
    scrollBehavior: TopAppBarScrollBehavior
) {
    val expandedHeight = dimensionResource(id = R.dimen.collapsing_toolbar_max_height)
    val heightOffsetDp = (scrollBehavior.state.heightOffset / LocalDensity.current.density).dp

    PreviewableAsyncImage(
        modifier = modifier
            .fillMaxWidth()
            .height(expandedHeight + heightOffsetDp),
        scrollBehavior = scrollBehavior,
        artist = state.artist
    )

    val color = lerp(
        start = Color.Transparent,
        stop = MaterialTheme.colorScheme.primaryContainer,
        fraction = scrollBehavior.state.collapsedFraction
    )

    LargeTopAppBar(
        modifier = modifier,
        title = {
            Text(text = state.artist?.name.orEmpty())
        },
        expandedHeight = expandedHeight,
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = color,
            titleContentColor = MaterialTheme.colorScheme.primary,
            scrolledContainerColor = MaterialTheme.colorScheme.primaryContainer,
        ),
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
    Box(
        modifier = modifier.fillMaxWidth(),
        contentAlignment = Alignment.BottomCenter
    ) {
        val artistName = artist?.name.orEmpty()
        val primaryImage = artist?.images?.firstOrNull { it.type == ImageType.PRIMARY }
        if (LocalInspectionMode.current) {
            // We are in preview mode
            Image(
                modifier = modifier
                    .fillMaxWidth(),
                painter = painterResource(id = R.mipmap.img_preview_landscape),
                contentDescription = stringResource(R.string.artist_image, artistName),
                contentScale = ContentScale.Crop
            )
        } else {
            // We are in "live" mode
            AsyncImage(
                modifier = modifier
                    .fillMaxWidth()
                    .nestedScroll(scrollBehavior.nestedScrollConnection),
                model = ImageRequest.Builder(LocalContext.current)
                    .data(primaryImage?.uri ?: "")
                    .build(),
                contentDescription = stringResource(R.string.artist_image, artistName),
                contentScale = ContentScale.Crop
            )
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(144.dp)
                .background(
                    Brush.verticalGradient(
                        listOf(Color.Transparent, Color.Black.copy(alpha = 0.75f))
                    )
                )
        )
    }
}


@Preview(
    showSystemUi = true
)
@Composable
fun ArtistScreenPreviewDark(
    @PreviewParameter(LoremIpsum::class) artistProfile: String
) {
    SkeletonTheme(darkTheme = true) {
        ArtistScreen(
            artistId = 1,
            state = ArtistScreenState(
                artistId = 1,
                artist = Artist(
                    name = "Lorem Ipsum",
                    profile = artistProfile
                )
            )
        )
    }
}
