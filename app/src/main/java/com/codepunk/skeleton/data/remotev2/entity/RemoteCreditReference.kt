package com.codepunk.skeleton.data.remotev2.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RemoteCreditReference(
    override val id: Long = 0L,
    override val name: String = "",
    @SerialName("resource_url")
    override val resourceUrl: String = "",
    val anv: String = "",
    val join: String = "",
    val role: String = "",
    val tracks: String = "",
    @SerialName("thumbnail_url")
    val thumbnailUrl: String = "",
) : RemoteReference
