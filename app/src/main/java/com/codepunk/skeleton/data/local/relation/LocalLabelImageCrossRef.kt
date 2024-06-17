package com.codepunk.skeleton.data.local.relation

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import com.codepunk.skeleton.data.local.entity.LocalImage
import com.codepunk.skeleton.data.local.entity.LocalLabel

@Entity(
    tableName = "label_image_cross_ref",
    primaryKeys = ["label_id", "image_id"],
    foreignKeys = [
        ForeignKey(
            entity = LocalLabel::class,
            parentColumns = ["id"],
            childColumns = ["label_id"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = LocalImage::class,
            parentColumns = ["id"],
            childColumns = ["image_id"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )
    ]
)
data class LocalLabelImageCrossRef(
    @ColumnInfo(name = "label_id")
    val labelId: Long = 0,
    @ColumnInfo(name = "image_id")
    val imageId: Long = 0,
)
