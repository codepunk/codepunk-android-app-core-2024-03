package com.codepunk.skeleton.data.localv2.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "label",
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
data class LocalLabel(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "label_id")
    val labelId: Long = 0L,
    @ColumnInfo(name = "resource_id")
    val resourceId: Long = 0L,
    val name: String = "",
    val profile: String = "",
    @ColumnInfo(name = "releases_url")
    val releasesUrl: String = "",
    @ColumnInfo(name = "contact_info")
    val contactInfo: String = ""
)
