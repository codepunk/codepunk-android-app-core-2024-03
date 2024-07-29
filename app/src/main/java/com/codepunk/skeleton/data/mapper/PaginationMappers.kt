package com.codepunk.skeleton.data.mapper

import com.codepunk.skeleton.data.local.entity.LocalRelatedReleasePageKeys
import com.codepunk.skeleton.data.remote.entity.RemotePagination
import kotlinx.datetime.Clock

fun RemotePagination.toLocalRelatedReleasePageKeys(
    relatedReleaseId: Long = 0L,
    prevKey: Int? = null,
    nextKey: Int? = null
): LocalRelatedReleasePageKeys = LocalRelatedReleasePageKeys(
    relatedReleaseId = relatedReleaseId,
    prevKey = prevKey,
    currentPage = page,
    nextKey = nextKey,
    createdAt = Clock.System.now().toEpochMilliseconds()
)
