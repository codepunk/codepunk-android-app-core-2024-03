package com.codepunk.skeleton.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "related_artist",
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
data class LocalRelatedArtist(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "related_artist_id")
    val relatedArtistId: Long = 0L,
    @ColumnInfo(name = "resource_id")
    val resourceId: Long = 0L,
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
