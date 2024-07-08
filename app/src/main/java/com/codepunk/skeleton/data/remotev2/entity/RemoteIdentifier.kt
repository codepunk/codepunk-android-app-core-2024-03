package com.codepunk.skeleton.data.remotev2.entity

import kotlinx.serialization.Serializable

@Serializable
data class RemoteIdentifier(
    val type: String = "",
    val value: String = "",
    val description: String? = null
)
