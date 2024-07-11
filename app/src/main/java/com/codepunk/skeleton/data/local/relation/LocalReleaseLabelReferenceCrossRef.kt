package com.codepunk.skeleton.data.local.relation

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import com.codepunk.skeleton.data.local.entity.LocalLabelReference
import com.codepunk.skeleton.data.local.entity.LocalRelease

@Entity(
    tableName = "release_label_reference_cross_ref",
    primaryKeys = ["release_id", "reference_id"],
    indices = [
        Index("release_id"),
        Index("reference_id")
    ],
    foreignKeys = [
        ForeignKey(
            entity = LocalRelease::class,
            parentColumns = ["release_id"],
            childColumns = ["release_id"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = LocalLabelReference::class,
            parentColumns = ["reference_id"],
            childColumns = ["reference_id"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )
    ]
)
data class LocalReleaseLabelReferenceCrossRef(
    @ColumnInfo(name = "release_id")
    val releaseId: Long,
    @ColumnInfo(name = "reference_id")
    val referenceId: Long,
    @ColumnInfo(name = "reference_idx")
    val referenceIdx: Int // TODO Where can I leverage this? By the time I've retrieved, it's gone
)
