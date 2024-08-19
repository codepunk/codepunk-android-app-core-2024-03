package com.codepunk.skeleton.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.codepunk.skeleton.R
import com.codepunk.skeleton.domain.model.Track
import com.codepunk.skeleton.ui.theme.smallPadding

@Composable
fun TrackListSection(
    modifier: Modifier = Modifier,
    trackList: List<Track>
) {
    Text(
        text = stringResource(id = R.string.track_list),
        style = MaterialTheme.typography.titleLarge
    )
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(smallPadding)
    ) {
        val flattenedTracks = trackList.flattenTracks()
        flattenedTracks.forEach { (level, track) ->
            Track(
                track = track,
                level = level
            )
        }
    }
}

fun List<Track>?.flattenTracks(level: Int = 0): List<Pair<Int, Track>> =
    this?.flatMap { track ->
        listOf(level to track) + track.subTracks.flattenTracks(level + 1)
    } ?: emptyList()
