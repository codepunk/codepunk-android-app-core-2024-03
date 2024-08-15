package com.codepunk.skeleton.ui.screen.product.release

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codepunk.skeleton.domain.repository.DiscogsRepository
import com.codepunk.skeleton.ui.screen.product.master.MasterScreenEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReleaseViewModel @Inject constructor(
    private val repository: DiscogsRepository
) : ViewModel() {

    var state by mutableStateOf(ReleaseScreenState())
        private set

    private fun fetchRelease(releaseId: Long) {
        state = state.copy(
            releaseId = releaseId,
            isLoading = true
        )

        viewModelScope.launch(Dispatchers.IO) {
            repository.fetchRelease(releaseId).collect { result ->
                val release = result.getOrNull()
                state = state.copy(
                    releaseId = releaseId,
                    isLoading = false,
                    release = release,
                    throwable = result.leftOrNull()
                )
            }
        }
    }

    private fun refreshRelease() {
        fetchRelease(state.releaseId)
    }

    fun onEvent(event: ReleaseScreenEvent) {
        when (event) {
            is ReleaseScreenEvent.LoadRelease -> fetchRelease(event.releaseId)
            is ReleaseScreenEvent.RefreshRelease -> refreshRelease()
        }
    }
}
