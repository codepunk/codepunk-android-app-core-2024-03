package com.codepunk.skeleton.data.localv2.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.codepunk.skeleton.domain.type.ImageType

@Entity(
    tableName = "image"
)
data class LocalImage(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "image_id")
    val imageId: Long = 0L,
    @ColumnInfo(name = "image_idx")
    val imageIdx: Int = 0,
    @ColumnInfo(name = "image_type")
    val type: ImageType = ImageType.PRIMARY,
    val uri: String = "",
    @ColumnInfo(name = "resource_url")
    val resourceUrl: String = "",
    val uri150: String = "",
    val width: Int = 0,
    val height: Int = 0
)
