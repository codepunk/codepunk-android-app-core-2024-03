package com.codepunk.skeleton.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import com.codepunk.skeleton.data.local.type.ResourceDetailType

@Entity(
    tableName = "label_detail",
    primaryKeys = [
        "label_id",
        "detail_type",
        "detail_idx"
    ],
    foreignKeys = [
        ForeignKey(
            entity = LocalLabel::class,
            parentColumns = ["id"],
            childColumns = ["label_id"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )
    ]
)
data class LocalLabelDetail(
    @ColumnInfo(name = "label_id")
    val labelId: Long = 0L,
    @ColumnInfo(name = "detail_type")
    val detailType: ResourceDetailType = ResourceDetailType.URL,
    @ColumnInfo(name = "detail_idx")
    val detailIdx: Int = 0,
    val detail: String = ""
)
