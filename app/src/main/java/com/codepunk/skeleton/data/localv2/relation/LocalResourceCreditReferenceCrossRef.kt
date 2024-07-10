package com.codepunk.skeleton.data.localv2.relation

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import com.codepunk.skeleton.data.localv2.entity.LocalCreditReference
import com.codepunk.skeleton.data.localv2.entity.LocalResource

@Entity(
    tableName = "resource_credit_reference_cross_ref",
    primaryKeys = ["resource_id", "reference_id"],
    foreignKeys = [
        ForeignKey(
            entity = LocalResource::class,
            parentColumns = ["resource_id"],
            childColumns = ["resource_id"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = LocalCreditReference::class,
            parentColumns = ["reference_id"],
            childColumns = ["reference_id"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )
    ]
)
data class LocalResourceCreditReferenceCrossRef(
    @ColumnInfo(name = "resource_id")
    val resourceId: Long,
    @ColumnInfo(name = "reference_id")
    val referenceId: Long,
    @ColumnInfo(name = "reference_idx")
    val referenceIdx: Int
)
