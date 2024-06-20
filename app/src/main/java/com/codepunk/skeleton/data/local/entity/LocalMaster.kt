package com.codepunk.skeleton.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.math.BigDecimal

@Entity(
    tableName = "master"
)
data class LocalMaster(
    @PrimaryKey(autoGenerate = false)
    val id: Long = 0,
    val title: String = "",
    @ColumnInfo(name = "main_release")
    val mainRelease: Long = 0,
    @ColumnInfo(name = "most_recent_release")
    val mostRecentRelease: Long = 0,
    @ColumnInfo(name = "resource_url")
    val resourceUrl: String = "",
    val uri: String = "",
    @ColumnInfo(name = "versions_url")
    val versionsUrl: String = "",
    @ColumnInfo(name = "main_release_url")
    val mainReleaseUrl: String = "",
    @ColumnInfo(name = "most_recent_release_url")
    val mostRecentReleaseUrl: String = "",
    val numForSale: Int = 0,
    val lowestPrice: BigDecimal = BigDecimal(0),
    val year: Int = 0,
    @ColumnInfo(name = "data_quality")
    val dataQuality: String = ""
)
