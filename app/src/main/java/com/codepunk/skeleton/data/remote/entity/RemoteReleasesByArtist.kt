package com.codepunk.skeleton.data.remote.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RemoteReleasesByArtist(
    val pagination: RemotePagination = RemotePagination(),
    @SerialName("releases")
    val relatedReleases: List<RemoteRelatedRelease> = emptyList()
)
