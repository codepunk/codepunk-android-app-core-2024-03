package com.codepunk.skeleton.data.remotev2.entity

import java.math.BigDecimal

sealed interface RemoteProduct : RemoteResource {
    val title: String
    val genres: List<String>
    val styles: List<String>
    val year: Int
    val numForSale: Int
    val lowestPrice: BigDecimal?
    val artists: List<RemoteCreditReference>
    val trackList: List<RemoteTrack>
    val videos: List<RemoteVideo>
}
