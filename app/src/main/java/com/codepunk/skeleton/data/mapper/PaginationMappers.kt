package com.codepunk.skeleton.data.mapper

import android.net.Uri
import com.codepunk.skeleton.data.local.entity.LocalRelatedReleasePagination
import com.codepunk.skeleton.data.remote.entity.RemotePagination
import kotlinx.datetime.Clock

private const val QUERY_PAGE = "page"

private fun String.getPageNumber(): Int? =
    Uri.parse(this).getQueryParameter(QUERY_PAGE)?.toIntOrNull()

fun RemotePagination.toLocalRelatedReleasePagination(
    relatedReleaseId: Long = 0L
): LocalRelatedReleasePagination = LocalRelatedReleasePagination(
    relatedReleaseId = relatedReleaseId,
    page = page,
    pages = pages,
    perPage = perPage,
    items = items,
    firstPage = urls.first?.getPageNumber(),
    lastPage = urls.last?.getPageNumber(),
    prevPage = urls.prev?.getPageNumber(),
    nextPage = urls.next?.getPageNumber(),
    createdAt = Clock.System.now().toEpochMilliseconds()
)
