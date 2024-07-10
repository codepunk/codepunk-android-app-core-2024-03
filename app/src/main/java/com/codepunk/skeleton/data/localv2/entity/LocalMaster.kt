package com.codepunk.skeleton.data.localv2.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.math.BigDecimal

@Entity(
    tableName = "master",
    foreignKeys = [
        ForeignKey(
            entity = LocalResource::class,
            parentColumns = ["resource_id"],
            childColumns = ["resource_id"],
            onUpdate = ForeignKey.CASCADE,
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class LocalMaster(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "master_id")
    val masterId: Long = 0L,
    @ColumnInfo(name = "resource_id")
    val resourceId: Long = 0L,
    val title: String = "",
    val year: Int = 0,
    @ColumnInfo(name = "num_for_sale")
    val numForSale: Int = 0,
    @ColumnInfo(name = "lowest_price")
    val lowestPrice: BigDecimal? = null,
    @ColumnInfo(name = "main_release")
    val mainRelease: Long = 0L,
    @ColumnInfo(name = "most_recent_release")
    val mostRecentRelease: Long = 0L,
    @ColumnInfo(name = "versions_url")
    val versionsUrl: String = "",
    @ColumnInfo(name = "main_release_url")
    val mainReleaseUrl: String = "",
    @ColumnInfo(name = "most_recent_release_url")
    val mostRecentReleaseUrl: String = ""
)
