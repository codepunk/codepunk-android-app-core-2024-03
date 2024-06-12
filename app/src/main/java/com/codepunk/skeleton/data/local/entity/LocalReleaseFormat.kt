package com.codepunk.skeleton.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "release_format",
    indices = [
        Index(
            value = ["release_id", "format_idx"],
            orders = [Index.Order.ASC, Index.Order.ASC],
            unique = true
        )
    ],
    foreignKeys = [
        ForeignKey(
            entity = LocalRelease::class,
            parentColumns = ["id"],
            childColumns = ["release_id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class LocalReleaseFormat(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    @ColumnInfo(name = "format_idx")
    val formatIdx: Int = 0,
    @ColumnInfo(name = "release_id")
    val releaseId: Long = 0,
    val name: String = "",
    @ColumnInfo(name = "qty")
    val quantity: Int = 0,
    val text: String = ""
)
