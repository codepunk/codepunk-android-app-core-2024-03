package com.codepunk.skeleton.domain.model

import java.math.BigDecimal

data class Master(
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
    val mainRelease: Long = 0L,
    val mostRecentRelease: Long = 0L,
    val versionsUrl: String = "",
    val mainReleaseUrl: String = "",
    val mostRecentReleaseUrl: String = ""
) : Product
