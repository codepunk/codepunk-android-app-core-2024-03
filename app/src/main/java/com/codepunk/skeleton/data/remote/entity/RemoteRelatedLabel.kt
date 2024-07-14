package com.codepunk.skeleton.data.remote.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RemoteRelatedLabel(
    @SerialName("id")
    val labelId: Long = 0L,
    val name: String = "",
    @SerialName("resource_url")
    val resourceUrl: String = ""
)
