package com.codepunk.skeleton.domain.model

data class Label(
    val id: Long = 0,
    val name: String = "",
    val resourceUrl: String = "",
    val uri: String = "",
    val releasesUrl: String = "",
    val images: List<Image> = emptyList(),
    val contactInfo: String = "",
    val profile: String = "",
    val dataQuality: String = "",
    val urls: List<String> = emptyList(),
    val subLabels: List<SubLabel> = emptyList()
) {

    // region Classes

    data class SubLabel(
        val id: Long = 0,
        val name: String = "",
        val resourceUrl: String = ""
    )

    // endregion Classes

}
