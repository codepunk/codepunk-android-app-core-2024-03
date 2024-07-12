package com.codepunk.skeleton.data.local.relation

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.codepunk.skeleton.data.local.entity.LocalCredit
import com.codepunk.skeleton.data.local.entity.LocalTrack

data class LocalTrackWithDetails(
    @Embedded
    val track: LocalTrack,
    @Relation(
        parentColumn = "track_id",
        entityColumn = "credit_id",
        associateBy = Junction(LocalTrackCreditCrossRef::class)
    )
    val extraArtists: List<LocalCredit>? = null,
)
