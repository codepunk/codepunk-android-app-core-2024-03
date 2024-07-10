package com.codepunk.skeleton.data.localv2.relation

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.codepunk.skeleton.data.localv2.entity.LocalCreditReference
import com.codepunk.skeleton.data.localv2.entity.LocalTrack

data class LocalTrackWithDetails(
    @Embedded
    val track: LocalTrack,
    @Relation(
        parentColumn = "track_id",
        entityColumn = "reference_id",
        associateBy = Junction(LocalTrackCreditReferenceCrossRef::class)
    )
    val extraArtists: List<LocalCreditReference>? = null,
)
