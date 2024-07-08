package com.codepunk.skeleton.domainv2.model

data class Artist(
    override val id: Long = 0L,
    override val resourceUrl: String = "",
    override val uri: String = "",
    override val images: List<Image> = emptyList(),
    override val dataQuality: String = "",
    override val name: String = "",
    override val profile: String = "",
    override val releasesUrl: String = "",
    override val urls: List<String> = emptyList(),
    val realName: String? = null,
    val nameVariations: List<String> = emptyList(),
    val aliases: List<ArtistReference> = emptyList(),
    val members: List<ArtistReference> = emptyList(),
    val groups: List<ArtistReference> = emptyList()
) : Entity
