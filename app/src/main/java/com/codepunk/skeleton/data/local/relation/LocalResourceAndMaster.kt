package com.codepunk.skeleton.data.local.relation

import androidx.room.Embedded
import androidx.room.Relation
import com.codepunk.skeleton.data.local.entity.LocalMaster
import com.codepunk.skeleton.data.local.entity.LocalResource

data class LocalResourceAndMaster(
    @Embedded
    val resource: LocalResource = LocalResource(),
    @Relation(
        entity = LocalMaster::class,
        parentColumn = "resource_id",
        entityColumn = "resource_id"
    )
    val masterWithDetails: LocalMasterWithDetails = LocalMasterWithDetails()
)
