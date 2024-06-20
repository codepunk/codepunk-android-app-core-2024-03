package com.codepunk.skeleton.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "credit"
)
data class LocalCredit(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val artistId: Long = 0,
    val name: String = "",
    val anv: String = "",
    val join: String = "",
    val role: String = "",
    val tracks: String = "",
    @ColumnInfo(name = "resource_url")
    val resourceUrl: String = "",
    @ColumnInfo(name = "thumbnail_url")
    val thumbnailUrl: String = ""
)
