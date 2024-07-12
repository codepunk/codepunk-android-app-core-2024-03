package com.codepunk.skeleton.data.local.relation

import androidx.room.Embedded
import androidx.room.Relation
import com.codepunk.skeleton.data.local.entity.LocalRelease
import com.codepunk.skeleton.data.local.entity.LocalResource

data class LocalResourceAndRelease(
    @Embedded
    val resource: LocalResource = LocalResource(),
    @Relation(
        entity = LocalRelease::class,
        parentColumn = "resource_id",
        entityColumn = "resource_id"
    )
    val releaseWithDetails: LocalReleaseWithDetails = LocalReleaseWithDetails()
)
