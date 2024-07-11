package com.codepunk.skeleton.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(
    tableName = "format_description",
    primaryKeys = ["format_id", "description_idx"],
    foreignKeys = [
        ForeignKey(
            entity = LocalFormat::class,
            parentColumns = ["format_id"],
            childColumns = ["format_id"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )
    ]
)
data class LocalFormatDescription(
    @ColumnInfo(name = "format_id")
    val formatId: Long = 0L,
    @ColumnInfo(name = "description_idx")
    val descriptionIdx: Int = 0,
    val description: String = ""
)
