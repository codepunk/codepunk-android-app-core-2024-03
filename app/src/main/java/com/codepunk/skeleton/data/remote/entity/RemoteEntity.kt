package com.codepunk.skeleton.data.remote.entity

sealed interface RemoteEntity : RemoteResource {
    val name: String
    val profile: String
    val releasesUrl: String
    val urls: List<String>
}
