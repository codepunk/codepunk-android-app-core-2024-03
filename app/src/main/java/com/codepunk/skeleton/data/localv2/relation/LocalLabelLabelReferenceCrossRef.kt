package com.codepunk.skeleton.data.localv2.relation

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import com.codepunk.skeleton.data.localv2.entity.LocalLabel
import com.codepunk.skeleton.data.localv2.entity.LocalLabelReference

@Entity(
    tableName = "label_label_reference_cross_ref",
    primaryKeys = ["label_id", "reference_id"],
    foreignKeys = [
        ForeignKey(
            entity = LocalLabel::class,
            parentColumns = ["label_id"],
            childColumns = ["label_id"],
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
data class LocalLabelLabelReferenceCrossRef(
    @ColumnInfo(name = "label_id")
    val labelId: Long,
    @ColumnInfo(name = "reference_id")
    val referenceId: Long,
    @ColumnInfo(name = "reference_idx")
    val referenceIdx: Int // TODO Where can I leverage this? By the time I've retrieved a label, it's gone
)
