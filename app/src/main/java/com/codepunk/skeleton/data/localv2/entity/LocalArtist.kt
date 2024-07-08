package com.codepunk.skeleton.data.localv2.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "artist",
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
data class LocalArtist(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "artist_id")
    val artistId: Long = 0L,
    @ColumnInfo(name = "resource_id")
    val resourceId: Long = 0L,
    @Suppress("SpellCheckingInspection")
    @ColumnInfo(name = "realname")
    val realName: String? = null,
    val name: String = "",
    val profile: String = "",
    @ColumnInfo(name = "releases_url")
    val releasesUrl: String = "",
)
