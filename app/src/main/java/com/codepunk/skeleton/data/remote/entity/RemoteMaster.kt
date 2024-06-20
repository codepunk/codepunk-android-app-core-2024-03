package com.codepunk.skeleton.data.remote.entity

import com.codepunk.skeleton.data.remote.serializer.BigDecimalSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.math.BigDecimal

@Serializable
data class RemoteMaster(
    val id: Long = 0,
    val title: String = "",
    @SerialName("main_release")
    val mainRelease: Long = 0,
    @SerialName("most_recent_release")
    val mostRecentRelease: Long = 0,
    @SerialName("resource_url")
    val resourceUrl: String = "",
    val uri: String = "",
    @SerialName("versions_url")
    val versionsUrl: String = "",
    @SerialName("main_release_url")
    val mainReleaseUrl: String = "",
    @SerialName("most_recent_release_url")
    val mostRecentReleaseUrl: String = "",
    val numForSale: Int = 0,
    @Serializable(with = BigDecimalSerializer::class)
    @SerialName("lowest_price")
    val lowestPrice: BigDecimal = BigDecimal(0),
    val images: List<RemoteImage> = emptyList(),
    val genres: List<String> = emptyList(),
    val styles: List<String> = emptyList(),
    val year: Int = 0,
    @Suppress("SpellCheckingInspection")
    @SerialName("tracklist")
    val trackList: List<RemoteTrack> = emptyList(),
    val artists: List<RemoteCredit> = emptyList(),
    @SerialName("data_quality")
    val dataQuality: String = "",
    val videos: List<RemoteVideo> = emptyList()
)
