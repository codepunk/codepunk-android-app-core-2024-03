package com.codepunk.skeleton.ui.screen.label

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
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
import com.codepunk.skeleton.ui.component.EntityAppBar
import com.codepunk.skeleton.ui.component.ImagesSection
import com.codepunk.skeleton.ui.component.ProfileSection
import com.codepunk.skeleton.ui.component.UrlsSection
import com.codepunk.skeleton.ui.theme.largePadding
import com.codepunk.skeleton.ui.theme.mediumPadding
import com.codepunk.skeleton.ui.theme.smallPadding

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LabelScreen(
    modifier: Modifier = Modifier,
    labelId: Long,
    state: LabelScreenState,
    onEvent: (LabelScreenEvent) -> Unit = {}
) {
    LaunchedEffect(Unit) {
        onEvent(LabelScreenEvent.LoadLabel(labelId))
    }

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val columnScrollState: ScrollState = rememberScrollState()

    Scaffold(
        modifier = modifier
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            EntityAppBar(
                scrollBehavior = scrollBehavior,
                entity = state.label
            ) {
                Text(
                    text = state.label?.name ?: stringResource(id = R.string.label),
                    style = MaterialTheme.typography.headlineMedium,
                )
            }
        }
    ) { innerPadding ->
        state.label?.run {
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
                    ProfileSection(entity = state.label)
                    hasContent = hasContent || hasProfile

                    val hasUrls = urls.isNotEmpty()
                    UrlsSection(entity = state.label)
                    hasContent = hasContent || hasUrls


                    val hasImages = images.isNotEmpty()
                    if (hasContent && hasImages) {
                        HorizontalDivider()
                    }
                    ImagesSection(entity = state.label)
                    hasContent = hasContent || hasImages
                }
            }
        }
    }
}
