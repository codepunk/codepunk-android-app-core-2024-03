package com.codepunk.skeleton.ui.screen.artist

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.graphics.painter.Painter
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
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.dp
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.codepunk.skeleton.R
import com.codepunk.skeleton.domain.model.Artist
import com.codepunk.skeleton.domain.orEmpty
import com.codepunk.skeleton.domain.type.ImageType
import com.codepunk.skeleton.ui.component.LinkChip
import com.codepunk.skeleton.ui.component.RelatedArtistView
import com.codepunk.skeleton.ui.component.RelatedReleaseView
import com.codepunk.skeleton.ui.preview.ArtistPreviewParameterProvider
import com.codepunk.skeleton.ui.theme.SkeletonTheme
import com.codepunk.skeleton.ui.theme.largePadding
import com.codepunk.skeleton.ui.theme.mediumPadding
import com.codepunk.skeleton.ui.theme.smallPadding
import com.codepunk.skeleton.ui.util.previewPainter
import com.codepunk.skeleton.util.url.UrlInfo
import com.codepunk.skeleton.util.url.UrlInfo.Domain
import kotlinx.coroutines.flow.flow

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
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
    val columnScrollState: ScrollState = rememberScrollState()

    Scaffold(
        modifier = modifier
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            ArtistAppBar(
                state = state,
                scrollBehavior = scrollBehavior
            )
        }
    ) { innerPadding ->
        val collapsedLines = integerResource(id = R.integer.artist_profile_collapsed_lines)
        var expanded by remember { mutableStateOf(false) }
        var textWidth by remember { mutableIntStateOf(0) }
        val textMeasurer = rememberTextMeasurer()

        val artist = state.artist.orEmpty()
        val releases = remember(state.releases) {
            flow {
                emit(state.releases)
            }
        }.collectAsLazyPagingItems()

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        horizontal = largePadding,
                        vertical = mediumPadding
                    )
                    .verticalScroll(columnScrollState),
                verticalArrangement = Arrangement.spacedBy(smallPadding)
            ) {

                // Profile

                var expandable by remember { mutableStateOf(false) }

                Text(
                    modifier = Modifier
                        .animateContentSize()
                        .onGloballyPositioned { text ->
                            textWidth = text.size.width
                            val expandedLines = textMeasurer.measure(
                                text = artist.profile,
                                constraints = Constraints(maxWidth = textWidth)
                            ).lineCount
                            expandable = (expandedLines > collapsedLines)
                        },
                    text = artist.profile,
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
                            Text(
                                text = if (expanded) {
                                    stringResource(id = R.string.show_less)
                                } else {
                                    stringResource(id = R.string.show_more)
                                }
                            )
                        }
                    }
                }

                // Urls

                if (artist.urls.isNotEmpty()) {
                    Text(
                        text = stringResource(id = R.string.links),
                        style = MaterialTheme.typography.titleLarge
                    )

                    FlowRow(
                        horizontalArrangement = Arrangement.spacedBy(smallPadding)
                    ) {
                        val urlInfos = artist.urls.map { UrlInfo(it) }
                        val countMap = urlInfos
                            .map { it.domain }
                            .filter { it != Domain.OTHER }
                            .groupingBy { it }
                            .eachCount()
                        urlInfos.forEach { urlInfo ->
                            val count = countMap.getOrElse(urlInfo.domain) { 1 }
                            LinkChip(
                                urlInfo = urlInfo,
                                count = count
                            )
                        }
                    }
                }

                // Images

                if (artist.images.isNotEmpty()) {
                    HorizontalDivider()
                    Text(
                        text = stringResource(id = R.string.images),
                        style = MaterialTheme.typography.titleLarge
                    )
                    LazyRow(
                        modifier = Modifier.height(96.dp),
                        horizontalArrangement = Arrangement.spacedBy(mediumPadding)
                    ) {
                        items(
                            items = artist.images
                        ) { image ->
                            val placeholder: Painter? = if (LocalInspectionMode.current) {
                                image.uri.toIntOrNull()?.let { painterResource(id = it) }
                            } else null

                            AsyncImage(
                                modifier = modifier
                                    .fillMaxSize(),
                                model = ImageRequest.Builder(LocalContext.current)
                                    .data(image.uri ?: "")
                                    .build(),
                                placeholder = placeholder,
                                contentDescription = stringResource(
                                    R.string.artist_image,
                                    artist.name
                                ),
                                contentScale = ContentScale.Inside
                            )
                        }
                    }
                }

                // Releases

                val releasesLazyListState: LazyListState = rememberLazyListState()

                if (releases.itemCount > 0) {
                    HorizontalDivider()
                    Text(
                        text = stringResource(id = R.string.releases),
                        style = MaterialTheme.typography.titleLarge
                    )
                    LazyRow(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(mediumPadding),
                        state = releasesLazyListState
                    ) {
                        items(
                            count = releases.itemCount,
                            key = releases.itemKey { it.releaseId }
                        ) { index ->
                            releases[index]?.also { release ->
                                RelatedReleaseView(
                                    modifier = Modifier.width(96.dp),
                                    relatedRelease = release
                                )
                            }
                        }
                        if (!releases.loadState.isIdle) {
                            item {
                                Box(
                                    modifier = Modifier
                                        .fillMaxHeight()
                                        .width(48.dp)
                                ) {
                                    CircularProgressIndicator(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .aspectRatio(1f)
                                            .padding(mediumPadding)
                                            .align(Alignment.Center)
                                    )
                                }
                            }
                        }
                    }
                }

                // Related artists - Aliases

                val aliasesLazyListState = rememberLazyListState()

                if (artist.aliases.isNotEmpty()) {
                    HorizontalDivider()
                    Text(
                        text = stringResource(id = R.string.aliases),
                        style = MaterialTheme.typography.titleLarge
                    )
                    LazyRow(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(mediumPadding),
                        state = aliasesLazyListState
                    ) {
                        items(
                            items = artist.aliases,
                            key = { it.artistId }
                        ) {
                            RelatedArtistView(
                                modifier = Modifier.width(96.dp),
                                relatedArtist = it
                            )
                        }
                    }
                }


                // Related artists - Members

                val membersLazyListState = rememberLazyListState()

                if (artist.members.isNotEmpty()) {
                    HorizontalDivider()
                    Text(
                        text = stringResource(id = R.string.members),
                        style = MaterialTheme.typography.titleLarge
                    )
                    LazyRow(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(mediumPadding),
                        state = membersLazyListState
                    ) {
                        items(
                            items = artist.members,
                            key = { it.artistId }
                        ) {
                            RelatedArtistView(
                                modifier = Modifier.width(96.dp),
                                relatedArtist = it
                            )
                        }
                    }
                }

                // Related artists - Groups

                val groupsLazyListState = rememberLazyListState()

                if (artist.groups.isNotEmpty()) {
                    HorizontalDivider()
                    Text(
                        text = stringResource(id = R.string.groups),
                        style = MaterialTheme.typography.titleLarge
                    )
                    LazyRow(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(mediumPadding),
                        state = groupsLazyListState
                    ) {
                        items(
                            items = artist.groups,
                            key = { it.artistId }
                        ) {
                            RelatedArtistView(
                                modifier = Modifier.width(96.dp),
                                relatedArtist = it
                            )
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

    ImageWithGradient(
        modifier = modifier
            .fillMaxWidth()
            .height(expandedHeight + heightOffsetDp),
        state = state
    )

    val color = lerp(
        start = Color.Transparent,
        stop = MaterialTheme.colorScheme.primaryContainer,
        fraction = scrollBehavior.state.collapsedFraction
    )

    LargeTopAppBar(
        modifier = modifier,
        title = {
            Column {
                Text(
                    text = state.artist?.name.orEmpty(),
                    style = MaterialTheme.typography.headlineMedium,
                )
                if (state.artist?.realName.orEmpty().isNotEmpty()) {
                    Text(
                        text = state.artist?.realName.orEmpty(),
                        style = MaterialTheme.typography.titleSmall,
                    )
                }
            }
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

@Composable
fun ImageWithGradient(
    modifier: Modifier = Modifier,
    state: ArtistScreenState
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomCenter
    ) {
        val artist = state.artist.orEmpty()
        val primaryImage = artist.images.firstOrNull { it.type == ImageType.PRIMARY }

        AsyncImage(
            modifier = modifier
                .fillMaxSize(),
            model = ImageRequest.Builder(LocalContext.current)
                .data(primaryImage?.uri ?: "")
                .build(),
            placeholder = previewPainter(value = primaryImage?.uri),
            contentDescription = stringResource(R.string.artist_image, artist.name),
            contentScale = ContentScale.Crop
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colorStops = arrayOf(
                            0f to Color.Transparent,
                            0.6f to Color.Transparent,
                            1f to Color.Black.copy(alpha = 0.8f)
                        )
                    )
                )
        )
    }
}

@Preview(showSystemUi = true)
@Composable
fun ArtistScreenPreviewDark(
    @PreviewParameter(ArtistPreviewParameterProvider::class) artist: Artist
) {
    SkeletonTheme(darkTheme = true) {
        ArtistScreen(
            artistId = 1,
            state = ArtistScreenState(
                artistId = 1,
                artist = artist
            )
        )
    }
}
