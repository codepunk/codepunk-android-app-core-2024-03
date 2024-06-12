package com.codepunk.skeleton.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "artist"
)
data class LocalArtist(
    @PrimaryKey(autoGenerate = false)
    val id: Long = 0,
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
    val resourceUrl: String = ""
)
