package com.codepunk.skeleton.data.remote.entity

import com.codepunk.skeleton.data.remote.serializer.BigDecimalSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import java.math.BigDecimal

@Serializable
data class RemoteRelease(
    val id: Long = 0L,
    val status: String = "",
    val year: Int = 0,
    @SerialName("resource_url")
    val resourceUrl: String = "",
    val uri: String = "",
    val artists: List<RemoteCredit> = emptyList(),
    @SerialName("artists_sort")
    val artistsSort: String = "",
    val labels: List<Relationship> = emptyList(),
    val series: List<Relationship> = emptyList(),
    val companies: List<Relationship> = emptyList(),
    val formats: List<Format> = emptyList(),
    @SerialName("data_quality")
    val dataQuality: String = "",
    @SerialName("format_quantity")
    val formatQuantity: Int = 0,
    @SerialName("date_added")
    val dateAdded: Instant = Instant.DISTANT_PAST,
    @SerialName("date_changed")
    val dateChanged: Instant = Instant.DISTANT_PAST,
    @SerialName("num_for_sale")
    val numForSale: Int = 0,
    @Serializable(with = BigDecimalSerializer::class)
    @SerialName("lowest_price")
    val lowestPrice: BigDecimal = BigDecimal(0),
    @SerialName("master_id")
    val masterId: Long = 0L, // TODO Nullable?
    @SerialName("master_url")
    val masterUrl: String = "", // TODO Nullable?
    val title: String = "",
    val released: LocalDate = LocalDate.fromEpochDays(0),
    val notes: String = "",
    @SerialName("released_formatted")
    val releasedFormatted: String = "",
    val identifiers: List<Identifier> = emptyList(),
    val videos: List<RemoteVideo> = emptyList(),
    val genres: List<String> = emptyList(),
    val styles: List<String> = emptyList(),
    @Suppress("SpellCheckingInspection")
    @SerialName("tracklist")
    val trackList: List<RemoteTrack> = emptyList(),
    @Suppress("SpellCheckingInspection")
    @SerialName("extraartists")
    val extraArtists: List<RemoteCredit> = emptyList(),
    val images: List<RemoteImage> = emptyList(),
    val thumb: String = ""
) {

    // region Classes

    @Serializable
    data class Relationship(
        val id: Long = 0L,
        val name: String = "",
        @Suppress("SpellCheckingInspection")
        @SerialName("catno")
        val catNo: String = "",
        @SerialName("entity_type")
        val entityType: String = "",
        @SerialName("entity_type_name")
        val entityTypeName: String = "",
        @SerialName("resource_url")
        val resourceUrl: String = "",
    )

    @Serializable
    data class Format(
        val name: String = "",
        val qty: Int = 0,
        val descriptions: List<String> = emptyList()
    )

    @Serializable
    data class Identifier(
        val type: String = "",
        val value: String = "",
        val description: String? = null
    )

    // endregion Classes

}
