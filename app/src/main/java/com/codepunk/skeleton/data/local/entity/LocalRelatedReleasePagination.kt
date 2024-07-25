package com.codepunk.skeleton.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "related_release_pagination",
    foreignKeys = [
        ForeignKey(
            entity = LocalRelatedRelease::class,
            parentColumns = ["related_release_id"],
            childColumns = ["related_release_id"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )
    ]
)
data class LocalRelatedReleasePagination(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "related_release_id")
    val relatedReleaseId: Long = 0L,
    val page: Int = 0,
    val pages: Int = 0,
    @ColumnInfo(name = "per_page")
    val perPage: Int = 0,
    val items: Int = 0,
    val firstKey: Int? = null,
    val lastKey: Int? = null,
    val prevKey: Int? = null,
    val nextKey: Int? = null,
    @ColumnInfo(name = "created_at")
    val createdAt: Long = 0L
)
