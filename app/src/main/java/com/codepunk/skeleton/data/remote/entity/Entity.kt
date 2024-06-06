package com.codepunk.skeleton.data.remote.entity

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonClassDiscriminator

@OptIn(ExperimentalSerializationApi::class)
@JsonClassDiscriminator(discriminator = "type")
@Serializable //(with = EntitySerializer::class)
sealed class Entity {
    abstract val id: Int
    abstract val type: String
    abstract val title: String
}
