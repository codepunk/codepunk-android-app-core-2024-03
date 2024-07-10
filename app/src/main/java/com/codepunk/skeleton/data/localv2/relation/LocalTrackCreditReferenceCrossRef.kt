package com.codepunk.skeleton.data.localv2.relation

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import com.codepunk.skeleton.data.localv2.entity.LocalCreditReference
import com.codepunk.skeleton.data.localv2.entity.LocalTrack

@Entity(
    tableName = "track_credit_reference_cross_ref",
    primaryKeys = ["track_id", "reference_id"],
    foreignKeys = [
        ForeignKey(
            entity = LocalTrack::class,
            parentColumns = ["track_id"],
            childColumns = ["track_id"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = LocalCreditReference::class,
            parentColumns = ["reference_id"],
            childColumns = ["reference_id"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )
    ]
)
data class LocalTrackCreditReferenceCrossRef(
    @ColumnInfo(name = "track_id")
    val trackId: Long,
    @ColumnInfo(name = "reference_id")
    val referenceId: Long,
    @ColumnInfo(name = "reference_idx")
    val referenceIdx: Int
)
