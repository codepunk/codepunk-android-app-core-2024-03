package com.codepunk.skeleton.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.codepunk.skeleton.R
import com.codepunk.skeleton.domain.model.Credit
import com.codepunk.skeleton.ui.theme.mediumPadding

@Composable
fun CreditsSection(
    modifier: Modifier = Modifier,
    credits: List<Credit>
) {
    val lazyListState = rememberLazyListState()

    Text(
        text = stringResource(id = R.string.artists),
        style = MaterialTheme.typography.titleLarge
    )
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .height(176.dp),
        horizontalArrangement = Arrangement.spacedBy(mediumPadding),
        state = lazyListState
    ) {
        items(
            items = credits,
            key = { it.artistId }
        ) {
            Credit(
                thumbnailSize = 96.dp,
                credit = it
            )
        }
    }
}