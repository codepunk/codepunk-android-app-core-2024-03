package com.codepunk.skeleton.data.local.relation

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import com.codepunk.skeleton.data.local.entity.LocalArtist
import com.codepunk.skeleton.data.local.entity.LocalArtistRelationship

@Entity(
    tableName = "artist_group_cross_ref",
    primaryKeys = ["artist_id", "relationship_id"],
    foreignKeys = [
        ForeignKey(
            entity = LocalArtist::class,
            parentColumns = ["artist_id"],
            childColumns = ["artist_id"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = LocalArtistRelationship::class,
            parentColumns = ["id"],
            childColumns = ["relationship_id"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )
    ]
)
data class LocalArtistGroupCrossRef(
    @ColumnInfo(name = "artist_id")
    override val artistId: Long = 0L,
    @ColumnInfo(name = "relationship_id")
    override val relationshipId: Long = 0L,
    @ColumnInfo(name = "relationship_idx")
    override val relationshipIdx: Int = 0
) : LocalArtistRelationshipCrossRef
