package com.codepunk.skeleton.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.FlowRowOverflow
import androidx.compose.foundation.layout.size
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Icon
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.codepunk.skeleton.R
import com.codepunk.skeleton.ui.theme.smallPadding

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun DetailsSection(
    modifier: Modifier = Modifier,
    details: List<String>,
    title: String,
    iconMapping: (String) -> Int = { R.drawable.ic_music_notes_24 },
    onClick: (String) -> Unit
) {
    val detailsCollapsedLines = integerResource(id = R.integer.details_collapsed_lines)
    var detailsMaxLines by remember {
        mutableIntStateOf(detailsCollapsedLines)
    }

    Text(
        text = title,
        style = MaterialTheme.typography.titleLarge
    )

    FlowRow(
        horizontalArrangement = Arrangement.spacedBy(smallPadding),
        maxLines = detailsMaxLines,
        overflow = FlowRowOverflow.expandOrCollapseIndicator(
            expandIndicator = {
                TextButton(
                    onClick = { detailsMaxLines = Int.MAX_VALUE }
                ) {
                    Text(stringResource(id = R.string.show_more))
                }
            },
            collapseIndicator = {
                TextButton(
                    onClick = { detailsMaxLines = detailsCollapsedLines }
                ) {
                    Text(stringResource(id = R.string.show_less))
                }
            },
            minRowsToShowCollapse = detailsCollapsedLines + 1
        )
    ) {
        details.forEach { detail ->
            AssistChip(
                onClick = { onClick(detail) },
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = iconMapping(detail)),
                        contentDescription = detail,
                        modifier = Modifier.size(AssistChipDefaults.IconSize)
                    )
                },
                label = { Text(text = detail) }
            )
        }
    }
}
