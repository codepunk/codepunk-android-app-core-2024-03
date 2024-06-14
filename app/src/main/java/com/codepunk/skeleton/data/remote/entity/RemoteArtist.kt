package com.codepunk.skeleton.data.remote.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RemoteArtist(
    val id: Long = 0,
    val name: String = "",
    @SerialName("resource_url")
    val resourceUrl: String = "",
    val uri: String = "",
    @SerialName("releases_url")
    val releasesUrl: String = "",
    val images: List<RemoteImage> = emptyList(),
    val profile: String = "",
    val urls: List<String> = emptyList(),
    @SerialName("namevariations")
    val nameVariations: List<String> = emptyList(),
    val aliases: List<Relationship> = emptyList(),
    val members: List<Relationship> = emptyList(),
    val groups: List<Relationship> = emptyList(),
    @SerialName("data_quality")
    val dataQuality: String = ""
) {

    // region Nested & inner classes

    @Serializable
    data class Relationship(
        val id: Long = 0,
        val name: String = "",
        @SerialName("resource_url")
        val resourceUrl: String = "",
        val active: Boolean = true,
        @SerialName("thumbnail_url")
        val thumbnailUrl: String = ""
    )

    // endregion Nested & inner classes

}