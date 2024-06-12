package com.codepunk.skeleton.data.local.relation

import androidx.room.Embedded
import androidx.room.Relation
import com.codepunk.skeleton.data.local.entity.LocalReleaseFormat
import com.codepunk.skeleton.data.local.entity.LocalReleaseFormatDescription

data class LocalReleaseFormatWithDescriptions(
    @Embedded
    val releaseFormat: LocalReleaseFormat = LocalReleaseFormat(),
    @Relation(
        parentColumn = "id",
        entityColumn = "format_id"
    )
    val descriptions: List<LocalReleaseFormatDescription> = emptyList()
)
