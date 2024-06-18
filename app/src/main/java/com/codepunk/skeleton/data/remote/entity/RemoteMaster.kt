package com.codepunk.skeleton.data.remote.entity

import androidx.room.ColumnInfo
import com.codepunk.skeleton.data.remote.serializer.BigDecimalSerializer
import com.codepunk.skeleton.data.remote.serializer.ElapsedTimeSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.math.BigDecimal
import kotlin.time.Duration

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
    val lowestPrice: BigDecimal = 0.toBigDecimal(),
    val images: List<RemoteImage> = emptyList(),
    val genres: List<String> = emptyList(),
    val styles: List<String> = emptyList(),
    val year: Int = 0,
    @Suppress("SpellCheckingInspection")
    @SerialName("tracklist")
    val trackList: List<Track> = emptyList(),
    val artists: List<Artist> = emptyList(),
    @SerialName("data_quality")
    val dataQuality: String = "",
    val videos: List<Video> = emptyList()
) {
    
    // region Classes

    @Serializable
    data class Track(
        val position: String = "",
        @SerialName("type_")
        val type: String = "",
        val title: String = "",
        @Serializable(with = ElapsedTimeSerializer::class)
        val duration: Duration = Duration.ZERO
    )

    @Serializable
    data class Artist(
        val id: Long = 0,
        val name: String = "",
        val anv: String = "",
        val join: String = "",
        val role: String = "",
        val tracks: String = "",
        @SerialName("resource_url")
        val resourceUrl: String = "",
        @SerialName("thumbnail_url")
        val thumbnailUrl: String = ""
    )

    @Serializable
    data class Video(
        val title: String = "",
        val description: String = "",
        val uri: String = "",
        val duration: Int = 0,
        val embed: Boolean = false
    )
    
    // endregion Classes
    
}