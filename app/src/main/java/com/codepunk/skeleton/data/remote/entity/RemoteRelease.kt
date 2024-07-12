package com.codepunk.skeleton.data.remote.entity

import com.codepunk.skeleton.data.remote.serializer.BigDecimalSerializer
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.math.BigDecimal

@Serializable
data class RemoteRelease(
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
    val status: String = "",
    @SerialName("artists_sort")
    val artistsSort: String = "",
    val labels: List<RemoteLabelReference> = emptyList(),
    val series: List<RemoteLabelReference> = emptyList(),
    val companies: List<RemoteLabelReference> = emptyList(),
    val formats: List<RemoteFormat> = emptyList(),
    @SerialName("format_quantity")
    val formatQuantity: Int = 0,
    @SerialName("date_added")
    val dateAdded: Instant = Instant.DISTANT_PAST,
    @SerialName("date_changed")
    val dateChanged: Instant = Instant.DISTANT_PAST,
    @SerialName("master_id")
    val masterId: Long = 0L,
    @SerialName("master_url")
    val masterUrl: String = "",
    val country: String = "",
    val released: LocalDate = LocalDate.fromEpochDays(0),
    val notes: String = "",
    @SerialName("released_formatted")
    val releasedFormatted: String = "",
    val identifiers: List<RemoteIdentifier> = emptyList(),
    @Suppress("SpellCheckingInspection")
    @SerialName("extraartists")
    val extraArtists: List<RemoteCredit> = emptyList(),
    val thumb: String = ""
) : RemoteProduct
