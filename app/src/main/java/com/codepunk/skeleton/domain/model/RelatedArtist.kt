package com.codepunk.skeleton.domain.model

data class RelatedArtist(
    val id: Long = 0L,
    val name: String = "",
    val resourceUrl: String = "",
    val active: Boolean? = null,
    val thumbnailUrl: String? = null
)
