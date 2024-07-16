package com.codepunk.skeleton.ui

import kotlinx.serialization.Serializable

@Serializable
sealed class Route {

    @Serializable
    data object HomeScreen: Route()

    @Serializable
    data class ArtistScreen(
        val artistId: Long
    ): Route()

}