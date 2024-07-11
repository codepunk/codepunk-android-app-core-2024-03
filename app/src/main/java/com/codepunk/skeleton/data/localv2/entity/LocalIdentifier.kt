package com.codepunk.skeleton.data.localv2.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(
    tableName = "identifier",
    primaryKeys = ["release_id", "identifier_idx"],
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
data class LocalIdentifier(
    @ColumnInfo(name = "release_id")
    val releaseId: Long = 0L,
    @ColumnInfo(name = "identifier_idx")
    val identifierIdx: Int = 0,
    val type: String = "",
    val value: String = "",
    val description: String? = null
)
