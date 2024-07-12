package com.codepunk.skeleton.data.remote.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RemoteRelatedArtist(
    val id: Long = 0L,
    val name: String = "",
    @SerialName("resource_url")
    val resourceUrl: String = "",
    val active: Boolean? = null,
    @SerialName("thumbnail_url")
    val thumbnailUrl: String? = null
)
