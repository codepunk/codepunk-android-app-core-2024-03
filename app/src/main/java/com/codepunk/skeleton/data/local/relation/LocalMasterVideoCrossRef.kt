package com.codepunk.skeleton.data.local.relation

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import com.codepunk.skeleton.data.local.entity.LocalMaster
import com.codepunk.skeleton.data.local.entity.LocalVideo

@Entity(
    tableName = "master_video_cross_ref",
    primaryKeys = ["master_id", "video_id"],
    foreignKeys = [
        ForeignKey(
            entity = LocalMaster::class,
            parentColumns = ["id"],
            childColumns = ["master_id"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = LocalVideo::class,
            parentColumns = ["id"],
            childColumns = ["video_id"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )
    ]
)
data class LocalMasterVideoCrossRef(
    @ColumnInfo(name = "master_id")
    val masterId: Long = 0,
    @ColumnInfo(name = "video_id")
    val videoId: Long = 0,
)
