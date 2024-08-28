package com.codepunk.skeleton.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "format"
)
data class LocalFormat(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "format_id")
    val formatId: Long = 0L,
    @ColumnInfo(name = "format_idx")
    val formatIdx: Int = 0,
    val name: String = "",
    val quantity: Int = 0,
    val text: String? = null
)
