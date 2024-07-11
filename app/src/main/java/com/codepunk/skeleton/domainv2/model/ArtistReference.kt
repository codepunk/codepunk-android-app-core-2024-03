package com.codepunk.skeleton.domainv2.model

data class ArtistReference(
    val id: Long = 0L,
    val name: String = "",
    val resourceUrl: String = "",
    val active: Boolean? = null,
    val thumbnailUrl: String? = null
)
