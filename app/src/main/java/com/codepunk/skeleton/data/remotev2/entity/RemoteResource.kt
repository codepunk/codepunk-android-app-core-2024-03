package com.codepunk.skeleton.data.remotev2.entity

sealed interface RemoteResource {
    val id: Long
    val resourceUrl: String
    val uri: String
    val images: List<RemoteImage>
    val dataQuality: String
}
