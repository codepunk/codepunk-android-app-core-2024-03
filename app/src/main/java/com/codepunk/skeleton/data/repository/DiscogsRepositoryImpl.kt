package com.codepunk.skeleton.data.repository

import arrow.core.Ior
import com.codepunk.skeleton.data.local.dao.AllDao
import com.codepunk.skeleton.data.local.dao.ArtistDao
import com.codepunk.skeleton.data.local.dao.DiscogsDao
import com.codepunk.skeleton.data.mapper.toDomainArtist
import com.codepunk.skeleton.data.mapper.toDomainLabel
import com.codepunk.skeleton.data.mapper.toDomainMaster
import com.codepunk.skeleton.data.mapper.toDomainRelease
import com.codepunk.skeleton.data.mapper.toLocalResourceAndArtist
import com.codepunk.skeleton.data.mapper.toLocalResourceAndLabel
import com.codepunk.skeleton.data.mapper.toLocalResourceAndMaster
import com.codepunk.skeleton.data.mapper.toLocalResourceAndRelease
import com.codepunk.skeleton.data.remote.webservice.DiscogsWebservice
import com.codepunk.skeleton.domain.repository.DiscogsRepository
import com.codepunk.skeleton.domain.model.Artist
import com.codepunk.skeleton.domain.model.Label
import com.codepunk.skeleton.domain.model.Master
import com.codepunk.skeleton.domain.model.Release
import com.codepunk.skeleton.util.networkBoundResource
import com.codepunk.skeleton.util.toThrowable
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DiscogsRepositoryImpl(
    private val discogsDao: DiscogsDao,
    private val artistDao: ArtistDao,
    private val allDao: AllDao,
    private val discogsWebService: DiscogsWebservice
) : DiscogsRepository {

    // region Methods
    override fun fetchArtist(artistId: Long): Flow<Ior<Throwable, Artist?>> =
        networkBoundResource(
            query = {
                artistDao.getResourceAndArtist(artistId).map { it?.toDomainArtist() }
            },
            fetch = {
                discogsWebService.getArtist(artistId).fold(
                    ifLeft = { throw it.toThrowable() },
                    ifRight = { it }
                )
            },
            saveFetchResult = {
                allDao.insertResourceAndArtist(it.toLocalResourceAndArtist())
            }
        )

    override fun fetchLabel(labelId: Long): Flow<Ior<Throwable, Label?>> =
        networkBoundResource(
            query = {
                discogsDao.getResourceAndLabel(labelId).map { it?.toDomainLabel() }
            },
            fetch = {
                discogsWebService.getLabel(labelId).fold(
                    ifLeft = { throw it.toThrowable() },
                    ifRight = { it }
                )
            },
            saveFetchResult = {
                discogsDao.insertResourceAndLabel(it.toLocalResourceAndLabel())
            }
        )

    override fun fetchMaster(masterId: Long): Flow<Ior<Throwable, Master?>> =
        networkBoundResource(
            query = {
                discogsDao.getResourceAndMaster(masterId).map { it?.toDomainMaster() }
            },
            fetch = {
                discogsWebService.getMaster(masterId).fold(
                    ifLeft = { throw it.toThrowable() },
                    ifRight = { it }
                )
            },
            saveFetchResult = {
                discogsDao.insertResourceAndMaster(it.toLocalResourceAndMaster())
            }
        )

    override fun fetchRelease(releaseId: Long): Flow<Ior<Throwable, Release?>> =
        networkBoundResource(
            query = {
                discogsDao.getResourceAndRelease(releaseId).map { it?.toDomainRelease() }
            },
            fetch = {
                discogsWebService.getRelease(releaseId).fold(
                    ifLeft = { throw it.toThrowable() },
                    ifRight = { it }
                )
            },
            saveFetchResult = {
                discogsDao.insertResourceAndRelease(it.toLocalResourceAndRelease())
            }
        )

    // endregion Methods

}
