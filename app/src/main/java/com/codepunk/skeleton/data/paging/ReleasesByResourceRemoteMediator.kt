package com.codepunk.skeleton.data.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.codepunk.skeleton.core.loginator.Loginator
import com.codepunk.skeleton.data.local.DiscogsDatabase
import com.codepunk.skeleton.data.local.dao.RelatedReleasePageKeyDao
import com.codepunk.skeleton.data.local.entity.LocalRelatedRelease
import com.codepunk.skeleton.data.local.entity.LocalRelatedReleasePageKeys
import com.codepunk.skeleton.data.mapper.toLocal
import com.codepunk.skeleton.data.mapper.toLocalRelatedReleasePageKeys
import com.codepunk.skeleton.data.remote.webservice.DiscogsWebservice
import com.codepunk.skeleton.util.toThrowable

@OptIn(ExperimentalPagingApi::class)
class ReleasesByResourceRemoteMediator(
    private val artistId: Long,
    private val pageSize: Int,
    private val sort: String,
    private val ascending: Boolean,
    private val webservice: DiscogsWebservice,
    private val database: DiscogsDatabase,
) : RemoteMediator<Int, LocalRelatedRelease>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, LocalRelatedRelease>
    ): MediatorResult {

        val relatedReleaseDao = database.relatedReleaseDao()
        val relatedReleasePageKeyDao = database.relatedReleasePageKeyDao()

        val page: Int = when (loadType) {
            LoadType.REFRESH -> {
                val keys = relatedReleasePageKeyDao.getPageKeyClosestToCurrentPosition(state)
                keys?.nextKey?.minus(1) ?: 1
            }
            LoadType.PREPEND -> {
                val keys = relatedReleasePageKeyDao.getPageKeyForFirstItem(state)
                val prevKey = keys?.prevKey
                prevKey ?: return MediatorResult.Success(endOfPaginationReached = (keys != null))
            }
            LoadType.APPEND -> {
                val keys = relatedReleasePageKeyDao.getPageKeyForLastItem(state)
                val nextKey = keys?.nextKey
                nextKey ?: return MediatorResult.Success(endOfPaginationReached = (keys != null))
            }
        }

        val sortOrder = if (ascending) "asc" else "desc"
        val response = webservice.getReleasesByArtist(
            artistId = artistId,
            sort = sort,
            sortOrder = sortOrder,
            pageSize = pageSize,
            page = page
        )

        val result = response.fold(
            ifLeft = { callError -> MediatorResult.Error(callError.toThrowable()) },
            ifRight = { (remotePagination, remoteRelatedReleases) ->
                Loginator.d { "Page ${remotePagination.page}: Fetched ${remoteRelatedReleases.size} more releases" }
                val endOfPaginationReached = remoteRelatedReleases.isEmpty()
                val prevKey = if (page > 1) page - 1 else null
                val nextKey = if (endOfPaginationReached) null else page + 1
                database.withTransaction {
                    val resourceId =
                        database.resourceDao().getResourceByArtistId(artistId)?.resourceId
                            ?: return@withTransaction MediatorResult.Error(
                                IllegalStateException("No resource found for artist ID $artistId")
                            )

                    if (loadType == LoadType.REFRESH) {
                        relatedReleaseDao.clearRelatedReleases(resourceId)
                    }
                    val localRelatedReleases =
                        remoteRelatedReleases.map { remoteRelatedRelease ->
                            remoteRelatedRelease.toLocal(resourceId = resourceId)
                        }
                    val localPageKeys = relatedReleaseDao.insertRelatedReleases(
                        localRelatedReleases
                    ).map { relatedReleaseId ->
                        remotePagination.toLocalRelatedReleasePageKeys(
                            relatedReleaseId = relatedReleaseId,
                            prevKey = prevKey,
                            nextKey = nextKey
                        )
                    }
                    relatedReleasePageKeyDao.insertPageKeys(localPageKeys)
                }
                MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
            }
        )

        return result
    }

    private suspend fun RelatedReleasePageKeyDao.getPageKeyClosestToCurrentPosition(
        state: PagingState<Int, LocalRelatedRelease>
    ): LocalRelatedReleasePageKeys? = state.anchorPosition?.let { position ->
        state.closestItemToPosition(position)?.relatedReleaseId?.let { relatedReleaseId ->
            getPageKeys(relatedReleaseId)
        }
    }

    private suspend fun RelatedReleasePageKeyDao.getPageKeyForFirstItem(
        state: PagingState<Int, LocalRelatedRelease>
    ): LocalRelatedReleasePageKeys? = state.pages.firstOrNull { page ->
        page.data.isNotEmpty()
    }?.data?.firstOrNull()?.let { localRelatedRelease ->
        getPageKeys(localRelatedRelease.relatedReleaseId)
    }

    private suspend fun RelatedReleasePageKeyDao.getPageKeyForLastItem(
        state: PagingState<Int, LocalRelatedRelease>
    ): LocalRelatedReleasePageKeys? = state.pages.lastOrNull { page ->
        page.data.isNotEmpty()
    }?.data?.lastOrNull()?.let { localRelatedRelease ->
        getPageKeys(localRelatedRelease.relatedReleaseId)
    }

}