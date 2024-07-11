package com.codepunk.skeleton.data.localv2.relation

import androidx.room.Embedded
import androidx.room.Relation
import com.codepunk.skeleton.data.localv2.entity.LocalRelease
import com.codepunk.skeleton.data.localv2.entity.LocalResource

data class LocalResourceAndRelease(
    @Embedded
    val resource: LocalResource,
    @Relation(
        entity = LocalRelease::class,
        parentColumn = "resource_id",
        entityColumn = "resource_id"
    )
    val releaseWithDetails: LocalReleaseWithDetails
)
