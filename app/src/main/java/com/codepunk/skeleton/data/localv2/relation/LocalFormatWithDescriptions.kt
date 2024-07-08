package com.codepunk.skeleton.data.localv2.relation

import androidx.room.Embedded
import androidx.room.Relation
import com.codepunk.skeleton.data.localv2.entity.LocalFormat
import com.codepunk.skeleton.data.localv2.entity.LocalFormatDescription

data class LocalFormatWithDescriptions(
    @Embedded
    val format: LocalFormat,
    @Relation(
        parentColumn = "format_id",
        entityColumn = "format_id"
    )
    val descriptions: List<LocalFormatDescription>
)
