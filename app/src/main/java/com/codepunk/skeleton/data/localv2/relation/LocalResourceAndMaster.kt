package com.codepunk.skeleton.data.localv2.relation

import androidx.room.Embedded
import androidx.room.Relation
import com.codepunk.skeleton.data.localv2.entity.LocalMaster
import com.codepunk.skeleton.data.localv2.entity.LocalResource

data class LocalResourceAndMaster(
    @Embedded
    val resource: LocalResource,
    @Relation(
        entity = LocalMaster::class,
        parentColumn = "resource_id",
        entityColumn = "resource_id"
    )
    val masterWithDetails: LocalMasterWithDetails
)
