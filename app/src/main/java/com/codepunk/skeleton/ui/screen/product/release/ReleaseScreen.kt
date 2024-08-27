package com.codepunk.skeleton.ui.screen.product.release

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.codepunk.skeleton.R
import com.codepunk.skeleton.ui.component.CreditsSection
import com.codepunk.skeleton.ui.component.DetailsSection
import com.codepunk.skeleton.ui.component.ImagesSection
import com.codepunk.skeleton.ui.component.ProductAppBar
import com.codepunk.skeleton.ui.component.TabSection
import com.codepunk.skeleton.ui.component.TrackListSection
import com.codepunk.skeleton.ui.component.VideoSection
import com.codepunk.skeleton.ui.screen.product.ProductTab
import com.codepunk.skeleton.ui.theme.largePadding
import com.codepunk.skeleton.ui.theme.mediumPadding
import com.codepunk.skeleton.ui.theme.smallPadding

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReleaseScreen(
    modifier: Modifier = Modifier,
    releaseId: Long,
    state: ReleaseScreenState,
    onEvent: (ReleaseScreenEvent) -> Unit = {}
) {
    LaunchedEffect(Unit) {
        onEvent(ReleaseScreenEvent.LoadRelease(releaseId))
    }

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val columnScrollState: ScrollState = rememberScrollState()
    val context = LocalContext.current

    Scaffold(
        modifier = modifier
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            ProductAppBar(
                scrollBehavior = scrollBehavior,
                product = state.release
            ) {
                Column {
                    Text(
                        text = state.release?.title.orEmpty(),
                        style = MaterialTheme.typography.headlineMedium,
                    )
                }
            }
        }
    ) { innerPadding ->
        state.release?.run {
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
                    if (images.isNotEmpty()) {
                        /*
                        if (hasContent) {
                            HorizontalDivider()
                        }
                         */
                        ImagesSection(resource = this@run)
                        hasContent = true
                    }

                    // Determine which sections are populated
                    val tabs = ProductTab.entries.filter { tab ->
                        when (tab) {
                            ProductTab.TRACK_LIST -> videos.isNotEmpty()
                            ProductTab.DETAILS -> genres.isNotEmpty() || styles.isNotEmpty()
                            ProductTab.ARTISTS -> artists.isNotEmpty()
                            ProductTab.VIDEOS -> videos.isNotEmpty()
                        }
                    }

                    TabSection(
                        modifier = Modifier.fillMaxWidth(),
                        tabs = tabs,
                        textOf = { context.getString(it.stringRes) }
                    ) { index ->
                        when (tabs[index]) {
                            ProductTab.TRACK_LIST -> TrackListSection(trackList = trackList)
                            ProductTab.DETAILS -> {
                                Column(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(vertical = mediumPadding)
                                ) {
                                    var hasDetails = false
                                    listOf(
                                        stringResource(id = R.string.genres) to genres,
                                        stringResource(id = R.string.styles) to styles
                                    ).forEach { (title, details) ->
                                        if (details.isNotEmpty()) {
                                            if (hasDetails) {
                                                HorizontalDivider()
                                            }
                                            DetailsSection(
                                                title = title,
                                                details = details
                                            ) { _ ->
                                                // onClick
                                            }
                                            hasDetails = true
                                        }
                                    }
                                }
                            }
                            ProductTab.ARTISTS -> CreditsSection(credits = artists)
                            ProductTab.VIDEOS -> VideoSection(product = this@run)
                        }
                    }
                }
            }
        }
    }
}
