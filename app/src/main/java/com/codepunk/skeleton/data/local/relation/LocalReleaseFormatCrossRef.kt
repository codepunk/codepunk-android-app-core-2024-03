package com.codepunk.skeleton.data.local.relation

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import com.codepunk.skeleton.data.local.entity.LocalFormat
import com.codepunk.skeleton.data.local.entity.LocalRelease

@Entity(
    tableName = "release_format_cross_ref",
    primaryKeys = ["release_id", "format_id"],
    indices = [
        Index("release_id"),
        Index("format_id")
    ],
    foreignKeys = [
        ForeignKey(
            entity = LocalRelease::class,
            parentColumns = ["release_id"],
            childColumns = ["release_id"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = LocalFormat::class,
            parentColumns = ["format_id"],
            childColumns = ["format_id"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )
    ]
)
data class LocalReleaseFormatCrossRef(
    @ColumnInfo(name = "release_id")
    val releaseId: Long,
    @ColumnInfo(name = "format_id")
    val formatId: Long
)