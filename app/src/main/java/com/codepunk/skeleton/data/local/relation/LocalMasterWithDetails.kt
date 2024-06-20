package com.codepunk.skeleton.data.local.relation

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.codepunk.skeleton.data.local.entity.LocalMaster
import com.codepunk.skeleton.data.local.entity.LocalCredit
import com.codepunk.skeleton.data.local.entity.LocalImage
import com.codepunk.skeleton.data.local.entity.LocalMasterDetail
import com.codepunk.skeleton.data.local.entity.LocalTrack
import com.codepunk.skeleton.data.local.entity.LocalVideo

data class LocalMasterWithDetails(
    @Embedded
    val master: LocalMaster = LocalMaster(),
    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(
            value = LocalMasterImageCrossRef::class,
            parentColumn = "master_id",
            entityColumn = "image_id"
        )
    )
    val images: List<LocalImage> = emptyList(),
    @Relation(
        parentColumn = "id",
        entityColumn = "master_id"
    )
    val details: List<LocalMasterDetail> = emptyList(),
    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(
            value = LocalMasterTrackCrossRef::class,
            parentColumn = "master_id",
            entityColumn = "track_id"
        )
    )
    val trackList: List<LocalTrack> = emptyList(),
    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(
            value = LocalMasterCreditCrossRef::class,
            parentColumn = "master_id",
            entityColumn = "credit_id"
        )
    )
    val artists: List<LocalCredit> = emptyList(),
    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(
            value = LocalMasterVideoCrossRef::class,
            parentColumn = "master_id",
            entityColumn = "video_id"
        )
    )
    val videos: List<LocalVideo> = emptyList()
)
