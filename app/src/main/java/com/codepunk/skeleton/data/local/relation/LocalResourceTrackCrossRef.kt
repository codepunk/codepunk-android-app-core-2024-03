package com.codepunk.skeleton.data.local.relation

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import com.codepunk.skeleton.data.local.entity.LocalResource
import com.codepunk.skeleton.data.local.entity.LocalTrack

@Entity(
    tableName = "resource_track_cross_ref",
    primaryKeys = ["resource_id", "track_id"],
    indices = [
        Index("resource_id"),
        Index("track_id")
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
            entity = LocalTrack::class,
            parentColumns = ["track_id"],
            childColumns = ["track_id"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )
    ]
)
data class LocalResourceTrackCrossRef(
    @ColumnInfo(name = "resource_id")
    val resourceId: Long,
    @ColumnInfo(name = "track_id")
    val trackId: Long
)
