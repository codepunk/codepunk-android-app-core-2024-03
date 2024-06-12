package com.codepunk.skeleton.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(
    tableName = "release_format_description",
    primaryKeys = ["format_id", "description_idx"],
    foreignKeys = [
        ForeignKey(
            entity = LocalReleaseFormat::class,
            parentColumns = ["id"],
            childColumns = ["format_id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class LocalReleaseFormatDescription(
    @ColumnInfo(name = "format_id")
    val formatId: Long = 0,
    @ColumnInfo(name = "description_idx")
    val descriptionIdx: Int = 0,
    val description: String = ""
)
