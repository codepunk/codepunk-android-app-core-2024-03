package com.codepunk.skeleton.data.remote.entity

import com.codepunk.skeleton.data.remote.serializer.ElapsedTimeSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.time.Duration

@Serializable
data class RemoteTrack(
    val position: String = "",
    @SerialName("type_")
    val type: String = "",
    val title: String = "",
    @Serializable(with = ElapsedTimeSerializer::class)
    val duration: Duration = Duration.ZERO,
    @SerialName("sub_tracks")
    val subTracks: List<RemoteTrack>? = null
)