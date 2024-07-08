package com.codepunk.skeleton.domainv2.model

import kotlin.time.Duration

data class Track(
    val position: String = "",
    val type: String = "", // TODO Can be "track" or "heading" ?
    val title: String = "",
    val extraArtists: List<CreditReference> = emptyList(),
    val duration: Duration = Duration.ZERO,
    val subTracks: List<Track>? = null
)
