package com.codepunk.skeleton.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "release"
)
data class LocalRelease(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "release_id")
    val releaseId: Long = 0,
    val title: String = "",
    @ColumnInfo(name = "in_wantlist")
    val inWantList: Boolean = false,
    @ColumnInfo(name = "in_collection")
    val inCollection: Boolean = false,
    @ColumnInfo(name = "master_id")
    val masterId: Int? = null,
    @ColumnInfo(name = "master_url")
    val masterUrl: String? = null,
    val uri: String = "",
    val thumb: String = "",
    @ColumnInfo(name = "cover_image")
    val coverImage: String = "",
    @ColumnInfo(name = "resource_url")
    val resourceUrl: String = "",
    val country: String = "",
    val year: String = "",
    @ColumnInfo(name = "catno")
    val categoryNumber: String = "",
    @ColumnInfo(name = "format_quantity")
    val formatQuantity: Int = 0
)
