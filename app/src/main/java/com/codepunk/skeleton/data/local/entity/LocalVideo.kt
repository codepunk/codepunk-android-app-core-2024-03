package com.codepunk.skeleton.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "video"
)
data class LocalVideo(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "video_id")
    val videoId: Long = 0L,
    val title: String = "",
    val description: String = "",
    val uri: String = "",
    val duration: Int = 0,
    val embed: Boolean = false
)
