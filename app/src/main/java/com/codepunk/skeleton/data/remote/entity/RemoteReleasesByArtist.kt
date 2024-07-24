package com.codepunk.skeleton.data.remote.entity

data class RemoteReleasesByArtist(
    val pagination: RemotePagination = RemotePagination(),
    val releases: List<RemoteRelatedRelease> = emptyList()
)
