package com.codepunk.skeleton.ui.screen.product.release

import com.codepunk.skeleton.domain.model.Release

data class ReleaseScreenState(
    val releaseId: Long = -1L, // TODO Does this make sense? I put it here so I could do "refresh"
    val isLoading: Boolean = false,
    val release: Release? = null,
    val throwable: Throwable? = null,
    val isConnected: Boolean = false
)
