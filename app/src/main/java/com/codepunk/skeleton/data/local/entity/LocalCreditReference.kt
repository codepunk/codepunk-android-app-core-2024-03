package com.codepunk.skeleton.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "credit_reference"
)
data class LocalCreditReference(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo("reference_id")
    val referenceId: Long = 0L,
    @ColumnInfo(name = "reference_type")
    val referenceType: RelationType,
    val artistId: Long = 0L,
    val name: String = "",
    val anv: String = "",
    val join: String = "",
    val role: String = "",
    val tracks: String = "",
    @ColumnInfo(name = "resource_url")
    val resourceUrl: String = "",
    @ColumnInfo(name = "thumbnail_url")
    val thumbnailUrl: String = ""
) {

    // region Nested & inner classes

    enum class RelationType {
        ARTIST,
        EXTRA_ARTIST
    }

    // endregion Nested & inner classes
}
