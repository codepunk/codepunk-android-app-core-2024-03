package com.codepunk.skeleton.domain.model

sealed interface Resource {
    val id: Long
    val resourceUrl: String
    val uri: String
    val images: List<Image>
    val dataQuality: String
}
