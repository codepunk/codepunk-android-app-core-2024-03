package com.codepunk.skeleton.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "format",
    indices = [
        Index("release_id")
    ],
    foreignKeys = [
        ForeignKey(
            entity = LocalRelease::class,
            parentColumns = ["release_id"],
            childColumns = ["release_id"],
            onUpdate = ForeignKey.CASCADE,
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class LocalFormat(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "format_id")
    val formatId: Long = 0L,
    @ColumnInfo(name = "release_id")
    val releaseId: Long = 0L,
    @ColumnInfo(name = "format_idx")
    val formatIdx: Int = 0,
    val name: String = "",
    val quantity: Int = 0,
    val text: String? = null
)
