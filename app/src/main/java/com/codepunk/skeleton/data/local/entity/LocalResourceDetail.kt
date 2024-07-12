package com.codepunk.skeleton.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(
    tableName = "resource_detail",
    primaryKeys = [
        "resource_id",
        "detail_type",
        "detail_idx"
    ],
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
data class LocalResourceDetail(
    @ColumnInfo(name = "resource_id")
    val resourceId: Long = 0L,
    @ColumnInfo(name = "detail_type")
    val detailType: DetailType = DetailType.URL,
    @ColumnInfo(name = "detail_idx")
    val detailIdx: Int = 0,
    val detail: String = ""
) {

    // region Nested & inner classes

    enum class DetailType {
        BARCODE,
        FORMAT,
        GENRE,
        LABEL,
        NAME_VARIATION,
        STYLE,
        URL
    }

    // endregion Nested & inner classes

}
