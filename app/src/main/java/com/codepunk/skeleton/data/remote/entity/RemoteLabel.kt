package com.codepunk.skeleton.data.remote.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RemoteLabel(
    val id: Long = 0,
    val name: String = "",
    @SerialName("resource_url")
    val resourceUrl: String = "",
    val uri: String = "",
    @SerialName("releases_url")
    val releasesUrl: String = "",
    val images: List<RemoteImage> = emptyList(),
    @SerialName("contact_info")
    val contactInfo: String = "",
    val profile: String = "",
    @SerialName("data_quality")
    val dataQuality: String = "",
    val urls: List<String> = emptyList(),
    @Suppress("SpellCheckingInspection")
    @SerialName("sublabels")
    val subLabels: List<SubLabel> = emptyList()
) {

    // region Nested & inner classes

    @Serializable
    data class SubLabel(
        val id: Long = 0,
        val name: String = "",
        @SerialName("resource_url")
        val resourceUrl: String = ""
    )

    // endregion Nested & inner classes

}