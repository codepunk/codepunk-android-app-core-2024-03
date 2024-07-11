package com.codepunk.skeleton.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.codepunk.skeleton.data.local.type.LabelReferenceType

@Entity(
    tableName = "label_reference"
)
data class LocalLabelReference(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "reference_id")
    val referenceId: Long = 0L,
    @ColumnInfo(name = "reference_type")
    val referenceType: LabelReferenceType,
    @ColumnInfo(name = "label_id")
    val labelId: Long = 0L,
    val name: String = "",
    @ColumnInfo(name = "resource_url")
    val resourceUrl: String = ""
)
