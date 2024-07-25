package com.codepunk.skeleton.data.remote.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RemoteRelatedRelease(
    @SerialName("id")
    val releaseId: Long = 0L,
    @SerialName("resource_url")
    val resourceUrl: String = "",
    val status: String = "",
    val type: String = "",
    val format: String = "",
    val label: String = "",
    val title: String = "",
    val role: String = "",
    val artist: String = "",
    val year: Int = 0,
    val thumb: String = ""
)
