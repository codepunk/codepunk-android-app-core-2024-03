package com.codepunk.skeleton.domain.model

import com.codepunk.skeleton.domainv2.model.Image

data class Label(
    val id: Long = 0L,
    val name: String = "",
    val resourceUrl: String = "",
    val uri: String = "",
    val releasesUrl: String = "",
    val images: List<Image> = emptyList(),
    val contactInfo: String = "",
    val profile: String = "",
    val parentLabel: Relationship? = null,
    val dataQuality: String = "",
    val urls: List<String> = emptyList(),
    val subLabels: List<Relationship> = emptyList()
) {

    // region Classes

    data class Relationship(
        val id: Long = 0L,
        val name: String = "",
        val resourceUrl: String = ""
    )

    // endregion Classes

}
