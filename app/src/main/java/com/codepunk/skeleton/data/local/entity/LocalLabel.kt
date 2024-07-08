package com.codepunk.skeleton.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Note: Because parentLabel can be null, "parent_label_id" is NOT included as a Foreign Key
 */
@Entity(
    tableName = "label"
)
data class LocalLabel(
    @PrimaryKey(autoGenerate = false)
    val id: Long = 0L,
    val name: String = "",
    @ColumnInfo(name = "resource_url")
    val resourceUrl: String = "",
    val uri: String = "",
    @ColumnInfo(name = "releases_url")
    val releasesUrl: String = "",
    @ColumnInfo(name = "contact_info")
    val contactInfo: String = "",
    val profile: String = "",
    @Embedded(prefix = "parent_label_")
    val parentLabel: LocalLabelRelationship? = null,
    @ColumnInfo(name = "data_quality")
    val dataQuality: String = ""
)
