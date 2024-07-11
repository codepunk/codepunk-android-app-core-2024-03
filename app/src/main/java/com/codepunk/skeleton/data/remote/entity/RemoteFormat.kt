package com.codepunk.skeleton.data.remote.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RemoteFormat(
    val name: String = "",
    @SerialName("qty")
    val quantity: Int = 0,
    val text: String = "",
    val descriptions: List<String> = emptyList()
)
