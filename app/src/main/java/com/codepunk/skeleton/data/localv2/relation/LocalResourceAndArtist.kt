package com.codepunk.skeleton.data.localv2.relation

import androidx.room.Embedded
import androidx.room.Relation
import com.codepunk.skeleton.data.localv2.entity.LocalArtist
import com.codepunk.skeleton.data.localv2.entity.LocalResource

data class LocalResourceAndArtist(
    @Embedded
    val resource: LocalResource,
    @Relation(
        entity = LocalArtist::class,
        parentColumn = "resource_id",
        entityColumn = "resource_id"
    )
    val artistWithDetails: LocalArtistWithDetails
)
