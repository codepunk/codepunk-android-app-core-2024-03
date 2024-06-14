package com.codepunk.skeleton.domain.model

data class Artist(
    val id: Long = 0,
    val name: String = "",
    val resourceUrl: String = "",
    val uri: String = "",
    val releasesUrl: String = "",
    val images: List<Image> = emptyList(),
    val profile: String = "",
    val urls: List<String> = emptyList(),
    val nameVariations: List<String> = emptyList(),
    val aliases: List<Relationship> = emptyList(),
    val members: List<Relationship> = emptyList(),
    val groups: List<Relationship> = emptyList(),
    val dataQuality: String = ""
) {

    // region Nested & inner classes

    data class Relationship(
        val id: Long = 0,
        val name: String = "",
        val resourceUrl: String = "",
        val active: Boolean? = null,
        val thumbnailUrl: String = ""
    )

    // endregion Nested & inner classes

}
