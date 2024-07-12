package com.codepunk.skeleton.data.local.relation

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import com.codepunk.skeleton.data.local.entity.LocalResource
import com.codepunk.skeleton.data.local.entity.LocalVideo

@Entity(
    tableName = "resource_video_cross_ref",
    primaryKeys = ["resource_id", "video_id"],
    indices = [
        Index("resource_id"),
        Index("video_id")
    ],
    foreignKeys = [
        ForeignKey(
            entity = LocalResource::class,
            parentColumns = ["resource_id"],
            childColumns = ["resource_id"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = LocalVideo::class,
            parentColumns = ["video_id"],
            childColumns = ["video_id"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )
    ]
)
data class LocalResourceVideoCrossRef(
    @ColumnInfo(name = "resource_id")
    val resourceId: Long,
    @ColumnInfo(name = "video_id")
    val videoId: Long
)
