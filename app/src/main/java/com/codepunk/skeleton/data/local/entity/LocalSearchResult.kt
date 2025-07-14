package com.codepunk.skeleton.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "search_result")
sealed class LocalSearchResult(
    val type: ResultType
) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "search_result_id")
    open val searchResultId: Long = 0
    open val id: Long = 0
    open val masterId: Long? = null
    open val masterUrl: String? = null
    open val uri: String = ""
    open val title: String = ""
    open val thumb: String = ""
    open val coverImage: String = ""
    open val resourceUrl: String = ""

    // TODO catno & format_quantity?

    enum class ResultType {
        ARTIST,
        LABEL,
        MASTER,
        RELEASE
    }
}
