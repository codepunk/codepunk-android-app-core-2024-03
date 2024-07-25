package com.codepunk.skeleton.data.mapper

import android.net.Uri
import com.codepunk.skeleton.data.local.entity.LocalRelatedReleasePagination
import com.codepunk.skeleton.data.remote.entity.RemotePagination
import kotlinx.datetime.Clock

private fun String.getPage(): Int? =
    Uri.parse(this).getQueryParameter("page")?.toIntOrNull()

fun RemotePagination.toLocalRelatedReleasePagination(
    relatedReleaseId: Long = 0L,
): LocalRelatedReleasePagination = LocalRelatedReleasePagination(
    relatedReleaseId = relatedReleaseId,
    page = page,
    pages = pages,
    perPage = perPage,
    items = items,
    firstKey = urls.first?.run { getPage() },
    lastKey = urls.last?.run { getPage() },
    prevKey = urls.prev?.run { getPage() },
    nextKey = urls.next?.run { getPage() },
    createdAt = Clock.System.now().toEpochMilliseconds()
)
