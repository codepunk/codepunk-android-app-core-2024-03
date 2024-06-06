package com.codepunk.skeleton.data.remote.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@SerialName("label")
@Serializable
data class Label(
    override val id: Int,
    override val type: String,
    override val title: String
): Entity()
