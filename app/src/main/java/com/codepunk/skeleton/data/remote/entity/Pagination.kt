package com.codepunk.skeleton.data.remote.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Pagination(
    val page: Int,
    val pages: Int,
    @SerialName(value = "per_page")
    val perPage: Int,
    val items: Int,
    val urls: Urls
) {

    // region Nested & inner classes

    @Serializable
    data class Urls(
        val last: String,
        val next: String
    )

    // endregion Nested & inner classes

}
