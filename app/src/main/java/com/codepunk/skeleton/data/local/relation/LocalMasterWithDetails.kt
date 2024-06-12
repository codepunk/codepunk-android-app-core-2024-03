package com.codepunk.skeleton.data.local.relation

import com.codepunk.skeleton.data.local.entity.LocalMaster

data class LocalMasterWithDetails(
    val master: LocalMaster = LocalMaster(),
    val format: List<String> = emptyList(),
    val label: List<String> = emptyList(),
    val genre: List<String> = emptyList(),
    val style: List<String> = emptyList(),
    val barcode: List<String> = emptyList()
)
