package com.codepunk.skeleton.data.local.relation

import androidx.room.Embedded
import androidx.room.Relation
import com.codepunk.skeleton.data.local.entity.ReleaseFormat
import com.codepunk.skeleton.data.local.entity.ReleaseFormatDescription

data class ReleaseFormatWithDescriptions(
    @Embedded
    val releaseFormat: ReleaseFormat,
    @Relation(
        parentColumn = "format_id",
        entityColumn = "format_id"
    )
    val descriptions: List<ReleaseFormatDescription> = emptyList()
)
