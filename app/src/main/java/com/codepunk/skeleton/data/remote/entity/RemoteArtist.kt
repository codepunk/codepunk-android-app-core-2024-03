package com.codepunk.skeleton.data.remote.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RemoteArtist(
    override val id: Long = 0L,
    @SerialName("resource_url")
    override val resourceUrl: String = "",
    override val uri: String = "",
    override val images: List<RemoteImage> = emptyList(),
    @SerialName("data_quality")
    override val dataQuality: String = "",
    override val name: String = "",
    override val profile: String = "",
    @SerialName("releases_url")
    override val releasesUrl: String = "",
    override val urls: List<String> = emptyList(),
    @Suppress("SpellCheckingInspection")
    @SerialName("realname")
    val realName: String? = null,
    @Suppress("SpellCheckingInspection")
    @SerialName("namevariations")
    val nameVariations: List<String> = emptyList(),
    val aliases: List<RemoteArtistReference> = emptyList(),
    val members: List<RemoteArtistReference> = emptyList(),
    val groups: List<RemoteArtistReference> = emptyList()
) : RemoteEntity
