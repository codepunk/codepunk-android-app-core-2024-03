package com.codepunk.skeleton.data.local.relation

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import com.codepunk.skeleton.data.local.entity.LocalMaster
import com.codepunk.skeleton.data.local.entity.LocalTrack

@Entity(
    tableName = "master_track_cross_ref",
    primaryKeys = ["master_id", "track_id"],
    foreignKeys = [
        ForeignKey(
            entity = LocalMaster::class,
            parentColumns = ["id"],
            childColumns = ["master_id"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = LocalTrack::class,
            parentColumns = ["id"],
            childColumns = ["track_id"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )
    ]
)
data class LocalMasterTrackCrossRef(
    @ColumnInfo(name = "master_id")
    val masterId: Long = 0,
    @ColumnInfo(name = "track_id")
    val trackId: Long = 0,
)
