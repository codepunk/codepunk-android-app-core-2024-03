package com.codepunk.skeleton.data.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.codepunk.skeleton.data.local.DiscogsDatabase
import com.codepunk.skeleton.data.local.dao.RelatedReleaseDao
import com.codepunk.skeleton.data.local.dao.RelatedReleasePaginationDao
import com.codepunk.skeleton.data.local.dao.ResourceDao
import com.codepunk.skeleton.data.local.entity.LocalRelatedRelease
import com.codepunk.skeleton.data.local.entity.LocalRelatedReleasePagination
import com.codepunk.skeleton.data.mapper.toLocal
import com.codepunk.skeleton.data.mapper.toLocalRelatedReleasePagination
import com.codepunk.skeleton.data.remote.webservice.DiscogsWebservice
import com.codepunk.skeleton.util.toThrowable

@OptIn(ExperimentalPagingApi::class)
class ReleasesByResourceRemoteMediator(
    private val artistId: Long,
    private val sort: String,
    private val ascending: Boolean,
    private val perPage: Int,
    private val webservice: DiscogsWebservice,
    private val discogsDatabase: DiscogsDatabase,
    private val relatedReleaseDao: RelatedReleaseDao,
    private val relatedReleasePaginationDao: RelatedReleasePaginationDao,
    private val resourceDao: ResourceDao
) : RemoteMediator<Int, LocalRelatedRelease>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, LocalRelatedRelease>
    ): MediatorResult {

        val page = when (loadType) {
            LoadType.REFRESH -> {
                val pagination = getPaginationClosestToCurrentPosition(state)
                pagination?.nextKey?.minus(1) ?: 1
            }
            LoadType.PREPEND -> {
                val pagination = getPaginationForFirstItem(state)
                val prevKey = pagination?.prevKey
                prevKey ?: return MediatorResult.Success(
                    endOfPaginationReached = pagination != null
                )
            }
            LoadType.APPEND -> {
                val pagination = getPaginationForLastItem(state)
                val nextKey = pagination?.nextKey
                nextKey ?: return MediatorResult.Success(
                    endOfPaginationReached = pagination != null
                )
            }
        }

        val response = webservice.getReleasesByArtist(
            artistId = artistId,
            sort = sort,
            sortOrder = if (ascending) "asc" else "desc",
            perPage = perPage,
            page = page
        )

        return response.fold(
            ifLeft = {
                MediatorResult.Error(it.toThrowable())
            },
            ifRight = { releasesByArtist ->
                val endOfPaginationReached = releasesByArtist.relatedReleases.isEmpty()

                discogsDatabase.withTransaction {
                    val resourceId = resourceDao.getResourceByArtistId(artistId)?.resourceId ?:
                        return@withTransaction MediatorResult.Error(
                            IllegalStateException("No resource found for artist ID $artistId")
                        )

                    if (loadType == LoadType.REFRESH) {
                        relatedReleaseDao.clearRelatedReleases(resourceId)
                    }

                    // First, I need to insert related releases so I have a list of IDs,
                    // then map them to local pagination instances
                    val localPaginations = relatedReleaseDao.insertRelatedReleases(
                        releasesByArtist.relatedReleases.map { relatedRelease ->
                            relatedRelease.toLocal(resourceId = resourceId)
                        }
                    ).zip(
                        releasesByArtist.relatedReleases
                    ).map { (relatedReleaseId, remoteRelatedRelease) ->
                        releasesByArtist.pagination.toLocalRelatedReleasePagination(
                            relatedReleaseId = relatedReleaseId
                        )
                    }
                    relatedReleasePaginationDao.insertPaginations(localPaginations)
                }

                MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
            }
        )
    }

    private suspend fun getPaginationClosestToCurrentPosition(
        state: PagingState<Int, LocalRelatedRelease>
    ): LocalRelatedReleasePagination? = state.anchorPosition?.let { position ->
        state.closestItemToPosition(position)?.let { localRelatedRelease ->
            relatedReleasePaginationDao.getPagination(localRelatedRelease.relatedReleaseId)
        }
    }

    private suspend fun getPaginationForFirstItem(
        state: PagingState<Int, LocalRelatedRelease>
    ): LocalRelatedReleasePagination? = state.pages.firstOrNull {
        it.data.isNotEmpty()
    }?.data?.firstOrNull()?.let { localRelatedRelease ->
        relatedReleasePaginationDao.getPagination(localRelatedRelease.relatedReleaseId)
    }

    private suspend fun getPaginationForLastItem(
        state: PagingState<Int, LocalRelatedRelease>
    ): LocalRelatedReleasePagination? = state.pages.lastOrNull {
        it.data.isNotEmpty()
    }?.data?.lastOrNull()?.let { localRelatedRelease ->
        relatedReleasePaginationDao.getPagination(localRelatedRelease.relatedReleaseId)
    }

}