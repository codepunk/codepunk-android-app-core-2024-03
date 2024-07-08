package com.codepunk.skeleton.domainv2.model

data class ArtistReference(
    override val id: Long = 0L,
    override val name: String = "",
    override val resourceUrl: String = "",
    val active: Boolean? = null,
    val thumbnailUrl: String? = null
) : Reference
