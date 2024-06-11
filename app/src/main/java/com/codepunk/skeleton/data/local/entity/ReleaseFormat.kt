package com.codepunk.skeleton.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "release_format"
)
data class ReleaseFormat(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "format_id")
    val formatId: Long = 0L,
    val name: String = "",
    val quantity: Int = 0,
    val text: String? = null
)
