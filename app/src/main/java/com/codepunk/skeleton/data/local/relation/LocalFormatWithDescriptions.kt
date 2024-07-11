package com.codepunk.skeleton.data.local.relation

import androidx.room.Embedded
import androidx.room.Relation
import com.codepunk.skeleton.data.local.entity.LocalFormat
import com.codepunk.skeleton.data.local.entity.LocalFormatDescription

data class LocalFormatWithDescriptions(
    @Embedded
    val format: LocalFormat,
    @Relation(
        parentColumn = "format_id",
        entityColumn = "format_id"
    )
    val descriptions: List<LocalFormatDescription>
)
