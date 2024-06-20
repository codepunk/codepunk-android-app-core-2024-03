package com.codepunk.skeleton.domain.repository

import arrow.core.Ior
import com.codepunk.skeleton.domain.model.Artist
import com.codepunk.skeleton.domain.model.Label
import com.codepunk.skeleton.domain.model.Master
import kotlinx.coroutines.flow.Flow

interface DiscogsRepository {

    // TODO suspend fun search(query: String): SearchResults

    fun fetchArtist(id: Long): Flow<Ior<Throwable, Artist?>>

    fun fetchLabel(id: Long): Flow<Ior<Throwable, Label?>>

    fun fetchMaster(id: Long): Flow<Ior<Throwable, Master?>>

}