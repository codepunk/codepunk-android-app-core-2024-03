package com.codepunk.skeleton.data.remote.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RemoteLabelReference(
    val id: Long = 0L,
    val name: String = "",
    @SerialName("resource_url")
    val resourceUrl: String = ""
)
