package com.codepunk.skeleton.data.remotev2.entity

import kotlinx.serialization.Serializable

@Serializable
data class RemoteFormat(
    val name: String = "",
    val quantity: Int = 0,
    val descriptions: List<String> = emptyList()
)
