package com.codepunk.skeleton.data.remote.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UnknownEntity(
    override val id: Int,
    override val title: String,
    @SerialName(value = "user_data")
    override val userData: UserData,
    @SerialName(value = "master_id")
    override val masterId: Int?,
    @SerialName(value = "master_url")
    override val masterUrl: String?,
    override val uri: String,
    override val thumb: String,
    @SerialName(value = "cover_image")
    override val coverImage: String,
    @SerialName(value = "resource_url")
    override val resourceUrl: String
): Entity()
