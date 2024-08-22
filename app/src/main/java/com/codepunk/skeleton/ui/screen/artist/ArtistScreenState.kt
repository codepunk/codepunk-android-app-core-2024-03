package com.codepunk.skeleton.ui.screen.artist

import androidx.paging.PagingData
import com.codepunk.skeleton.domain.model.Artist
import com.codepunk.skeleton.domain.model.RelatedRelease
import com.codepunk.skeleton.util.url.UrlInfo

data class ArtistScreenState(
    val artistId: Long = -1L, // TODO Does this make sense? I put it here so I could do "refresh"
    val isLoading: Boolean = false,
    val artist: Artist? = null,
    val urlInfos: List<UrlInfo> = emptyList(),
    val releases: PagingData<RelatedRelease> = PagingData.empty(), // TODO
    val throwable: Throwable? = null,
    val isConnected: Boolean = false
)
