package com.codepunk.skeleton.domain.repository

import androidx.paging.PagingData
import arrow.core.Ior
import com.codepunk.skeleton.domain.model.Artist
import com.codepunk.skeleton.domain.model.Label
import com.codepunk.skeleton.domain.model.Master
import com.codepunk.skeleton.domain.model.RelatedRelease
import com.codepunk.skeleton.domain.model.Release
import kotlinx.coroutines.flow.Flow

interface DiscogsRepository {

    // TODO suspend fun search(query: String): SearchResults

    fun fetchArtist(artistId: Long): Flow<Ior<Throwable, Artist?>>

    fun fetchArtistReleases(
        artistId: Long,
        pageSize: Int,
        sort: String,
        ascending: Boolean
    ): Flow<PagingData<RelatedRelease>>

    fun fetchLabel(labelId: Long): Flow<Ior<Throwable, Label?>>

    fun fetchMaster(masterId: Long): Flow<Ior<Throwable, Master?>>

    fun fetchRelease(releaseId: Long): Flow<Ior<Throwable, Release?>>

}