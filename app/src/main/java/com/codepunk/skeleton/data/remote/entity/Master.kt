package com.codepunk.skeleton.data.remote.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Master(
    val id: Long,
    val title: String,
    @SerialName(value = "user_data")
    val userData: UserData,
    @SerialName(value = "master_id")
    val masterId: Int?,
    @SerialName(value = "master_url")
    val masterUrl: String?,
    val uri: String,
    val thumb: String,
    @SerialName(value = "cover_image")
    val coverImage: String,
    @SerialName(value = "resource_url")
    val resourceUrl: String,
    val country: String,
    val year: String,
    val format: List<String>,
    val label: List<String>,
    val genre: List<String>,
    val style: List<String>,
    val barcode: List<String>,
    @SerialName(value = "catno")
    val categoryNumber: String
): Entity()
