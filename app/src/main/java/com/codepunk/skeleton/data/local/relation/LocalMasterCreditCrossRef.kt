package com.codepunk.skeleton.data.local.relation

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import com.codepunk.skeleton.data.local.entity.LocalCredit
import com.codepunk.skeleton.data.local.entity.LocalMaster

@Entity(
    tableName = "master_credit_cross_ref",
    primaryKeys = ["master_id", "credit_id"],
    foreignKeys = [
        ForeignKey(
            entity = LocalMaster::class,
            parentColumns = ["id"],
            childColumns = ["master_id"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = LocalCredit::class,
            parentColumns = ["id"],
            childColumns = ["credit_id"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )
    ]
)
data class LocalMasterCreditCrossRef(
    @ColumnInfo(name = "master_id")
    val masterId: Long = 0L,
    @ColumnInfo(name = "credit_id")
    val creditId: Long = 0L,
)
