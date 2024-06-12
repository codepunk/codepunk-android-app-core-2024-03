package com.codepunk.skeleton.data.remote.entity

import kotlinx.serialization.Serializable

@Serializable
data class SearchResults(
    val pagination: Pagination,
    val results: List<Entity>
)