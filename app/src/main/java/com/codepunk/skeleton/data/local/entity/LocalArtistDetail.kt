package com.codepunk.skeleton.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import com.codepunk.skeleton.data.local.type.ArtistDetailType

@Entity(
    tableName = "artist_detail",
    primaryKeys = [
        "artist_id",
        "detail_type",
        "detail_idx"
    ],
    foreignKeys = [
        ForeignKey(
            entity = LocalArtist::class,
            parentColumns = ["id"],
            childColumns = ["artist_id"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )
    ]
)
data class LocalArtistDetail(
    @ColumnInfo(name = "artist_id")
    val artistId: Long = 0L,
    @ColumnInfo(name = "detail_type")
    val detailType: ArtistDetailType = ArtistDetailType.URL,
    @ColumnInfo(name = "detail_idx")
    val detailIdx: Int = 0,
    val detail: String = "",
)