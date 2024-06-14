package com.codepunk.skeleton.data.remote.entity

import com.codepunk.skeleton.data.remote.serializer.ImageTypeSerializer
import com.codepunk.skeleton.domain.type.ImageType
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RemoteImage(
    @Serializable(with = ImageTypeSerializer::class)
    val type: ImageType = ImageType.PRIMARY,
    val uri: String = "",
    @SerialName("resource_url")
    val resourceUrl: String = "",
    val uri150: String = "",
    val width: Int = 0,
    val height: Int = 0
)
