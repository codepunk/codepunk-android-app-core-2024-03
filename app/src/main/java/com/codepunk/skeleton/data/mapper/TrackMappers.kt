package com.codepunk.skeleton.data.mapper

import com.codepunk.skeleton.data.local.entity.LocalTrack
import com.codepunk.skeleton.data.remote.entity.RemoteTrack
import com.codepunk.skeleton.domainv2.model.Track
import com.codepunk.skeleton.util.parseElapsedTimeString
import com.codepunk.skeleton.util.toElapsedTimeString
import kotlin.time.DurationUnit

fun RemoteTrack.toLocalTrack(id: Long = 0): LocalTrack = LocalTrack(
    id = id,
    position = this.position,
    type = this.type,
    title = this.title,
    duration = this.duration.toElapsedTimeString(DurationUnit.MINUTES)
)

fun LocalTrack.toDomainTrack(): Track = Track(
    position = this.position,
    type = this.type,
    title = this.title,
    duration = parseElapsedTimeString(this.duration)
)
