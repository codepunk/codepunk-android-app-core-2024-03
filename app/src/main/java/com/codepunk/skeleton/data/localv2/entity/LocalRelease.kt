package com.codepunk.skeleton.data.localv2.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import java.math.BigDecimal

@Entity(
    tableName = "release",
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
data class LocalRelease(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "release_id")
    val releaseId: Long = 0L,
    @ColumnInfo(name = "resource_id")
    val resourceId: Long = 0L,
    val title: String = "",
    val year: Int = 0,
    @ColumnInfo(name = "num_for_sale")
    val numForSale: Int = 0,
    @ColumnInfo(name = "lowest_price")
    val lowestPrice: BigDecimal? = null,
    val status: String = "",
    @ColumnInfo(name = "artists_sort")
    val artistsSort: String = "",
    @ColumnInfo(name = "format_quantity")
    val formatQuantity: Int = 0,
    @ColumnInfo(name = "date_added")
    val dateAdded: Instant = Instant.DISTANT_PAST,
    @ColumnInfo(name = "date_changed")
    val dateChanged: Instant = Instant.DISTANT_PAST,
    @ColumnInfo(name = "master_id")
    val masterId: Long = 0L,
    @ColumnInfo(name = "master_url")
    val masterUrl: String = "",
    val country: String = "",
    val released: LocalDate = LocalDate.fromEpochDays(0),
    val notes: String = "",
    @ColumnInfo(name = "released_formatted")
    val releasedFormatted: String = "",
    val thumb: String = ""
)
