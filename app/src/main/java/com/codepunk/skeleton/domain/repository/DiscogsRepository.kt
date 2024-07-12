package com.codepunk.skeleton.domain.repository

import arrow.core.Ior
import com.codepunk.skeleton.domain.model.Artist
import com.codepunk.skeleton.domain.model.Label
import com.codepunk.skeleton.domain.model.Master
import com.codepunk.skeleton.domain.model.Release
import kotlinx.coroutines.flow.Flow

interface DiscogsRepository {

    // TODO suspend fun search(query: String): SearchResults

    fun fetchArtist(artistId: Long): Flow<Ior<Throwable, Artist?>>

    fun fetchLabel(labelId: Long): Flow<Ior<Throwable, Label?>>

    fun fetchMaster(masterId: Long): Flow<Ior<Throwable, Master?>>

    fun fetchRelease(releaseId: Long): Flow<Ior<Throwable, Release?>>

}