package com.codepunk.skeleton.data.repository

import arrow.core.Ior
import com.codepunk.skeleton.data.local.dao.ArtistDao
import com.codepunk.skeleton.data.mapper.toDomainArtist
import com.codepunk.skeleton.data.mapper.toLocalArtistWithDetails
import com.codepunk.skeleton.data.remote.webservice.DiscogsWebService
import com.codepunk.skeleton.domain.model.Artist
import com.codepunk.skeleton.domain.repository.DiscogsRepository
import com.codepunk.skeleton.util.networkBoundResource
import com.codepunk.skeleton.util.toThrowable
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DiscogsRepositoryImpl(
    private val artistDao: ArtistDao,
    private val discogsWebService: DiscogsWebService
) : DiscogsRepository {

    override fun fetchArtist(id: Long): Flow<Ior<Throwable, Artist?>> = networkBoundResource(
        query = {
            artistDao.getArtistWithDetails(id).map { localArtistWithDetails ->
                localArtistWithDetails?.toDomainArtist()
            }
        },
        fetch = {
            discogsWebService.getArtist(id).fold(
                ifLeft = { throw it.toThrowable() },
                ifRight = { it }
            )
        },
        saveFetchResult = {
            artistDao.upsertArtistWithDetails(it.toLocalArtistWithDetails())
        }
    )

}
