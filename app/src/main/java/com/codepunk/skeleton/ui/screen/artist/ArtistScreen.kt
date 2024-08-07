package com.codepunk.skeleton.ui.screen.artist

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.codepunk.skeleton.R
import com.codepunk.skeleton.domain.model.Artist
import com.codepunk.skeleton.ui.component.EntityAppBar
import com.codepunk.skeleton.ui.component.ImagesSection
import com.codepunk.skeleton.ui.component.ProfileSection
import com.codepunk.skeleton.ui.component.RelatedArtistView
import com.codepunk.skeleton.ui.component.RelatedReleaseView
import com.codepunk.skeleton.ui.component.UrlsSection
import com.codepunk.skeleton.ui.preview.ArtistPreviewParameterProvider
import com.codepunk.skeleton.ui.theme.SkeletonTheme
import com.codepunk.skeleton.ui.theme.largePadding
import com.codepunk.skeleton.ui.theme.mediumPadding
import com.codepunk.skeleton.ui.theme.smallPadding
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
            EntityAppBar(
                scrollBehavior = scrollBehavior,
                entity = state.artist
            ) {
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
            }
        }
    ) { innerPadding ->
        state.artist?.run {
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
                        .padding(horizontal = largePadding, vertical = mediumPadding)
                        .verticalScroll(columnScrollState),
                    verticalArrangement = Arrangement.spacedBy(smallPadding)
                ) {

                    var hasContent = false

                    val hasProfile = profile.isNotEmpty()
                    ProfileSection(entity = state.artist)
                    hasContent = hasContent || hasProfile

                    val hasUrls = urls.isNotEmpty()
                    UrlsSection(entity = state.artist)
                    hasContent = hasContent || hasUrls


                    val hasImages = images.isNotEmpty()
                    if (hasContent && hasImages) {
                        HorizontalDivider()
                    }
                    ImagesSection(entity = state.artist)
                    hasContent = hasContent || hasImages

                    // Releases

                    val releasesLazyListState: LazyListState = rememberLazyListState()

                    if (releases.itemCount > 0) {
                        HorizontalDivider()
                        Text(
                            text = stringResource(id = R.string.releases),
                            style = MaterialTheme.typography.titleLarge
                        )
                        LazyRow(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(176.dp),
                            horizontalArrangement = Arrangement.spacedBy(mediumPadding),
                            state = releasesLazyListState
                        ) {
                            items(
                                count = releases.itemCount,
                                key = releases.itemKey { it.releaseId }
                            ) { index ->
                                releases[index]?.also { release ->
                                    RelatedReleaseView(
                                        thumbnailSize = 96.dp,
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

                    if (state.artist.aliases.isNotEmpty()) {
                        HorizontalDivider()
                        Text(
                            text = stringResource(id = R.string.aliases),
                            style = MaterialTheme.typography.titleLarge
                        )
                        LazyRow(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(176.dp),
                            horizontalArrangement = Arrangement.spacedBy(mediumPadding),
                            state = aliasesLazyListState
                        ) {
                            items(
                                items = state.artist.aliases,
                                key = { it.artistId }
                            ) {
                                RelatedArtistView(
                                    thumbnailSize = 96.dp,
                                    relatedArtist = it
                                )
                            }
                        }
                    }


                    // Related artists - Members

                    val membersLazyListState = rememberLazyListState()

                    if (state.artist.members.isNotEmpty()) {
                        HorizontalDivider()
                        Text(
                            text = stringResource(id = R.string.members),
                            style = MaterialTheme.typography.titleLarge
                        )
                        LazyRow(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(176.dp),
                            horizontalArrangement = Arrangement.spacedBy(mediumPadding),
                            state = membersLazyListState
                        ) {
                            items(
                                items = state.artist.members,
                                key = { it.artistId }
                            ) {
                                RelatedArtistView(
                                    thumbnailSize = 96.dp,
                                    relatedArtist = it
                                )
                            }
                        }
                    }

                    // Related artists - Groups

                    val groupsLazyListState = rememberLazyListState()

                    if (state.artist.groups.isNotEmpty()) {
                        HorizontalDivider()
                        Text(
                            text = stringResource(id = R.string.groups),
                            style = MaterialTheme.typography.titleLarge
                        )
                        LazyRow(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(176.dp),
                            horizontalArrangement = Arrangement.spacedBy(mediumPadding),
                            state = groupsLazyListState
                        ) {
                            items(
                                items = state.artist.groups,
                                key = { it.artistId }
                            ) {
                                RelatedArtistView(
                                    thumbnailSize = 96.dp,
                                    relatedArtist = it
                                )
                            }
                        }
                    }
                }
            }
        }
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
