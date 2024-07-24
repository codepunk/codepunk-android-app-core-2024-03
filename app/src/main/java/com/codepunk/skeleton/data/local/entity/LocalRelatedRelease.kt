package com.codepunk.skeleton.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "related_release",
    foreignKeys = [
        ForeignKey(
            entity = LocalResource::class,
            parentColumns = ["resource_id"],
            childColumns = ["resource_id"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )
    ]
)
data class LocalRelatedRelease(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "related_release_id")
    val relatedReleaseId: Long = 0L,
    @ColumnInfo(name = "resource_id")
    val resourceId: Long = 0L,
    @ColumnInfo(name = "resource_url")
    val resourceUrl: String = "",
    val status: String = "",
    val type: String = "",
    val format: String = "",
    val label: String = "",
    val title: String = "",
    val role: String = "",
    val artist: String = "",
    val year: Int = 0,
    val thumb: String = ""
)
