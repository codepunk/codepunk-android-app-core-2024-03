package com.codepunk.skeleton.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "artist_relationship"
)
data class LocalArtistRelationship(
    @PrimaryKey
    val id: Long = 0L,
    val name: String = "",
    @ColumnInfo(name = "resource_url")
    val resourceUrl: String = "",
    val active: Boolean? = null,
    @ColumnInfo(name = "thumbnail_url")
    val thumbnailUrl: String = ""
)