package com.codepunk.skeleton.data.remote.entity

sealed interface RemoteResource {
    val id: Long
    val resourceUrl: String
    val uri: String
    val images: List<RemoteImage>
    val dataQuality: String
}
