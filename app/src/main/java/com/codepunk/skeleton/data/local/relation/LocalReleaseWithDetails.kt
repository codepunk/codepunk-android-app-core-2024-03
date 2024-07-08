package com.codepunk.skeleton.data.local.relation

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.codepunk.skeleton.data.local.entity.LocalFormat
import com.codepunk.skeleton.data.local.entity.LocalImage
import com.codepunk.skeleton.data.local.entity.LocalMasterDetail
import com.codepunk.skeleton.data.local.entity.LocalRelease

data class LocalReleaseWithDetails(
    @Embedded
    val label: LocalRelease = LocalRelease(),
    @Relation(
        parentColumn = "id",
        entityColumn = "master_id"
    )
    val details: List<LocalMasterDetail> = emptyList(),
    @Relation(
        entity = LocalFormat::class,
        parentColumn = "id",
        entityColumn = "format_id"
    )
    val formats: List<LocalFormatWithDetails> = emptyList(),
    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(
            value = LocalArtistImageCrossRef::class,
            parentColumn = "release_id",
            entityColumn = "image_id"
        )
    )
    val images: List<LocalImage> = emptyList(),
)
