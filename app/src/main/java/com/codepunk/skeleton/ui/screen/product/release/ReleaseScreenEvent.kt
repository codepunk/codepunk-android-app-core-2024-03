package com.codepunk.skeleton.ui.screen.product.release

sealed class ReleaseScreenEvent {

    data class LoadRelease(val releaseId: Long) : ReleaseScreenEvent()

    data object RefreshRelease : ReleaseScreenEvent()

}