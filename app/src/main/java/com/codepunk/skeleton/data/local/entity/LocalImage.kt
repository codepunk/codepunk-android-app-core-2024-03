package com.codepunk.skeleton.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.codepunk.skeleton.domain.type.ImageType

/*
 * TODO move type from here to LocalArtistImageCrossRef / LocalLabelImageCrossRef / LocalMasterImageCrossRef
 *  Maybe inherit each CrossRef from an interface that has entityId / imageId / imageIdx ?
 */

@Entity(
    tableName = "image",
    indices = [
        Index("image_type", "uri")
    ]
)
data class LocalImage(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    @ColumnInfo(name = "image_type")
    val type: ImageType = ImageType.PRIMARY,
    val uri: String = "",
    @ColumnInfo(name = "resource_url")
    val resourceUrl: String = "",
    val uri150: String = "",
    val width: Int = 0,
    val height: Int = 0
)