package com.codepunk.skeleton.data.local.relation

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.codepunk.skeleton.data.local.entity.LocalCredit
import com.codepunk.skeleton.data.local.entity.LocalFormat
import com.codepunk.skeleton.data.local.entity.LocalIdentifier
import com.codepunk.skeleton.data.local.entity.LocalImage
import com.codepunk.skeleton.data.local.entity.LocalRelatedLabel
import com.codepunk.skeleton.data.local.entity.LocalRelease
import com.codepunk.skeleton.data.local.entity.LocalResourceDetail
import com.codepunk.skeleton.data.local.entity.LocalTrack
import com.codepunk.skeleton.data.local.entity.LocalVideo

data class LocalReleaseWithDetails(
    @Embedded
    val release: LocalRelease = LocalRelease(),
    @Relation(
        parentColumn = "resource_id",
        entityColumn = "image_id",
        associateBy = Junction(LocalResourceImageCrossRef::class)
    )
    val images: List<LocalImage> = emptyList(),
    @Relation(
        parentColumn = "resource_id",
        entityColumn = "resource_id"
    )
    val details: List<LocalResourceDetail> = emptyList(),
    @Relation(
        entity = LocalTrack::class,
        parentColumn = "resource_id",
        entityColumn = "track_id",
        associateBy = Junction(
            value = LocalResourceTrackCrossRef::class,
            parentColumn = "resource_id",
            entityColumn = "track_id"
        )
    )
    val trackList: List<LocalTrackWithDetails> = emptyList(),
    @Relation(
        parentColumn = "resource_id",
        entityColumn = "credit_id",
        associateBy = Junction(LocalResourceCreditCrossRef::class)
    )
    val credits: List<LocalCredit> = emptyList(),
    @Relation(
        parentColumn = "resource_id",
        entityColumn = "video_id",
        associateBy = Junction(LocalResourceVideoCrossRef::class)
    )
    val videos: List<LocalVideo> = emptyList(),
    @Relation(
        parentColumn = "resource_id",
        entityColumn = "resource_id"
    )
    val relatedLabels: List<LocalRelatedLabel> = emptyList(),
    @Relation(
        entity = LocalFormat::class,
        parentColumn = "release_id",
        entityColumn = "format_id",
        associateBy = Junction(
            value = LocalReleaseFormatCrossRef::class,
            parentColumn = "release_id",
            entityColumn = "format_id"
        )
    )
    val formats: List<LocalFormatWithDetails> = emptyList(),
    @Relation(
        parentColumn = "release_id",
        entityColumn = "release_id"
    )
    val identifiers: List<LocalIdentifier> = emptyList()
)