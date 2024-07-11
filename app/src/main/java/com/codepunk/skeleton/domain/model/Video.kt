package com.codepunk.skeleton.domain.model

data class Video(
    val title: String = "",
    val description: String = "",
    val uri: String = "",
    val duration: Int = 0,
    val embed: Boolean = false
)
