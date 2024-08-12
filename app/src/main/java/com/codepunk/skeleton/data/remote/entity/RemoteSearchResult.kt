package com.codepunk.skeleton.data.remote.entity

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonClassDiscriminator

@OptIn(ExperimentalSerializationApi::class)
@JsonClassDiscriminator("type")
@Serializable
sealed interface RemoteSearchResult {
    val id: Long
    val masterId: Long?
    val masterUrl: String?
    val uri: String
    val title: String
    val thumb: String
    val coverImage: String
    val resourceUrl: String
}
