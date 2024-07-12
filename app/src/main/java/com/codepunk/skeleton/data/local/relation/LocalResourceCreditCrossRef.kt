package com.codepunk.skeleton.data.local.relation

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import com.codepunk.skeleton.data.local.entity.LocalCredit
import com.codepunk.skeleton.data.local.entity.LocalResource

@Entity(
    tableName = "resource_credit_cross_ref",
    primaryKeys = ["resource_id", "credit_id"],
    indices = [
        Index("resource_id"),
        Index("credit_id")
    ],
    foreignKeys = [
        ForeignKey(
            entity = LocalResource::class,
            parentColumns = ["resource_id"],
            childColumns = ["resource_id"],
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
data class LocalResourceCreditCrossRef(
    @ColumnInfo(name = "resource_id")
    val resourceId: Long,
    @ColumnInfo(name = "credit_id")
    val creditId: Long
)
