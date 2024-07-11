package com.codepunk.skeleton.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "artist_reference"
)
data class LocalArtistReference(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "reference_id")
    val referenceId: Long = 0L,
    @ColumnInfo(name = "reference_type")
    val relationType: RelationType,
    @ColumnInfo(name = "artist_id")
    val artistId: Long = 0L,
    val name: String = "",
    @ColumnInfo(name = "resource_url")
    val resourceUrl: String = "",
    val active: Boolean? = null,
    @ColumnInfo(name = "thumbnail_url")
    val thumbnailUrl: String? = null
) {

    // region Nested & inner classes

    enum class RelationType {
        ALIAS,
        MEMBER,
        GROUP
    }

    // endregion Nested & inner classes

}
