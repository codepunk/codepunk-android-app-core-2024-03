package com.codepunk.skeleton.domain.model

import com.codepunk.skeleton.domainv2.model.Image
import com.codepunk.skeleton.domainv2.model.Track
import com.codepunk.skeleton.domainv2.model.Video
import java.math.BigDecimal

data class Master(
    val id: Long = 0L,
    val title: String = "",
    val mainRelease: Long = 0L,
    val mostRecentRelease: Long = 0L,
    val resourceUrl: String = "",
    val uri: String = "",
    val versionsUrl: String = "",
    val mainReleaseUrl: String = "",
    val mostRecentReleaseUrl: String = "",
    val numForSale: Int = 0,
    val lowestPrice: BigDecimal = BigDecimal(0),
    val images: List<Image> = emptyList(),
    val genres: List<String> = emptyList(),
    val styles: List<String> = emptyList(),
    val year: Int = 0,
    val trackList: List<Track> = emptyList(),
    val artists: List<Credit> = emptyList(),
    val dataQuality: String = "",
    val videos: List<Video> = emptyList()
)
