package com.codepunk.skeleton.ui

import kotlinx.serialization.Serializable

@Serializable
sealed class Route {

    @Serializable
    data object Home: Route()

    @Serializable
    data class Artist(
        val artistId: Long
    ): Route()

    @Serializable
    data class Label(
        val labelId: Long
    ): Route()

    @Serializable
    data class Master(
        val masterId: Long
    ): Route()

    @Serializable
    data class Release(
        val releaseId: Long
    ): Route()

}
