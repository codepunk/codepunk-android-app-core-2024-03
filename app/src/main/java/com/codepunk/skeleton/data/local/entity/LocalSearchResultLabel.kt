package com.codepunk.skeleton.data.local.entity

import androidx.room.ColumnInfo

data class LocalSearchResultLabel(
    override val id: Long = 0,
    @ColumnInfo(name = "master_id")
    override val masterId: Long? = null,
    @ColumnInfo(name = "master_url")
    override val masterUrl: String? = null,
    override val uri: String = "",
    override val title: String = "",
    override val thumb: String = "",
    @ColumnInfo(name = "cover_image")
    override val coverImage: String = "",
    @ColumnInfo(name = "resource_url")
    override val resourceUrl: String = ""
) : LocalSearchResult(ResultType.LABEL)
