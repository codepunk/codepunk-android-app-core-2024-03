package com.codepunk.skeleton.data.local.relation

import com.codepunk.skeleton.data.local.entity.LocalRelease

data class LocalReleaseWithDetails(
    val release: LocalRelease = LocalRelease(),
    val format: List<String> = emptyList(),
    val label: List<String> = emptyList(),
    val genre: List<String> = emptyList(),
    val style: List<String> = emptyList(),
    val barcode: List<String> = emptyList(),
    val formats: List<LocalReleaseFormatWithDescriptions> = emptyList()
)
