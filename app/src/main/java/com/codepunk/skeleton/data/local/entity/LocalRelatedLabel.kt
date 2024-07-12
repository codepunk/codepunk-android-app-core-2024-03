package com.codepunk.skeleton.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "related_label",
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
data class LocalRelatedLabel(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "related_label_id")
    val relatedLabelId: Long = 0L,
    @ColumnInfo(name = "resource_id")
    val resourceId: Long = 0L,
    @ColumnInfo(name = "reference_type")
    val referenceType: RelationType,
    @ColumnInfo(name = "label_id")
    val labelId: Long = 0L,
    val name: String = "",
    @ColumnInfo(name = "resource_url")
    val resourceUrl: String = ""
) {

    // region Nested & inner classes

    enum class RelationType {
        COMPANY,
        LABEL,
        PARENT_LABEL,
        SERIES,
        SUB_LABEL
    }

    // endregion Nested & inner classes

}
