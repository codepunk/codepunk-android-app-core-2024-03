package com.codepunk.skeleton.ui.screen.label

import com.codepunk.skeleton.domain.model.Label
import com.codepunk.skeleton.util.url.UrlInfo

data class LabelScreenState(
    val labelId: Long = -1L, // TODO Does this make sense? I put it here so I could do "refresh"
    val isLoading: Boolean = false,
    val label: Label? = null,
    /*
    val releases: PagingData<RelatedRelease> = PagingData.empty(), // TODO
     */
    val urlInfos: List<UrlInfo> = emptyList(),
    val throwable: Throwable? = null,
    val isConnected: Boolean = false
)
