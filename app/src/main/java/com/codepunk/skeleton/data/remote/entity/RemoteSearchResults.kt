package com.codepunk.skeleton.data.remote.entity

import kotlinx.serialization.Serializable

@Serializable
data class RemoteSearchResults(
    val pagination: RemotePagination = RemotePagination(),
    val results: List<RemoteSearchResult> = emptyList()
)
