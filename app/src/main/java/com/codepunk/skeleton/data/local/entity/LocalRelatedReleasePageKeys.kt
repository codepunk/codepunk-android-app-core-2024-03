package com.codepunk.skeleton.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "related_release_page_keys",
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
data class LocalRelatedReleasePageKeys(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "related_release_id")
    val relatedReleaseId: Long = 0L,
    val prevKey: Int? = null,
    val currentPage: Int = 0,
    val nextKey: Int? = null,
    @ColumnInfo(name = "created_at")
    val createdAt: Long = System.currentTimeMillis()
)
