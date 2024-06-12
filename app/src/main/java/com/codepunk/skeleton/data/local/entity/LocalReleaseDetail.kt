package com.codepunk.skeleton.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(
    tableName = "release_detail",
    primaryKeys = ["release_id", "detail_type", "detail_idx"],
    foreignKeys = [
        ForeignKey(
            entity = LocalRelease::class,
            parentColumns = ["id"],
            childColumns = ["release_id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class LocalReleaseDetail(
    @ColumnInfo(name = "release_id")
    val releaseId: Long = 0,
    @ColumnInfo(name = "detail_type")
    val detailType: DetailType = DetailType.FORMAT,
    @ColumnInfo(name = "detail_idx")
    val detailIdx: Int = 0,
    val detail: String = ""
) {

    // region Nested & inner classes

    enum class DetailType {
        FORMAT,
        LABEL,
        GENRE,
        STYLE,
        BARCODE
    }

    // endregion Nested & inner classes

}
