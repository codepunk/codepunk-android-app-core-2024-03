package com.codepunk.skeleton.ui.screen.product.master

import com.codepunk.skeleton.domain.model.Master

data class MasterScreenState(
    val masterId: Long = -1L, // TODO Does this make sense? I put it here so I could do "refresh"
    val isLoading: Boolean = false,
    val master: Master? = null,
    val throwable: Throwable? = null,
    val isConnected: Boolean = false
)
