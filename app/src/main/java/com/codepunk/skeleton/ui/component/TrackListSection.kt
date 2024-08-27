package com.codepunk.skeleton.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.codepunk.skeleton.domain.model.Track
import com.codepunk.skeleton.ui.theme.mediumPadding
import com.codepunk.skeleton.ui.theme.smallPadding

@Composable
fun TrackListSection(
    modifier: Modifier = Modifier,
    trackList: List<Track>
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = mediumPadding),
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
