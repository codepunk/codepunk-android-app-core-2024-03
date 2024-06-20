package com.codepunk.skeleton.domain.model

import kotlin.time.Duration

data class Track(
    val position: String = "",
    val type: String = "",
    val title: String = "",
    val duration: Duration = Duration.ZERO
)
