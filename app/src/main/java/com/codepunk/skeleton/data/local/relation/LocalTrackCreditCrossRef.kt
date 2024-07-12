package com.codepunk.skeleton.data.local.relation

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import com.codepunk.skeleton.data.local.entity.LocalCredit
import com.codepunk.skeleton.data.local.entity.LocalTrack

@Entity(
    tableName = "track_credit_cross_ref",
    primaryKeys = ["track_id", "credit_id"],
    indices = [
        Index("track_id"),
        Index("credit_id")
    ],
    foreignKeys = [
        ForeignKey(
            entity = LocalTrack::class,
            parentColumns = ["track_id"],
            childColumns = ["track_id"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = LocalCredit::class,
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
    val creditId: Long
)
