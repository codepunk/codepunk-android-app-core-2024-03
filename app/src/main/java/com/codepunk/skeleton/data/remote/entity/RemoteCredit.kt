package com.codepunk.skeleton.data.remote.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RemoteCredit(
    @SerialName("id")
    val artistId: Long = 0,
    val name: String = "",
    val anv: String = "",
    val join: String = "",
    val role: String = "",
    val tracks: String = "",
    @SerialName("resource_url")
    val resourceUrl: String = "",
    @SerialName("thumbnail_url")
    val thumbnailUrl: String = ""
)