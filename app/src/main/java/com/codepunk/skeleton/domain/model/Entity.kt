package com.codepunk.skeleton.domain.model

sealed interface Entity : Resource {
    val name: String
    val profile: String
    val releasesUrl: String
    val urls: List<String>
}
