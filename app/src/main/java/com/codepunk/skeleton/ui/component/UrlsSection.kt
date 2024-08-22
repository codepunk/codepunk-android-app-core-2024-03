package com.codepunk.skeleton.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.FlowRowOverflow
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.integerResource
import androidx.compose.ui.res.stringResource
import com.codepunk.skeleton.R
import com.codepunk.skeleton.domain.model.Entity
import com.codepunk.skeleton.ui.theme.smallPadding
import com.codepunk.skeleton.util.url.UrlInfo
import com.codepunk.skeleton.util.url.UrlInfo.Domain

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun UrlsSection(
    modifier: Modifier = Modifier,
    entity: Entity
) {
    val urlsCollapsedLines = integerResource(id = R.integer.urls_collapsed_lines)
    var urlMaxLines by remember {
        mutableIntStateOf(urlsCollapsedLines)
    }

    Text(
        text = stringResource(id = R.string.links),
        style = MaterialTheme.typography.titleLarge
    )

    FlowRow(
        horizontalArrangement = Arrangement.spacedBy(smallPadding),
        maxLines = urlMaxLines,
        overflow = FlowRowOverflow.expandOrCollapseIndicator(
            expandIndicator = {
                TextButton(
                    onClick = { urlMaxLines = Int.MAX_VALUE }
                ) {
                    Text(stringResource(id = R.string.show_more))
                }
            },
            collapseIndicator = {
                TextButton(
                    onClick = { urlMaxLines = urlsCollapsedLines }
                ) {
                    Text(stringResource(id = R.string.show_less))
                }
            },
            minRowsToShowCollapse = urlsCollapsedLines + 1
        )
    ) {
        val urlInfos = entity.urls.map { UrlInfo(it) }
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
