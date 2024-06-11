package com.codepunk.skeleton.data.remote.entity

import kotlinx.serialization.Serializable

@Serializable
data class UnknownEntity(
    val id: Long,
    val title: String
): Entity()
