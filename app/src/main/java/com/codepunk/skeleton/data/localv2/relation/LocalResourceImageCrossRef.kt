package com.codepunk.skeleton.data.localv2.relation

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import com.codepunk.skeleton.data.localv2.entity.LocalImage
import com.codepunk.skeleton.data.localv2.entity.LocalResource

@Entity(
    tableName = "resource_image_cross_ref",
    primaryKeys = ["resource_id", "image_id"],
    foreignKeys = [
        ForeignKey(
            entity = LocalResource::class,
            parentColumns = ["resource_id"],
            childColumns = ["resource_id"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = LocalImage::class,
            parentColumns = ["image_id"],
            childColumns = ["image_id"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )
    ]
)
data class LocalResourceImageCrossRef(
    @ColumnInfo(name = "resource_id")
    val resourceId: Long,
    @ColumnInfo(name = "image_id")
    val imageId: Long,
    @ColumnInfo(name = "image_idx")
    val imageIdx: Int
)
