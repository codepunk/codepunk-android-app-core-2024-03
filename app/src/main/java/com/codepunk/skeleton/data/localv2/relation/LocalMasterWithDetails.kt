package com.codepunk.skeleton.data.localv2.relation

import androidx.room.Embedded
import androidx.room.Ignore
import androidx.room.Junction
import androidx.room.Relation
import com.codepunk.skeleton.data.localv2.entity.LocalCreditReference
import com.codepunk.skeleton.data.localv2.entity.LocalImage
import com.codepunk.skeleton.data.localv2.entity.LocalMaster
import com.codepunk.skeleton.data.localv2.entity.LocalResourceDetail
import com.codepunk.skeleton.data.localv2.entity.LocalTrack
import com.codepunk.skeleton.data.localv2.entity.LocalVideo

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
        entityColumn = "reference_id",
        associateBy = Junction(LocalResourceCreditReferenceCrossRef::class)
    )
    val artists: List<LocalCreditReference>,
    @Relation(
        parentColumn = "resource_id",
        entityColumn = "video_id",
        associateBy = Junction(LocalResourceVideoCrossRef::class)
    )
    val videos: List<LocalVideo>
) {

    // region Constructors

    constructor(
        master: LocalMaster,
        images: List<LocalImage>,
        details: List<LocalResourceDetail>,
        artists: List<LocalCreditReference>,
        videos: List<LocalVideo>
    ) : this(
        master = master,
        images = images,
        details = details,
        trackList = emptyList(),
        artists = artists,
        videos = videos,
    )

    // endregion Constructors

}
