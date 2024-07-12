package com.codepunk.skeleton.data.remote.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RemoteLabel(
    override val id: Long = 0L,
    @SerialName("resource_url")
    override val resourceUrl: String = "",
    override val uri: String = "",
    override val images: List<RemoteImage> = emptyList(),
    @SerialName("data_quality")
    override val dataQuality: String = "",
    override val name: String = "",
    override val profile: String = "",
    @SerialName("releases_url")
    override val releasesUrl: String = "",
    override val urls: List<String> = emptyList(),
    @SerialName("contact_info")
    val contactInfo: String = "",
    @SerialName("parent_label")
    val parentLabel: RemoteRelatedLabel? = null,
    @Suppress("SpellCheckingInspection")
    @SerialName("sublabels")
    val subLabels: List<RemoteRelatedLabel> = emptyList()
) : RemoteEntity
