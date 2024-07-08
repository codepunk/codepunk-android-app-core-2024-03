package com.codepunk.skeleton.domain.repository

import arrow.core.Ior
import com.codepunk.skeleton.domainv2.model.Artist
import com.codepunk.skeleton.domainv2.model.Label
import com.codepunk.skeleton.domainv2.model.Master
import com.codepunk.skeleton.domainv2.model.Release
import kotlinx.coroutines.flow.Flow

interface DiscogsRepositoryV2 {

    // TODO suspend fun search(query: String): SearchResults

    fun fetchArtist(artistId: Long): Flow<Ior<Throwable, Artist?>>

    fun fetchLabel(labelId: Long): Flow<Ior<Throwable, Label?>>

    fun fetchMaster(masterId: Long): Flow<Ior<Throwable, Master?>>

    fun fetchRelease(releaseId: Long): Flow<Ior<Throwable, Release?>>

}
