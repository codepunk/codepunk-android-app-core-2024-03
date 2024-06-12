package com.codepunk.skeleton.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import com.codepunk.skeleton.data.local.DetailType

@Entity(
    tableName = "master_detail",
    primaryKeys = ["master_id", "detail_type", "detail_idx"],
    foreignKeys = [
        ForeignKey(
            entity = LocalMaster::class,
            parentColumns = ["id"],
            childColumns = ["master_id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class LocalMasterDetail(
    @ColumnInfo(name = "master_id")
    val masterId: Long = 0,
    @ColumnInfo(name = "detail_type")
    val detailType: DetailType = DetailType.FORMAT,
    @ColumnInfo(name = "detail_idx")
    val detailIdx: Int = 0,
    val detail: String = ""
)
