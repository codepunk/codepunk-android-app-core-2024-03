package com.codepunk.skeleton.data.remote.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Master(
    override val id: Int,
    override val title: String,
    @SerialName(value = "user_data")
    override val userData: UserData,
    @SerialName(value = "master_id")
    override val masterId: Int?,
    @SerialName(value = "master_url")
    override val masterUrl: String?,
    override val uri: String,
    override val thumb: String,
    @SerialName(value = "cover_image")
    override val coverImage: String,
    @SerialName(value = "resource_url")
    override val resourceUrl: String,
    override val country: String,
    override val year: String,
    override val format: List<String>,
    override val label: List<String>,
    override val genre: List<String>,
    override val style: List<String>,
    override val barcode: List<String>,
    @SerialName(value = "catno")
    override val categoryNumber: String
): BaseRelease()
