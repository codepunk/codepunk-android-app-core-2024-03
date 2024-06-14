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
    val name: String = "",
    @ColumnInfo(name = "resource_url")
    val resourceUrl: String = "",
    val uri: String = "",
    @ColumnInfo(name = "releases_url")
    val releasesUrl: String = "",
    val profile: String = "",
    @ColumnInfo(name = "data_quality")
    val dataQuality: String = ""
)
