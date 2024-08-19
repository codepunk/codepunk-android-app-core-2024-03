package com.codepunk.skeleton.domain

import android.net.Uri
import com.codepunk.skeleton.domain.model.Artist
import com.codepunk.skeleton.domain.model.Label
import com.codepunk.skeleton.domain.model.Video

fun Artist?.orEmpty(): Artist = this ?: Artist()

fun Label?.orEmpty(): Label = this ?: Label()

fun Video.toThumbnailUrl(): String? {
    val uri = Uri.parse(this.uri)
    val hostParts = uri.host?.lowercase()?.split(".") ?: emptyList()
    return when {
        hostParts.contains("youtube") -> {
            uri.getQueryParameter("v")?.let { vid ->
                "https://img.youtube.com/vi/$vid/mqdefault.jpg"
            }
        }
        else -> null
    }
}

