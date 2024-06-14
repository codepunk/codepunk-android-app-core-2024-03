package com.codepunk.skeleton.domain.repository

import arrow.core.Ior
import com.codepunk.skeleton.domain.model.Artist
import kotlinx.coroutines.flow.Flow

interface DiscogsRepository {

    // TODO suspend fun search(query: String): SearchResults

    fun fetchArtist(id: Long): Flow<Ior<Throwable, Artist?>>

}