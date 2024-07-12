package com.codepunk.skeleton.data.local.relation

import androidx.room.Embedded
import androidx.room.Relation
import com.codepunk.skeleton.data.local.entity.LocalFormat
import com.codepunk.skeleton.data.local.entity.LocalFormatDetail

data class LocalFormatWithDetails(
    @Embedded
    val format: LocalFormat = LocalFormat(),
    @Relation(
        parentColumn = "format_id",
        entityColumn = "format_id"
    )
    val details: List<LocalFormatDetail> = emptyList()
)
