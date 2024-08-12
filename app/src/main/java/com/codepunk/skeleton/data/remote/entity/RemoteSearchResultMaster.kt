package com.codepunk.skeleton.data.remote.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("master")
data class RemoteSearchResultMaster(
    override val id: Long = 0,
    @SerialName("master_id")
    override val masterId: Long? = null,
    @SerialName("master_url")
    override val masterUrl: String? = null,
    override val uri: String = "",
    override val title: String = "",
    override val thumb: String = "",
    @SerialName("cover_image")
    override val coverImage: String = "",
    @SerialName("resource_url")
    override val resourceUrl: String = "",
    val country: String? = null,
    val year: Int? = null,
    val format: List<String>? = null,
    val label: List<String>? = null,
    val genre: List<String>? = null,
    val style: List<String>? = null,
    val barcode: List<String>? = null,
    @Suppress("SpellCheckingInspection")
    @SerialName("catno")
    val catNo: String? = null,
) : RemoteSearchResult
