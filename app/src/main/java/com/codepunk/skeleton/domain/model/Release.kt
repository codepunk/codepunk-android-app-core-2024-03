package com.codepunk.skeleton.domain.model

import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import java.math.BigDecimal

data class Release(
    override val id: Long = 0L,
    override val resourceUrl: String = "",
    override val uri: String = "",
    override val images: List<Image> = emptyList(),
    override val dataQuality: String = "",
    override val title: String = "",
    override val genres: List<String> = emptyList(),
    override val styles: List<String> = emptyList(),
    override val year: Int = 0,
    override val numForSale: Int = 0,
    override val lowestPrice: BigDecimal? = null,
    override val trackList: List<Track> = emptyList(),
    override val artists: List<CreditReference> = emptyList(),
    override val videos: List<Video> = emptyList(),
    val status: String = "",
    val artistsSort: String = "",
    val labels: List<LabelReference> = emptyList(),
    val series: List<LabelReference> = emptyList(),
    val companies: List<LabelReference> = emptyList(),
    val formats: List<Format> = emptyList(),
    val formatQuantity: Int = 0,
    val dateAdded: Instant = Instant.DISTANT_PAST,
    val dateChanged: Instant = Instant.DISTANT_PAST,
    val masterId: Long = 0L,
    val masterUrl: String = "",
    val country: String = "",
    val released: LocalDate = LocalDate.fromEpochDays(0),
    val notes: String = "",
    val releasedFormatted: String = "",
    val identifiers: List<Identifier> = emptyList(),
    val extraArtists: List<CreditReference> = emptyList(),
    val thumb: String = ""
) : Product
