package com.codepunk.skeleton.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "release"
)
data class LocalRelease(
    @PrimaryKey(autoGenerate = false)
    val id: Long = 0L,
    val country: String = "",
    @Embedded(prefix = "master_")
    val master: LocalMaster = LocalMaster(), // TODO Can this be null?
    @ColumnInfo(name = "master_url")
    val masterUrl: String = "",
    @ColumnInfo(name = "resource_url")
    val resourceUrl: String = "",
    val uri: String = "",
    @Suppress("SpellCheckingInspection")
    @ColumnInfo(name = "catno")
    val catNo: String = "",
    val title: String = "",
    val thumb: String = "",
    @ColumnInfo(name = "cover_image")
    val coverImage: String = "",
    @ColumnInfo(name = "format_quantity")
    val formatQuantity: Int = 0
)
