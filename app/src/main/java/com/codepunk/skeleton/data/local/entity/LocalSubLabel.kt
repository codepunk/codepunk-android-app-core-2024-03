package com.codepunk.skeleton.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey

@Suppress("SpellCheckingInspection")
@Entity(
    tableName = "label_sublabel",
    primaryKeys = [
        "parent_id",
        "sublabel_idx"
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
data class LocalSubLabel(
    @ColumnInfo(name = "parent_id")
    val parentId: Long = 0L,
    @ColumnInfo(name = "sublabel_idx")
    val subLabelIdx: Int = 0,
    @ColumnInfo(name = "child_id")
    val childId: Long = 0L,
    val name: String = "",
    @ColumnInfo(name = "resource_url")
    val resourceUrl: String = ""
)
