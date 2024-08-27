package com.codepunk.skeleton.ui.screen.search

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codepunk.skeleton.domain.repository.DiscogsRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class SearchViewModel @Inject constructor(
    private val repository: DiscogsRepository
) : ViewModel() {

    var state by mutableStateOf(SearchScreenState())
        private set

    private fun search(query: String) {
        state = state.copy(
            query = query,
            isLoading = true
        )

        viewModelScope.launch {
            //repository.search()
        }
    }

}