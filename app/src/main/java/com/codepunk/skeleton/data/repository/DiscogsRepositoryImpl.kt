package com.codepunk.skeleton.data.repository

import arrow.core.Ior
import com.codepunk.skeleton.data.local.dao.AllDao
import com.codepunk.skeleton.data.local.dao.ArtistDao
import com.codepunk.skeleton.data.local.dao.LabelDao
import com.codepunk.skeleton.data.local.dao.MasterDao
import com.codepunk.skeleton.data.local.dao.ReleaseDao
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
    private val allDao: AllDao,
    private val artistDao: ArtistDao,
    private val labelDao: LabelDao,
    private val masterDao: MasterDao,
    private val releaseDao: ReleaseDao,
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
                labelDao.getResourceAndLabel(labelId).map { it?.toDomainLabel() }
            },
            fetch = {
                discogsWebService.getLabel(labelId).fold(
                    ifLeft = { throw it.toThrowable() },
                    ifRight = { it }
                )
            },
            saveFetchResult = {
                allDao.insertResourceAndLabel(it.toLocalResourceAndLabel())
            }
        )

    override fun fetchMaster(masterId: Long): Flow<Ior<Throwable, Master?>> =
        networkBoundResource(
            query = {
                masterDao.getResourceAndMaster(masterId).map { it?.toDomainMaster() }
            },
            fetch = {
                discogsWebService.getMaster(masterId).fold(
                    ifLeft = { throw it.toThrowable() },
                    ifRight = { it }
                )
            },
            saveFetchResult = {
                allDao.insertResourceAndMaster(it.toLocalResourceAndMaster())
            }
        )

    override fun fetchRelease(releaseId: Long): Flow<Ior<Throwable, Release?>> =
        networkBoundResource(
            query = {
                releaseDao.getResourceAndRelease(releaseId).map { it?.toDomainRelease() }
            },
            fetch = {
                discogsWebService.getRelease(releaseId).fold(
                    ifLeft = { throw it.toThrowable() },
                    ifRight = { it }
                )
            },
            saveFetchResult = {
                allDao.insertResourceAndRelease(it.toLocalResourceAndRelease())
            }
        )

    // endregion Methods

}
