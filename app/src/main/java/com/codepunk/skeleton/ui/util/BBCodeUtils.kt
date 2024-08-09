package com.codepunk.skeleton.ui.util

import android.net.Uri

private val TAG = Regex("\\[.*?]")
private val ARTIST_BY_NAME = Regex("^\\[a=(.*?)]$")
private val ARTIST_BY_ID = Regex("^\\[a(.\\d*?)]$")
private val LABEL_BY_NAME = Regex("^\\[l=(.*?)]$")
private val LABEL_BY_ID = Regex("^\\[l(.\\d*?)]$")
private const val URI_BASE = "/discogs.codepunk"

fun String.updateLinks(): String = mutableListOf<String>().apply {
    var prevResult: MatchResult? = null
    var nextResult = TAG.find(this@updateLinks)
    do {
        val startIndex = prevResult?.run { range.last + 1 } ?: 0
        val endIndex = nextResult?.run { range.first } ?: length
        if (endIndex > startIndex) add(substring(startIndex, endIndex))
        prevResult = nextResult
        nextResult = nextResult?.run {
            add(substring(range))
            next()
        }
    } while (prevResult != null)
}.joinToString(separator = "") { substring ->
    ARTIST_BY_NAME.matchEntire(substring)?.let { matchResult ->
        val artistName = matchResult.groups[1]?.value.orEmpty()
        val url = buildUrlString("artist", "name", artistName)
        "[url=$url]$artistName[/url]"
    } ?: ARTIST_BY_ID.matchEntire(substring)?.let { matchResult ->
        val artistId = matchResult.groups[1]?.value.orEmpty()
        val url = buildUrlString("artist", "id", artistId)
        "[url=$url]$artistId[/url]"
    } ?: LABEL_BY_NAME.matchEntire(substring)?.let { matchResult ->
        val labelName = matchResult.groups[1]?.value.orEmpty()
        val url = buildUrlString("label", "name", labelName)
        "[url=$url]$labelName[/url]"
    } ?: LABEL_BY_ID.matchEntire(substring)?.let { matchResult ->
        val labelId = matchResult.groups[1]?.value.orEmpty()
        val url = buildUrlString("label", "id", labelId)
        "[url=$url]$labelId[/url]"
    } ?: substring
}

private fun buildUrlString(path: String, subPath: String, value: String): String =
    Uri.parse(URI_BASE)
        .buildUpon()
        .appendPath(path)
        .appendPath(subPath)
        .appendPath(value)
        .build()
        .toString()