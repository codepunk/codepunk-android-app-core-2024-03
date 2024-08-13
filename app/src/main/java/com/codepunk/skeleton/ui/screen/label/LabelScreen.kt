package com.codepunk.skeleton.ui.screen.label

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
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
import androidx.compose.ui.unit.dp
import com.codepunk.skeleton.R
import com.codepunk.skeleton.ui.component.EntityAppBar
import com.codepunk.skeleton.ui.component.ImagesSection
import com.codepunk.skeleton.ui.component.ProfileSection
import com.codepunk.skeleton.ui.component.RelatedLabel
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
                    ProfileSection(profile = state.label.profile)
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

                    val hasParentLabel = parentLabel != null
                    if (hasContent && hasParentLabel) {
                        HorizontalDivider()
                    }
                    parentLabel?.apply {
                        Text(
                            text = stringResource(id = R.string.parent_label),
                            style = MaterialTheme.typography.titleLarge
                        )
                        RelatedLabel(
                            iconSize = 72.dp,
                            relatedLabel = this
                        )
                    }
                    // TODO Parent Label
                    hasContent = hasContent || hasParentLabel

                    val subLabelsLazyListState = rememberLazyListState()

                    val hasSubLabels = subLabels.isNotEmpty()
                    if (hasContent && hasSubLabels) {
                        HorizontalDivider()
                    }
                    Text(
                        text = stringResource(id = R.string.sub_labels),
                        style = MaterialTheme.typography.titleLarge
                    )
                    LazyRow(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(176.dp),
                        horizontalArrangement = Arrangement.spacedBy(largePadding),
                        state = subLabelsLazyListState
                    ) {
                        items(
                            items = state.label.subLabels,
                            key = { it.labelId }
                        ) {
                            RelatedLabel(
                                iconSize = 72.dp,
                                relatedLabel = it
                            )
                        }
                    }
                    // hasContent = hasContent || hasSubLabels

                }
            }
        }
    }
}
