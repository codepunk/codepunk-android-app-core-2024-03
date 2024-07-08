package com.codepunk.skeleton.data.local.relation

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import com.codepunk.skeleton.data.local.entity.LocalArtist
import com.codepunk.skeleton.data.local.entity.LocalImage

@Entity(
    tableName = "artist_image_cross_ref",
    primaryKeys = ["artist_id", "image_id"],
    foreignKeys = [
        ForeignKey(
            entity = LocalArtist::class,
            parentColumns = ["artist_id"],
            childColumns = ["artist_id"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = LocalImage::class,
            parentColumns = ["id"],
            childColumns = ["image_id"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )
    ]
)
data class LocalArtistImageCrossRef(
    @ColumnInfo(name = "artist_id")
    val artistId: Long = 0L,
    @ColumnInfo(name = "image_id")
    val imageId: Long = 0L,
)