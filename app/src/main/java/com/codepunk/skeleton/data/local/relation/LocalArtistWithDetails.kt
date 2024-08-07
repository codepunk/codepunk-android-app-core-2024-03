package com.codepunk.skeleton.data.local.relation

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.codepunk.skeleton.data.local.entity.LocalArtist
import com.codepunk.skeleton.data.local.entity.LocalRelatedArtist
import com.codepunk.skeleton.data.local.entity.LocalImage
import com.codepunk.skeleton.data.local.entity.LocalResourceDetail

data class LocalArtistWithDetails(
    @Embedded
    val artist: LocalArtist = LocalArtist(),
    @Relation(
        parentColumn = "resource_id",
        entityColumn = "image_id",
        associateBy = Junction(LocalResourceImageCrossRef::class)
    )
    val images: List<LocalImage> = emptyList(),
    @Relation(
        parentColumn = "resource_id",
        entityColumn = "resource_id"
    )
    val details: List<LocalResourceDetail> = emptyList(),
    @Relation(
        parentColumn = "resource_id",
        entityColumn = "resource_id"
    )
    val relatedArtists: List<LocalRelatedArtist> = emptyList()
)
