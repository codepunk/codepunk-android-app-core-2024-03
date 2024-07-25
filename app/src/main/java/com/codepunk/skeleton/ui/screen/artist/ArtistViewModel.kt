package com.codepunk.skeleton.ui.screen.artist

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codepunk.skeleton.domain.repository.DiscogsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArtistViewModel @Inject constructor(
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
        viewModelScope.launch(Dispatchers.IO) {
            repository.fetchArtistReleases(
                artistId = artistId,
                pageSize = 20,
                sort = "year",
                ascending = false
            ).collect {
                state = state.copy(
                    releases = it
                )
            }
        }
    }

    private fun refreshArtist() {
        fetchArtist(state.artistId)
    }

    /*
    // TODO Try with RemoteMediator after this works
    @OptIn(ExperimentalPagingApi::class)
    private suspend fun tryPaging(
        artistId: Long,
        sort: String,
        ascending: Boolean
    ) {
        //viewModelScope.launch(Dispatchers.IO) {
            // TODO Move all of this logic to Repository and get extra stuff out of constructor

            Pager(
                config = PagingConfig(
                    pageSize = 10,
                    prefetchDistance = 2,
                    initialLoadSize = 20
                ),
                pagingSourceFactory = {
                    relatedReleaseDao.getReleasesByArtist(
                        artistId = artistId,
                        sort = sort,
                        ascending = ascending
                    )
                },
                remoteMediator = ReleasesByResourceRemoteMediator(
                    artistId = artistId,
                    sort = sort,
                    ascending = ascending,
                    perPage = 10,
                    webservice = webservice,
                    database = database
                )
            ).flow.cachedIn(viewModelScope).collect { releases ->
                state = state.copy(
                    releases = releases.map {
                        it.toDomain()
                    }
                )
            }
        //}
    }
     */

    fun onEvent(event: ArtistScreenEvent) {
        when (event) {
            is ArtistScreenEvent.LoadArtist -> fetchArtist(event.artistId)
            is ArtistScreenEvent.RefreshArtist -> refreshArtist()
            is ArtistScreenEvent.TryPaging -> { /* No op */ }
        }
    }
}