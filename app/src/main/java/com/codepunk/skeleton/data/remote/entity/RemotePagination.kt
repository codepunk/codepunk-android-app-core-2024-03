package com.codepunk.skeleton.data.remote.entity

import kotlinx.serialization.SerialName

data class RemotePagination(
    val page: Int = 0,
    val pages: Int = 0,
    @SerialName("per_page")
    val perPage: Int = 0,
    val items: Int = 0,
    val urls: RemoteUrls = RemoteUrls()
)
