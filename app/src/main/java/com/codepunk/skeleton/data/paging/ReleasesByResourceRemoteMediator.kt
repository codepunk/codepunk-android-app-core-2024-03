package com.codepunk.skeleton.data.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.codepunk.skeleton.data.local.dao.RelatedReleaseDao
import com.codepunk.skeleton.data.remote.webservice.DiscogsWebservice
import com.codepunk.skeleton.domain.model.RelatedRelease

@OptIn(ExperimentalPagingApi::class)
class ReleasesByResourceRemoteMediator(
    private val artistId: Long,
    private val sort: String,
    private val ascending: Boolean,
    private val perPage: Int,
    private val relatedReleaseDao: RelatedReleaseDao,
    private val webservice: DiscogsWebservice
) : RemoteMediator<Int, RelatedRelease>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, RelatedRelease>
    ): MediatorResult {
        TODO("Not yet implemented")
        /*
        return try {
            /*
            // The network load method takes an optional after=<user.id>
            // parameter. For every page after the first, pass the last user
            // ID to let it continue from where it left off. For REFRESH,
            // pass null to load the first page.
            val page = when (loadType) {
                LoadType.REFRESH -> 1
                // In this example, you never need to prepend, since REFRESH
                // will always load the first page in the list. Immediately
                // return, reporting end of pagination.
                LoadType.PREPEND ->
                    return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    val lastItem = state.

                    // You must explicitly check if the last item is null when
                    // appending, since passing null to networkService is only
                    // valid for initial load. If lastItem is null it means no
                    // items were loaded after the initial REFRESH and there are
                    // no more items to load.
                    if (lastItem == null) {
                        return MediatorResult.Success(
                            endOfPaginationReached = true
                        )
                    }

                    lastItem.releaseId.toInt() // TODO Need to return a page!
                }
            }

            // Suspending network load via Retrofit. This doesn't need to be
            // wrapped in a withContext(Dispatcher.IO) { ... } block since
            // Retrofit's Coroutine CallAdapter dispatches on a worker
            // thread.
            val response = webservice.getReleasesByArtist(
                artistId = artistId,
                sort = sort,
                sortOrder = if (ascending) "ASC" else "DESC",
                perPage = perPage,
                page = page
            )

             */

            TODO()
        } catch (e: IOException) {
            TODO()
        } catch (e: HttpException) {
            TODO()
        }
         */
    }
}