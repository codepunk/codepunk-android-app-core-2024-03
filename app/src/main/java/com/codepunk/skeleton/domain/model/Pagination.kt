package com.codepunk.skeleton.domain.model

data class Pagination(
    val page: Int,
    val pages: Int,
    val perPage: Int,
    val items: Int,
    val urls: Urls
) {

    // region Nested & inner classes

    data class Urls(
        val last: String,
        val next: String
    )

    // endregion Nested & inner classes

}
