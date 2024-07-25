package com.codepunk.skeleton.data.paging

import android.net.Uri
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.codepunk.skeleton.data.remote.entity.RemoteRelatedRelease
import com.codepunk.skeleton.data.remote.webservice.DiscogsWebservice
import com.codepunk.skeleton.util.toThrowable

class ReleasesByResourcePagingDataSource(
    private val artistId: Long,
    private val sort: String,
    private val ascending: Boolean,
    private val perPage: Int,
    private val webservice: DiscogsWebservice
) : PagingSource<Int, RemoteRelatedRelease>() {

    override fun getRefreshKey(state: PagingState<Int, RemoteRelatedRelease>): Int = 1

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, RemoteRelatedRelease> =
        webservice.getReleasesByArtist(
            artistId = artistId,
            sort = sort,
            sortOrder = if (ascending) "ASC" else "DESC",
            perPage = perPage,
            page = params.key ?: 1
        ).fold(
            ifLeft = { callError ->
                LoadResult.Error(callError.toThrowable())
            },
            ifRight = { result ->
                var nextPageNum: Int? = null
                result.pagination.urls.next?.also { next ->
                    val uri = Uri.parse(next)
                    val nextPageQuery = uri.getQueryParameter("page")
                    nextPageNum = nextPageQuery?.toIntOrNull()
                }

                LoadResult.Page(
                    data = result.relatedReleases,
                    prevKey = null,
                    nextKey = nextPageNum
              )
            }
        )
    }
