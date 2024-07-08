package com.codepunk.skeleton.data.localv2.relation

import androidx.room.Embedded
import androidx.room.Relation
import com.codepunk.skeleton.data.localv2.entity.LocalLabel
import com.codepunk.skeleton.data.localv2.entity.LocalResource

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
