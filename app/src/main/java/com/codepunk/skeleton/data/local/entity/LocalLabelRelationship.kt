package com.codepunk.skeleton.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(
    tableName = "label_relationship",
    primaryKeys = [
        "parent_id",
        "relationship_idx"
    ],
    foreignKeys = [
        ForeignKey(
            entity = LocalLabel::class,
            parentColumns = ["id"],
            childColumns = ["parent_id"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )
    ]
)
data class LocalLabelRelationship(
    @ColumnInfo(name = "parent_id")
    val parentId: Long = 0L,
    @ColumnInfo(name = "relationship_idx")
    val relationshipIdx: Int = 0,
    @ColumnInfo(name = "child_id")
    val childId: Long = 0L,
    val name: String = "",
    @ColumnInfo(name = "resource_url")
    val resourceUrl: String = ""
)
