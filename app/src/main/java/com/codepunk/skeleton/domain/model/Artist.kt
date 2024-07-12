package com.codepunk.skeleton.domain.model

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
    val aliases: List<RelatedArtist> = emptyList(),
    val members: List<RelatedArtist> = emptyList(),
    val groups: List<RelatedArtist> = emptyList()
) : Entity
