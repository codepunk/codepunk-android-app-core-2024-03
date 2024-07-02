package com.codepunk.skeleton.data.local.relation

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.codepunk.skeleton.data.local.entity.LocalArtist
import com.codepunk.skeleton.data.local.entity.LocalArtistDetail
import com.codepunk.skeleton.data.local.entity.LocalArtistRelationship
import com.codepunk.skeleton.data.local.entity.LocalImage

data class LocalArtistWithDetails(
    @Embedded
    val artist: LocalArtist = LocalArtist(),
    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(
            value = LocalArtistImageCrossRef::class,
            parentColumn = "artist_id",
            entityColumn = "image_id"
        )
    )
    val images: List<LocalImage> = emptyList(),
    @Relation(
        parentColumn = "id",
        entityColumn = "artist_id"
    )
    val details: List<LocalArtistDetail> = emptyList(),
    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(
            value = LocalArtistAliasCrossRef::class,
            parentColumn = "artist_id",
            entityColumn = "relationship_id"
        )
    )
    val aliases: List<LocalArtistRelationship> = emptyList(),
    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(
            value = LocalArtistMemberCrossRef::class,
            parentColumn = "artist_id",
            entityColumn = "relationship_id"
        )
    )
    val members: List<LocalArtistRelationship> = emptyList(),
    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(
            value = LocalArtistGroupCrossRef::class,
            parentColumn = "artist_id",
            entityColumn = "relationship_id"
        )
    )
    val groups: List<LocalArtistRelationship> = emptyList(),
)
