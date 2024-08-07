package com.codepunk.skeleton.data.remote.entity

import kotlinx.serialization.Serializable

@Serializable
data class RemoteUrls(
    val first: String? = null,
    val last: String? = null,
    val prev: String? = null,
    val next: String? = null
)
