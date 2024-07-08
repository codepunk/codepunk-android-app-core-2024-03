package com.codepunk.skeleton.data.mapper

import com.codepunk.skeleton.data.local.entity.LocalVideo
import com.codepunk.skeleton.data.remote.entity.RemoteVideo
import com.codepunk.skeleton.domainv2.model.Video

fun RemoteVideo.toLocalVideo(id: Long = 0): LocalVideo = LocalVideo(
    id = id,
    title = this.title,
    description = this.description,
    uri = this.uri,
    duration = this.duration,
    embed = this.embed
)

fun LocalVideo.toDomainVideo(): Video = Video(
    title = this.title,
    description = this.description,
    uri = this.uri,
    duration = this.duration,
    embed = this.embed
)
