package com.codepunk.skeleton.data.localv2.relation

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import com.codepunk.skeleton.data.localv2.entity.LocalCreditReference
import com.codepunk.skeleton.data.localv2.entity.LocalTrack

@Entity(
    tableName = "track_credit_cross_ref",
    primaryKeys = ["track_id", "credit_id"],
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
            parentColumns = ["credit_id"],
            childColumns = ["credit_id"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )
    ]
)
data class LocalTrackCreditCrossRef(
    @ColumnInfo(name = "track_id")
    val trackId: Long,
    @ColumnInfo(name = "credit_id")
    val creditId: Long,
    @ColumnInfo(name = "credit_idx")
    val creditIdx: Int
)
