package com.codepunk.skeleton.data.localv2.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "track"
)
data class LocalTrack(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "track_id")
    val trackId: Long = 0L,
    @ColumnInfo(name = "track_marker")
    val trackMarker: Int = 0,
    @ColumnInfo(name = "parent_track_marker")
    val parentTrackMarker: Int = -1,
    val position: String = "",
    val type: String = "",
    val title: String = "",
    val duration: String = ""
)
