package com.codepunk.skeleton.data.mapper

import com.codepunk.skeleton.data.local.entity.LocalVideo
import com.codepunk.skeleton.data.remote.entity.RemoteVideo
import com.codepunk.skeleton.domain.model.Video

fun RemoteVideo.toLocal(
    videoId: Long = 0
): LocalVideo =
    LocalVideo(
        videoId = videoId,
        title = title,
        description = description,
        uri = uri,
        duration = duration,
        embed = embed

    )

fun LocalVideo.toDomain(): Video =
    Video(
        title = title,
        description = description,
        uri = uri,
        duration = duration,
        embed = embed
    )
