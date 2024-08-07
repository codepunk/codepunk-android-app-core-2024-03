package com.codepunk.skeleton.ui.screen.label

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
class LabelViewModel @Inject constructor(
    private val repository: DiscogsRepository
): ViewModel() {

    var state by mutableStateOf(LabelScreenState())
        private set

    private fun fetchLabel(labelId: Long) {
        state = state.copy(
            labelId = labelId,
            isLoading = true
        )

        /* TODO Use flow.combine somehow?
        viewModelScope.launch(Dispatchers.IO) {
            repository.fetchLabel(labelId).combine(
                repository.fetchLabelSomethings(
                    labelId = labelId,
                    pageSize = 20,
                    sort = "year",
                    ascending = false
                )
            ) { label, pagingData ->

            }
        }
         */

        viewModelScope.launch(Dispatchers.IO) {
            repository.fetchLabel(labelId).collect { result ->
                // Also try this
                state = state.copy(
                    labelId = labelId,
                    isLoading = false,
                    label = result.getOrNull(),
                    throwable = result.leftOrNull()
                )
            }
        }

        /*
        viewModelScope.launch(Dispatchers.IO) {
            repository.fetchArtistReleases(
                artistId = artistId,
                pageSize = PAGE_SIZE,
                sort = SORT,
                ascending = IS_ASCENDING
            ).collect { pagingData ->
                state = state.copy(
                    releases = pagingData
                )
            }
        }
         */
    }

    private fun refreshLabel() {
        fetchLabel(state.labelId)
    }

    fun onEvent(event: LabelScreenEvent) {
        when (event) {
            is LabelScreenEvent.LoadLabel -> fetchLabel(event.labelId)
            is LabelScreenEvent.RefreshLabel -> refreshLabel()
        }
    }

    companion object {
        private const val PAGE_SIZE = 10
        private const val SORT = "year"
        private const val IS_ASCENDING = false
    }

}
