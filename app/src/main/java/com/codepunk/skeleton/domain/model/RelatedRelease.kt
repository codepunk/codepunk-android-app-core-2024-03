package com.codepunk.skeleton.domain.model

data class RelatedRelease(
    val releaseId: Long = 0L,
    val resourceUrl: String = "",
    val status: String = "",
    val type: String = "",
    val format: String = "",
    val label: String = "",
    val title: String = "",
    val role: String = "",
    val artist: String = "",
    val year: Int = 0,
    val thumb: String = ""
)
