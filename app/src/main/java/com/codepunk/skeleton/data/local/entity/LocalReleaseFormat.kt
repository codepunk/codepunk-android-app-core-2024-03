package com.codepunk.skeleton.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "release_format"
)
data class LocalReleaseFormat(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "format_id")
    val formatId: Long = 0,
    @ColumnInfo(name = "format_idx")
    val formatIdx: Int = 0,
    @ColumnInfo(name = "release_id")
    val releaseId: Long = 0,
    val name: String = "",
    @ColumnInfo(name = "qty")
    val quantity: Int = 0,
    val text: String = ""
)
