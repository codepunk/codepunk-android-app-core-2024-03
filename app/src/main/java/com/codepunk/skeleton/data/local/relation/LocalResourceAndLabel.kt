package com.codepunk.skeleton.data.local.relation

import androidx.room.Embedded
import androidx.room.Relation
import com.codepunk.skeleton.data.local.entity.LocalLabel
import com.codepunk.skeleton.data.local.entity.LocalResource

data class LocalResourceAndLabel(
    @Embedded
    val resource: LocalResource,
    @Relation(
        entity = LocalLabel::class,
        parentColumn = "resource_id",
        entityColumn = "resource_id"
    )
    val labelWithDetails: LocalLabelWithDetails
)
