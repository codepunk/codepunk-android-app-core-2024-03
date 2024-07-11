package com.codepunk.skeleton.domain.model

import java.math.BigDecimal

sealed interface Product : Resource {
    val title: String
    val genres: List<String>
    val styles: List<String>
    val year: Int
    val numForSale: Int
    val lowestPrice: BigDecimal?
    val artists: List<CreditReference>
    val trackList: List<Track>
    val videos: List<Video>
}
