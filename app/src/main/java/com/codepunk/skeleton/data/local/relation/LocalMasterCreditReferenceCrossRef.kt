package com.codepunk.skeleton.data.local.relation

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import com.codepunk.skeleton.data.local.entity.LocalCredit
import com.codepunk.skeleton.data.local.entity.LocalMaster

@Entity(
    tableName = "master_credit_reference_cross_ref",
    primaryKeys = ["master_id", "reference_id"],
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
            childColumns = ["reference_id"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )
    ]
)
data class LocalMasterCreditReferenceCrossRef(
    @ColumnInfo(name = "master_id")
    val masterId: Long = 0L,
    @ColumnInfo(name = "reference_id")
    val referenceId: Long = 0L,
)
