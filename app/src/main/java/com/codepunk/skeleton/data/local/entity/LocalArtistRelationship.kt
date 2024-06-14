package com.codepunk.skeleton.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import com.codepunk.skeleton.data.local.type.ArtistRelationshipType

@Entity(
    tableName = "artist_relationship",
    primaryKeys = [
        "parent_id",
        "relationship_type",
        "relationship_idx"
    ],
    indices = [
        Index(
            value = [
                "parent_id",
                "relationship_type",
                "child_id"
            ],
            unique = true
        )
    ],
    foreignKeys = [
        ForeignKey(
            entity = LocalArtist::class,
            parentColumns = ["id"],
            childColumns = ["parent_id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class LocalArtistRelationship(
    @ColumnInfo(name = "parent_id")
    val parentId: Long = 0L,
    @ColumnInfo(name = "relationship_type")
    val relationshipType: ArtistRelationshipType = ArtistRelationshipType.ALIAS,
    @ColumnInfo(name = "relationship_idx")
    val relationshipIdx: Int = 0,
    @ColumnInfo(name = "child_id")
    val childId: Long = 0L,
    val name: String = "",
    @ColumnInfo(name = "resource_url")
    val resourceUrl: String = "",
    val active: Boolean? = null,
    @ColumnInfo(name = "thumbnail_url")
    val thumbnailUrl: String = ""
)