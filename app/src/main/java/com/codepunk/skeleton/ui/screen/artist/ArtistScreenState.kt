package com.codepunk.skeleton.ui.screen.artist

import androidx.paging.PagingData
import com.codepunk.skeleton.data.remote.entity.RemoteRelatedRelease
import com.codepunk.skeleton.domain.model.Artist

data class ArtistScreenState(
    val artistId: Long = -1L, // TODO Does this make sense? I put it here so I could do "refresh"
    val isLoading: Boolean = false,
    val artist: Artist? = null,
    val releases: PagingData<RemoteRelatedRelease>? = null, // TODO
    val throwable: Throwable? = null,
    val isConnected: Boolean = false
)
