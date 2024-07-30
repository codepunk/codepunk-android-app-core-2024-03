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

        /* TODO Use flow.combine somehow?
        viewModelScope.launch(Dispatchers.IO) {
            repository.fetchArtist(artistId).combine(
                repository.fetchArtistReleases(
                    artistId = artistId,
                    pageSize = 20,
                    sort = "year",
                    ascending = false
                )
            ) { artist, pagingData ->

            }
        }
         */

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
                pageSize = PAGE_SIZE,
                sort = SORT,
                ascending = IS_ASCENDING
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

    fun onEvent(event: ArtistScreenEvent) {
        when (event) {
            is ArtistScreenEvent.LoadArtist -> fetchArtist(event.artistId)
            is ArtistScreenEvent.RefreshArtist -> refreshArtist()
            is ArtistScreenEvent.TryPaging -> { /* No op */ }
        }
    }

    companion object {
        private const val PAGE_SIZE = 10
        private const val SORT = "year"
        private const val IS_ASCENDING = false
    }
}