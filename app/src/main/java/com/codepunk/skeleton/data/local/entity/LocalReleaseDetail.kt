package com.codepunk.skeleton.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import com.codepunk.skeleton.data.local.type.ResourceDetailType

@Entity(
    tableName = "release_detail",
    primaryKeys = [
        "release_id",
        "detail_type",
        "detail_idx"
    ],
    foreignKeys = [
        ForeignKey(
            entity = LocalRelease::class,
            parentColumns = ["id"],
            childColumns = ["release_id"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )
    ]
)
data class LocalReleaseDetail(
    @ColumnInfo(name = "release_id")
    val releaseId: Long = 0L,
    @ColumnInfo(name = "detail_type")
    val detailType: ResourceDetailType = ResourceDetailType.FORMAT,
    @ColumnInfo(name = "detail_idx")
    val detailIdx: Int = 0,
    val detail: String = "",
)
