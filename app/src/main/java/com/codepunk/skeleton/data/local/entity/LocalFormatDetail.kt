package com.codepunk.skeleton.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(
    tableName = "format_detail",
    primaryKeys = ["format_id", "detail_idx"],
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
data class LocalFormatDetail(
    @ColumnInfo(name = "format_id")
    val formatId: Long = 0L,
    @ColumnInfo(name = "detail_idx")
    val detailIdx: Int = 0,
    val detail: String = ""
) {

    // region Nested & inner classes

    enum class Type {
        DESCRIPTION
    }

    // endregion Nested & inner classes

}
