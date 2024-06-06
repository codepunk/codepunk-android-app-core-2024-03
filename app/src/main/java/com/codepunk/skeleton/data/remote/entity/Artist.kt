package com.codepunk.skeleton.data.remote.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@SerialName("artist")
@Serializable
data class Artist(
    override val id: Int,
    override val type: String,
    override val title: String
): Entity()
