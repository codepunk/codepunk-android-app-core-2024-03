package com.codepunk.skeleton.data.local.entity

import androidx.room.ColumnInfo

data class LocalSearchResultMaster(
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
    override val resourceUrl: String = "",
    val country: String? = null,
    val year: Int? = null,
    /* TODO detail table
    val format: List<String>? = null,
    val label: List<String>? = null,
    val genre: List<String>? = null,
    val style: List<String>? = null,
    val barcode: List<String>? = null,
     */
    @Suppress("SpellCheckingInspection")
    @ColumnInfo(name = "catno")
    val catNo: String? = null
) : LocalSearchResult(ResultType.MASTER)
