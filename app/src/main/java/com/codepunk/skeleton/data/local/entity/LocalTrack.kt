package com.codepunk.skeleton.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "track"
)
data class LocalTrack(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val position: String = "",
    val type: String = "",
    val title: String = "",
    val duration: String = ""
)
