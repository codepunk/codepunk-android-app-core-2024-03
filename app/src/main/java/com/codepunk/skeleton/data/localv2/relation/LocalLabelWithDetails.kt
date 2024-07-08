package com.codepunk.skeleton.data.localv2.relation

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.codepunk.skeleton.data.localv2.entity.LocalImage
import com.codepunk.skeleton.data.localv2.entity.LocalLabel
import com.codepunk.skeleton.data.localv2.entity.LocalLabelReference
import com.codepunk.skeleton.data.localv2.entity.LocalResourceDetail

data class LocalLabelWithDetails(
    @Embedded
    val label: LocalLabel,
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
        parentColumn = "label_id",
        entityColumn = "reference_id",
        associateBy = Junction(LocalLabelLabelReferenceCrossRef::class)
    )
    val labelRefs: List<LocalLabelReference>
)
