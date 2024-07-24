package com.codepunk.skeleton.ui.screen.artist

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.codepunk.skeleton.data.paging.ReleasesByResourcePagingDataSource
import com.codepunk.skeleton.data.remote.webservice.DiscogsWebservice
import com.codepunk.skeleton.domain.repository.DiscogsRepository
import com.codepunk.skeleton.ui.TAYLOR_SWIFT
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArtistViewModel @Inject constructor(
    private val webservice: DiscogsWebservice, // TODO TEMP
    private val repository: DiscogsRepository
): ViewModel() {

    var state by mutableStateOf(ArtistScreenState())
        private set

    private fun fetchArtist(artistId: Long) {
        state = state.copy(
            artistId = artistId,
            isLoading = true
        )
        viewModelScope.launch(Dispatchers.IO) {
            repository.fetchArtist(artistId).collect { result ->
                // Also try this
                state = state.copy(
                    artistId = artistId,
                    isLoading = false,
                    artist = result.getOrNull(),
                    throwable = result.leftOrNull()
                )
            }
        }
    }

    private fun refreshArtist() {
        fetchArtist(state.artistId)
    }

    // TODO Try with RemoteMediator after this works
    private fun tryPaging(
        artistId: Long,
        sort: String,
        ascending: Boolean
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            Pager(
                config = PagingConfig(
                    pageSize = 10,
                    prefetchDistance = 2,
                    initialLoadSize = 20
                ),
                pagingSourceFactory = {
                    ReleasesByResourcePagingDataSource(
                        artistId = artistId,
                        sort = sort,
                        ascending = ascending,
                        perPage = 10,
                        webservice = webservice
                    )
                }
            ).flow.cachedIn(viewModelScope).collect { result ->
                state = state.copy(
                    releases = result
                )
            }
        }
    }

    fun onEvent(event: ArtistScreenEvent) {
        when (event) {
            is ArtistScreenEvent.LoadArtist -> fetchArtist(event.artistId)
            is ArtistScreenEvent.RefreshArtist -> refreshArtist()
            is ArtistScreenEvent.TryPaging -> tryPaging( // TODO TEMP
                TAYLOR_SWIFT,
                "year",
                false
            )
        }
    }
}