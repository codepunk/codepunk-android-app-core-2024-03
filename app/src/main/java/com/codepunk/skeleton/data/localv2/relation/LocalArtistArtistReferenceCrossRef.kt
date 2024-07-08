package com.codepunk.skeleton.data.localv2.relation

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import com.codepunk.skeleton.data.localv2.entity.LocalArtist
import com.codepunk.skeleton.data.localv2.entity.LocalArtistReference

@Entity(
    tableName = "artist_artist_reference_cross_ref",
    primaryKeys = ["artist_id", "reference_id"],
    foreignKeys = [
        ForeignKey(
            entity = LocalArtist::class,
            parentColumns = ["artist_id"],
            childColumns = ["artist_id"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = LocalArtistReference::class,
            parentColumns = ["reference_id"],
            childColumns = ["reference_id"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )
    ]
)
data class LocalArtistArtistReferenceCrossRef(
    @ColumnInfo(name = "artist_id")
    val artistId: Long,
    @ColumnInfo(name = "reference_id")
    val referenceId: Long,
    @ColumnInfo(name = "reference_idx")
    val referenceIdx: Int // TODO Where can I leverage this? By the time I've retrieved an artist, it's gone
)
