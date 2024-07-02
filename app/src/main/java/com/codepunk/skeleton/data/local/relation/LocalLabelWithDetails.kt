package com.codepunk.skeleton.data.local.relation

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.codepunk.skeleton.data.local.entity.LocalImage
import com.codepunk.skeleton.data.local.entity.LocalLabel
import com.codepunk.skeleton.data.local.entity.LocalLabelDetail
import com.codepunk.skeleton.data.local.entity.LocalLabelRelationship

data class LocalLabelWithDetails(
    @Embedded
    val label: LocalLabel = LocalLabel(),
    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(
            value = LocalLabelImageCrossRef::class,
            parentColumn = "label_id",
            entityColumn = "image_id"
        )
    )
    val images: List<LocalImage> = emptyList(),
    @Relation(
        parentColumn = "id",
        entityColumn = "label_id"
    )
    val details: List<LocalLabelDetail> = emptyList(),
    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(
            value = LocalLabelSubLabelCrossRef::class,
            parentColumn = "label_id",
            entityColumn = "relationship_id"
        )
    )
    val subLabels: List<LocalLabelRelationship> = emptyList()
)
