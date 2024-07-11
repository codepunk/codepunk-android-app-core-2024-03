package com.codepunk.skeleton.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "label_reference"
)
data class LocalLabelReference(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "reference_id")
    val referenceId: Long = 0L,
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
