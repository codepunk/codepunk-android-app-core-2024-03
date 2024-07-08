package com.codepunk.skeleton.data.remotev2.entity

import kotlinx.serialization.Serializable

@Serializable
data class RemoteVideo(
    val title: String = "",
    val description: String = "",
    val uri: String = "",
    val duration: Int = 0,
    val embed: Boolean = false
)
