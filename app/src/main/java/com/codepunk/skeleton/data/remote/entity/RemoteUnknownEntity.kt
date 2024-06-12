package com.codepunk.skeleton.data.remote.entity

import kotlinx.serialization.Serializable

@Serializable
data class RemoteUnknownEntity(
    val id: Long,
    val title: String
): RemoteEntity()
