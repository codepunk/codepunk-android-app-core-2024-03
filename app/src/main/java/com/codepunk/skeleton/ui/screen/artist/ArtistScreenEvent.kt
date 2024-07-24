package com.codepunk.skeleton.ui.screen.artist

sealed class ArtistScreenEvent {

    data class LoadArtist(val artistId: Long) : ArtistScreenEvent()

    data object RefreshArtist : ArtistScreenEvent()

    data object TryPaging : ArtistScreenEvent()

}