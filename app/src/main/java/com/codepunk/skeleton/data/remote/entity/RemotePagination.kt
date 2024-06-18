package com.codepunk.skeleton.data.remote.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RemotePagination(
    val page: Int,
    val pages: Int,
    @SerialName("per_page")
    val perPage: Int,
    val items: Int,
    val urls: Urls
) {

    // region Classes

    @Serializable
    data class Urls(
        val last: String,
        val next: String
    )

    // endregion Classes

}
