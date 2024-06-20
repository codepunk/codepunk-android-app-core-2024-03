package com.codepunk.skeleton.data.remote.entity

import kotlinx.serialization.Serializable

@Serializable
data class RemoteVideo(
    val title: String = "",
    val description: String = "",
    val uri: String = "",
    val duration: Int = 0,
    val embed: Boolean = false
)
