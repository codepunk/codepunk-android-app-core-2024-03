package com.codepunk.skeleton.data.remote.entity

import kotlinx.serialization.Serializable

@Serializable
data class RemoteIdentifier(
    val type: String = "",
    val value: String = "",
    val description: String? = null
)
