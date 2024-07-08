package com.codepunk.skeleton.data.local.relation

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import com.codepunk.skeleton.data.local.entity.LocalLabel
import com.codepunk.skeleton.data.local.entity.LocalLabelRelationship

@Suppress("SpellCheckingInspection")
@Entity(
    tableName = "label_sublabel_cross_ref",
    primaryKeys = ["label_id", "relationship_id"],
    foreignKeys = [
        ForeignKey(
            entity = LocalLabel::class,
            parentColumns = ["id"],
            childColumns = ["label_id"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = LocalLabelRelationship::class,
            parentColumns = ["id"],
            childColumns = ["relationship_id"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )
    ]
)
data class LocalLabelSubLabelCrossRef(
    @ColumnInfo(name = "label_id")
    val labelId: Long = 0L,
    @ColumnInfo(name = "relationship_id")
    val relationshipId: Long = 0L,
    @ColumnInfo(name = "relationship_idx")
    val relationshipIdx: Int = 0
)
