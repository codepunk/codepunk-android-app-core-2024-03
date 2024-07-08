package com.codepunk.skeleton.data.localv2.relation

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.codepunk.skeleton.data.localv2.entity.LocalArtist
import com.codepunk.skeleton.data.localv2.entity.LocalArtistReference
import com.codepunk.skeleton.data.localv2.entity.LocalImage
import com.codepunk.skeleton.data.localv2.entity.LocalResourceDetail

data class LocalArtistWithDetails(
    @Embedded
    val artist: LocalArtist,
    @Relation(
        parentColumn = "resource_id",
        entityColumn = "image_id",
        associateBy = Junction(LocalResourceImageCrossRef::class)
    )
    val images: List<LocalImage>,
    @Relation(
        parentColumn = "resource_id",
        entityColumn = "resource_id"
    )
    val details: List<LocalResourceDetail>,
    @Relation(
        parentColumn = "artist_id",
        entityColumn = "reference_id",
        associateBy = Junction(LocalArtistArtistReferenceCrossRef::class)
    )
    val artistRefs: List<LocalArtistReference>
)
