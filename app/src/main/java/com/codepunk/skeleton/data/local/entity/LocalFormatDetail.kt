package com.codepunk.skeleton.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import com.codepunk.skeleton.data.local.type.ResourceDetailType

@Entity(
    tableName = "format_detail",
    primaryKeys = [
        "format_id",
        "detail_type",
        "detail_idx"
    ],
    foreignKeys = [
        ForeignKey(
            entity = LocalRelease::class,
            parentColumns = ["id"],
            childColumns = ["format_id"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )
    ]
)
data class LocalFormatDetail(
    @ColumnInfo(name = "format_id")
    val formatId: Long = 0L,
    @ColumnInfo(name = "detail_type")
    val detailType: ResourceDetailType = ResourceDetailType.FORMAT,
    @ColumnInfo(name = "detail_idx")
    val detailIdx: Int = 0,
    val detail: String = "",
)
