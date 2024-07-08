package com.codepunk.skeleton.data.localv2.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "video"
)
data class LocalVideo(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val title: String = "",
    val description: String = "",
    val uri: String = "",
    val duration: Int = 0,
    val embed: Boolean = false
)
