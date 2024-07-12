package com.codepunk.skeleton.data.local.relation

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.codepunk.skeleton.data.local.entity.LocalCredit
import com.codepunk.skeleton.data.local.entity.LocalImage
import com.codepunk.skeleton.data.local.entity.LocalMaster
import com.codepunk.skeleton.data.local.entity.LocalResourceDetail
import com.codepunk.skeleton.data.local.entity.LocalTrack
import com.codepunk.skeleton.data.local.entity.LocalVideo

data class LocalMasterWithDetails(
    @Embedded
    val master: LocalMaster,
    @Relation(
        parentColumn = "resource_id",
        entityColumn = "image_id",
        associateBy = Junction(LocalResourceImageCrossRef::class)
    )
    val images: List<LocalImage>,
    @Relation(
        parentColumn = "resource_id",
        entityColumn = "resource_id"
    )
    val details: List<LocalResourceDetail>,
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
    val trackList: List<LocalTrackWithDetails>,
    @Relation(
        parentColumn = "resource_id",
        entityColumn = "credit_id",
        associateBy = Junction(LocalResourceCreditCrossRef::class)
    )
    val artists: List<LocalCredit>,
    @Relation(
        parentColumn = "resource_id",
        entityColumn = "video_id",
        associateBy = Junction(LocalResourceVideoCrossRef::class)
    )
    val videos: List<LocalVideo>
)
