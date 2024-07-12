package com.codepunk.skeleton.data.local.relation

import androidx.room.Embedded
import androidx.room.Relation
import com.codepunk.skeleton.data.local.entity.LocalArtist
import com.codepunk.skeleton.data.local.entity.LocalResource

data class LocalResourceAndArtist(
    @Embedded
    val resource: LocalResource = LocalResource(),
    @Relation(
        entity = LocalArtist::class,
        parentColumn = "resource_id",
        entityColumn = "resource_id"
    )
    val artistWithDetails: LocalArtistWithDetails = LocalArtistWithDetails()
)
