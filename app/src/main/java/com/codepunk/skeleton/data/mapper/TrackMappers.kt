package com.codepunk.skeleton.data.mapper

import androidx.compose.ui.util.fastForEachReversed
import com.codepunk.skeleton.data.local.entity.LocalCredit.CreditType
import com.codepunk.skeleton.data.local.entity.LocalTrack
import com.codepunk.skeleton.data.local.relation.LocalTrackWithDetails
import com.codepunk.skeleton.data.remote.entity.RemoteTrack
import com.codepunk.skeleton.domain.model.Track
import com.codepunk.skeleton.util.parseElapsedTimeString
import com.codepunk.skeleton.util.toElapsedTimeString
import java.util.Stack
import kotlin.time.DurationUnit

fun RemoteTrack.toLocal(
    trackId: Long = 0L,
    trackNum: Int = 0,
    parentTrackNum: Int = -1
): LocalTrackWithDetails =
    LocalTrackWithDetails(
        track = LocalTrack(
            trackId = trackId,
            trackNum = trackNum,
            parentTrackNum = parentTrackNum,
            position = position,
            type = type,
            title = title,
            duration = duration.toElapsedTimeString(DurationUnit.MINUTES)
        ),
        extraArtists = extraArtists?.map {
            it.toLocal(
                // creditId = [Unknown],
                type = CreditType.ARTIST
            )
        }
    )

fun LocalTrackWithDetails.toDomain(): Track =
    Track(
        position = track.position,
        type = track.type,
        title = track.title,
        extraArtists = extraArtists?.map { it.toDomain() },
        duration = parseElapsedTimeString(track.duration)
        // subTracks = [Unknown]
    )

/**
 * Uses a stack to (non-recursively) walk through track tree
 */
fun List<RemoteTrack>.toLocal(): List<LocalTrackWithDetails> {
    // Set up variables and structures
    var trackNum = -1
    val parentTrackNumMap = mutableMapOf<RemoteTrack, Int>()
    val remoteTrackStack = Stack<RemoteTrack>()
    fastForEachReversed { remoteTrack ->
        parentTrackNumMap[remoteTrack] = trackNum
        remoteTrackStack.push(remoteTrack)
    }

    // Traverse track tree & process all items via the stack
    val localTracks = mutableListOf<LocalTrackWithDetails>()
    while (remoteTrackStack.isNotEmpty()) {
        val remoteTrack = remoteTrackStack.pop()
        trackNum++
        val parentTrackNum = parentTrackNumMap.getOrElse(remoteTrack) { -1 }
        remoteTrack.subTracks?.fastForEachReversed { childRemoteTrack ->
            // The parentTrackNum of each child is this track's trackNum
            parentTrackNumMap[childRemoteTrack] = trackNum
            remoteTrackStack.push(childRemoteTrack)
        }
        localTracks.add(
            remoteTrack.toLocal(
                trackNum = trackNum,
                parentTrackNum = parentTrackNum
            )
        )
    }
    return localTracks.toList()
}

fun List<LocalTrackWithDetails>.toDomain(): List<Track> {
    val trackNumMap = associateBy(
        keySelector = { it.track.trackNum }
    ) { it.toDomain() }.toMutableMap()

    // Build tree structure by walking through local track list
    for (trackWithDetails in this) {
        val track = trackNumMap[trackWithDetails.track.trackNum] ?: continue
        val parentTrack = trackNumMap[trackWithDetails.track.parentTrackNum] ?: continue

        // We have a valid localTrack and a valid parentLocalTrack
        // Use copy() to add to parent's subTrack list
        trackNumMap.replace(
            trackWithDetails.track.parentTrackNum,
            parentTrack.copy(
                subTracks = parentTrack.subTracks.orEmpty()
                    .toMutableList()
                    .apply { add(track) }
                    .toList()
            )
        )
    }

    // Return "root" tracks (i.e., the list of tracks with no parent)
    return filter {
        it.track.parentTrackNum == -1
    }.map {
        trackNumMap.getValue(it.track.trackNum)
    }
}
