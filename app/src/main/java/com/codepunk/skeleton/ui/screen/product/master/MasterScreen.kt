package com.codepunk.skeleton.ui.screen.product.master

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
import androidx.compose.ui.res.stringResource
import com.codepunk.skeleton.R
import com.codepunk.skeleton.domain.model.Track
import com.codepunk.skeleton.domain.model.Video
import com.codepunk.skeleton.ui.component.CreditsSection
import com.codepunk.skeleton.ui.component.DetailsSection
import com.codepunk.skeleton.ui.component.ImagesSection
import com.codepunk.skeleton.ui.component.ProductAppBar
import com.codepunk.skeleton.ui.component.Track
import com.codepunk.skeleton.ui.component.TrackListSection
import com.codepunk.skeleton.ui.component.VideoSection
import com.codepunk.skeleton.ui.theme.largePadding
import com.codepunk.skeleton.ui.theme.mediumPadding
import com.codepunk.skeleton.ui.theme.smallPadding
import com.codepunk.skeleton.ui.theme.tinyPadding

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MasterScreen(
    modifier: Modifier = Modifier,
    masterId: Long,
    state: MasterScreenState,
    onEvent: (MasterScreenEvent) -> Unit = {}
) {
    LaunchedEffect(Unit) {
        onEvent(MasterScreenEvent.LoadMaster(masterId))
    }

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val columnScrollState: ScrollState = rememberScrollState()

    Scaffold(
        modifier = modifier
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            ProductAppBar(
                scrollBehavior = scrollBehavior,
                product = state.master
            ) {
                Column {
                    Text(
                        text = state.master?.title.orEmpty(),
                        style = MaterialTheme.typography.headlineMedium,
                    )
                }
            }
        }
    ) { innerPadding ->
        state.master?.run {
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

                    if (genres.isNotEmpty()) {
                        if (hasContent) {
                            HorizontalDivider()
                        }
                        DetailsSection(
                            title = stringResource(id = R.string.genres),
                            details = genres
                        ) { _ ->
                            // onClick
                        }
                        hasContent = true
                    }

                    if (styles.isNotEmpty()) {
                        if (hasContent) {
                            HorizontalDivider()
                        }
                        DetailsSection(
                            title = stringResource(id = R.string.styles),
                            details = styles
                        ) { _ ->
                            // onClick
                        }
                        hasContent = true
                    }

                    if (trackList.isNotEmpty()) {
                        if (hasContent) {
                            HorizontalDivider()
                        }
                        TrackListSection(trackList = trackList)
                        hasContent = true
                    }
                    
                    if (artists.isNotEmpty()) {
                        if (hasContent) {
                            HorizontalDivider()
                        }
                        CreditsSection(credits = artists)
                        hasContent = true
                    }

                    if (videos.isNotEmpty()) {
                        if (hasContent) {
                            HorizontalDivider()
                        }
                        VideoSection(product = this@run)
                    }
                }
            }
        }
    }
}

