package com.codepunk.skeleton.util.url

import android.net.Uri

data class UrlInfo(
    val urlString: String,
    val domain: Domain,
    val display: String,
    val qualifier: String = Uri.parse(urlString).lastPathSegment.orEmpty()
)
