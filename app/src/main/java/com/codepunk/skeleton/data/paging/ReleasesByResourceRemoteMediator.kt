package com.codepunk.skeleton.data.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.codepunk.skeleton.data.local.DiscogsDatabase
import com.codepunk.skeleton.data.local.dao.RelatedReleasePaginationDao
import com.codepunk.skeleton.data.local.entity.LocalRelatedRelease
import com.codepunk.skeleton.data.local.entity.LocalRelatedReleasePagination
import com.codepunk.skeleton.data.mapper.toLocal
import com.codepunk.skeleton.data.mapper.toLocalRelatedReleasePagination
import com.codepunk.skeleton.data.remote.entity.RemoteUrls
import com.codepunk.skeleton.data.remote.webservice.DiscogsWebservice
import com.codepunk.skeleton.util.toThrowable

@OptIn(ExperimentalPagingApi::class)
class ReleasesByResourceRemoteMediator(
    private val artistId: Long,
    private val pageSize: Int,
    private val sort: String,
    private val ascending: Boolean,
    private val database: DiscogsDatabase,
    private val webservice: DiscogsWebservice
) : RemoteMediator<Int, LocalRelatedRelease>() {

    override suspend fun initialize(): InitializeAction {
        val retVal = super.initialize()
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, LocalRelatedRelease>
    ): MediatorResult {

        val relatedReleaseDao = database.relatedReleaseDao()
        val relatedReleasePaginationDao = database.relatedReleasePaginationDao()

        val page: Int = when (loadType) {
            // Our "Refresh" action will always be page 1
            LoadType.REFRESH -> 1

            // We never need to prepend
            LoadType.PREPEND -> return MediatorResult.Success(true)

            LoadType.APPEND -> {
                val pagination = relatedReleasePaginationDao.getPaginationForLastItem(state)
                pagination?.nextPage
                    ?: return MediatorResult.Success(pagination != null)
            }
        }

        val response = webservice.getReleasesByArtist(
            artistId = artistId,
            sort = sort,
            sortOrder = if (ascending) "asc" else "desc",
            pageSize = pageSize,
            page = page
        )

        @Suppress("SpellCheckingInspection")
        val result = response.fold(
            ifLeft = { callError -> MediatorResult.Error(callError.toThrowable()) },
            ifRight = { (remotePagination, remoteRelatedReleases) ->
                val endOfPaginationReached = !remotePagination.urls.hasNext() //remoteRelatedReleases.isEmpty()

                val resourceId =
                    // TODO This is crashing on first REFRESH sometimes
                    database.resourceDao().getResourceByArtist(artistId)?.resourceId
                        ?: return MediatorResult.Error(
                            IllegalStateException("No resource found for artist ID $artistId")
                        )

                database.withTransaction {
                    if (loadType == LoadType.REFRESH) {
                        relatedReleaseDao.deleteRelatedReleasesByResource(resourceId)
                    }

                    val localRelatedReleases = remoteRelatedReleases.map {
                        it.toLocal(resourceId = resourceId)
                    }
                    val relatedReleaseIds = relatedReleaseDao.insertRelatedReleases(
                        localRelatedReleases
                    )

                    val localPagination = remotePagination.toLocalRelatedReleasePagination()
                    val localPaginations = relatedReleaseIds.map { relatedReleaseId ->
                        localPagination.copy(relatedReleaseId = relatedReleaseId)
                    }
                    relatedReleasePaginationDao.insertPaginations(localPaginations)
                }
                MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
            }
        )

        return result
    }

    private fun RemoteUrls.hasNext(): Boolean = !next.isNullOrBlank()

    private suspend fun RelatedReleasePaginationDao.getPaginationForLastItem(
        state: PagingState<Int, LocalRelatedRelease>
    ): LocalRelatedReleasePagination? {
        val lastPage = state.pages.lastOrNull { page -> page.data.isNotEmpty() } ?: return null
        val localRelatedRelease = lastPage.data.lastOrNull() ?: return null
        return getPagination(localRelatedRelease.relatedReleaseId)
    }

}