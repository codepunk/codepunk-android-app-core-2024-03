package com.codepunk.skeleton.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(
    tableName = "release_format_description",
    primaryKeys = ["format_id", "description"]
)
data class ReleaseFormatDescription(
    @ColumnInfo(name = "format_id")
    val formatId: Long = 0L,
    val description: String = ""
)
