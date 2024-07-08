package com.codepunk.skeleton.data.localv2.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "resource"
)
data class LocalResource(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "resource_id")
    val resourceId: Long = 0L,
    @ColumnInfo(name = "resource_url")
    val resourceUrl: String = "",
    val uri: String = "",
    val dataQuality: String = ""
)
