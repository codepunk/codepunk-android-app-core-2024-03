package com.codepunk.skeleton.data.localv2.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "identifier"
)
data class LocalIdentifier(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val type: String = "",
    val value: String = "",
    val description: String? = null
)
