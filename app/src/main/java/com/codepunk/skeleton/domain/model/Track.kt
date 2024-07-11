package com.codepunk.skeleton.domain.model

import kotlin.time.Duration

data class Track(
    val position: String = "",
    val type: String = "", // TODO Can be "track" or "heading" ?
    val title: String = "",
    val extraArtists: List<CreditReference>? = null,
    val duration: Duration = Duration.ZERO,
    val subTracks: List<Track>? = null
)
