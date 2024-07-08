package com.codepunk.skeleton.domain.model

import com.codepunk.skeleton.domainv2.model.Image
import com.codepunk.skeleton.domainv2.model.Track
import com.codepunk.skeleton.domainv2.model.Video
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import java.math.BigDecimal

data class Release(
    val id: Long = 0L,
    val status: String = "",
    val year: Int = 0,
    val resourceUrl: String = "",
    val uri: String = "",
    val artists: List<Credit> = emptyList(),
    val artistsSort: String = "",
    val labels: List<Relationship> = emptyList(),
    val series: List<Relationship> = emptyList(),
    val companies: List<Relationship> = emptyList(),
    val formats: List<Format> = emptyList(),
    val dataQuality: String = "",
    val formatQuantity: Int = 0,
    val dateAdded: Instant = Instant.DISTANT_PAST,
    val dateChanged: Instant = Instant.DISTANT_PAST,
    val numForSale: Int = 0,
    val lowestPrice: BigDecimal = BigDecimal(0),
    val masterId: Long = 0L, // TODO Nullable?
    val masterUrl: String = "", // TODO Nullable?
    val title: String = "",
    val released: LocalDate = LocalDate.fromEpochDays(0),
    val notes: String = "",
    val releasedFormatted: String = "",
    val identifiers: List<Identifier> = emptyList(),
    val videos: List<Video> = emptyList(),
    val genres: List<String> = emptyList(),
    val styles: List<String> = emptyList(),
    val trackList: List<Track> = emptyList(),
    val extraArtists: List<Credit> = emptyList(),
    val images: List<Image> = emptyList(),
    val thumb: String = ""
) {

    // region Classes

    data class Relationship(
        val id: Long = 0L,
        val name: String = "",
        val catNo: String = "",
        val entityType: String = "",
        val entityTypeName: String = "",
        val resourceUrl: String = "",
    )

    data class Format(
        val name: String = "",
        val qty: Int = 0,
        val descriptions: List<String> = emptyList()
    )

    data class Identifier(
        val type: String = "",
        val value: String = "",
        val description: String? = null
    )

    // endregion Classes

}
