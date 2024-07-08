package com.codepunk.skeleton.data.repository

import arrow.core.Ior
import com.codepunk.skeleton.data.local.dao.ArtistDao
import com.codepunk.skeleton.data.local.dao.LabelDao
import com.codepunk.skeleton.data.local.dao.MasterDao
import com.codepunk.skeleton.data.mapper.toDomainArtist
import com.codepunk.skeleton.data.mapper.toDomainLabel
import com.codepunk.skeleton.data.mapper.toDomainMaster
import com.codepunk.skeleton.data.mapper.toLocalArtistWithDetails
import com.codepunk.skeleton.data.mapper.toLocalLabelWithDetails
import com.codepunk.skeleton.data.mapper.toLocalMasterWithDetails
import com.codepunk.skeleton.data.remote.webservice.DiscogsWebService
import com.codepunk.skeleton.domain.model.Release
import com.codepunk.skeleton.domain.repository.DiscogsRepository
import com.codepunk.skeleton.util.networkBoundResource
import com.codepunk.skeleton.util.toThrowable
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DiscogsRepositoryImpl(
    private val artistDao: ArtistDao,
    private val labelDao: LabelDao,
    private val masterDao: MasterDao,
    private val discogsWebService: DiscogsWebService
) : DiscogsRepository {

    // region Methods

    override fun fetchArtist(artistId: Long) = networkBoundResource(
        query = {
            artistDao.getArtistWithDetails(artistId).map { it?.toDomainArtist() }
        },
        fetch = {
            discogsWebService.getArtist(artistId).fold(
                ifLeft = { throw it.toThrowable() },
                ifRight = { it }
            )
        },
        saveFetchResult = {
            artistDao.insertArtistWithDetails(it.toLocalArtistWithDetails())
        }
    )

    override fun fetchLabel(labelId: Long) = networkBoundResource(
        query = {
            labelDao.getLabelWithDetails(labelId).map { it?.toDomainLabel() }
        },
        fetch = {
            discogsWebService.getLabel(labelId).fold(
                ifLeft = { throw it.toThrowable() },
                ifRight = { it }
            )
        },
        saveFetchResult = {
            labelDao.insertLabelWithDetails(it.toLocalLabelWithDetails())
        }
    )

    override fun fetchMaster(masterId: Long) = networkBoundResource(
        query = {
            masterDao.getMasterWithDetails(masterId).map { it?.toDomainMaster() }
        },
        fetch = {
            discogsWebService.getMaster(masterId).fold(
                ifLeft = { throw it.toThrowable() },
                ifRight = { it }
            )
        },
        saveFetchResult = {
            masterDao.insertMasterWithDetails(it.toLocalMasterWithDetails())
        }
    )

    override fun fetchRelease(releaseId: Long): Flow<Ior<Throwable, Release?>> {
        TODO("Not yet implemented")
    }

    // endregion Methods

}
