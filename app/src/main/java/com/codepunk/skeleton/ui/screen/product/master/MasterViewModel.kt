package com.codepunk.skeleton.ui.screen.product.master

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
class MasterViewModel @Inject constructor(
    private val repository: DiscogsRepository
) : ViewModel() {

    var state by mutableStateOf(MasterScreenState())
        private set

    private fun fetchMaster(masterId: Long) {
        state = state.copy(
            masterId = masterId,
            isLoading = true
        )

        viewModelScope.launch(Dispatchers.IO) {
            repository.fetchMaster(masterId).collect { result ->
                val master = result.getOrNull()
                state = state.copy(
                    masterId = masterId,
                    isLoading = false,
                    master = master,
                    throwable = result.leftOrNull()
                )
            }
        }
    }

    private fun refreshMaster() {
        fetchMaster(state.masterId)
    }

    fun onEvent(event: MasterScreenEvent) {
        when (event) {
            is MasterScreenEvent.LoadMaster -> fetchMaster(event.masterId)
            is MasterScreenEvent.RefreshMaster -> refreshMaster()
        }
    }

}