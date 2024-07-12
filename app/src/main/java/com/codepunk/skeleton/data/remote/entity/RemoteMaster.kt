package com.codepunk.skeleton.data.remote.entity

import com.codepunk.skeleton.data.remote.serializer.BigDecimalSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.math.BigDecimal

@Serializable
data class RemoteMaster(
    override val id: Long = 0L,
    @SerialName("resource_url")
    override val resourceUrl: String = "",
    override val uri: String = "",
    override val images: List<RemoteImage> = emptyList(),
    @SerialName("data_quality")
    override val dataQuality: String = "",
    override val title: String = "",
    override val genres: List<String> = emptyList(),
    override val styles: List<String> = emptyList(),
    override val year: Int = 0,
    @SerialName("num_for_sale")
    override val numForSale: Int = 0,
    @Serializable(with = BigDecimalSerializer::class)
    @SerialName("lowest_price")
    override val lowestPrice: BigDecimal? = null,
    @Suppress("SpellCheckingInspection")
    @SerialName("tracklist")
    override val trackList: List<RemoteTrack> = emptyList(),
    override val artists: List<RemoteCredit> = emptyList(),
    override val videos: List<RemoteVideo> = emptyList(),
    @SerialName("main_release")
    val mainRelease: Long = 0L,
    @SerialName("most_recent_release")
    val mostRecentRelease: Long = 0L,
    @SerialName("versions_url")
    val versionsUrl: String = "",
    @SerialName("main_release_url")
    val mainReleaseUrl: String = "",
    @SerialName("most_recent_release_url")
    val mostRecentReleaseUrl: String = ""
) : RemoteProduct
