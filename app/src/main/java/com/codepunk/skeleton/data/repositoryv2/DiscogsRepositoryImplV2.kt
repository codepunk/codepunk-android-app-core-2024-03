package com.codepunk.skeleton.data.repositoryv2

import arrow.core.Ior
import com.codepunk.skeleton.data.localv2.dao.DiscogsDao
import com.codepunk.skeleton.data.mapperv2.toDomainArtist
import com.codepunk.skeleton.data.mapperv2.toDomainLabel
import com.codepunk.skeleton.data.mapperv2.toLocalResourceAndArtist
import com.codepunk.skeleton.data.mapperv2.toLocalResourceAndLabel
import com.codepunk.skeleton.data.remote.webservice.DiscogsWebserviceV2
import com.codepunk.skeleton.domain.repository.DiscogsRepositoryV2
import com.codepunk.skeleton.domainv2.model.Artist
import com.codepunk.skeleton.domainv2.model.Label
import com.codepunk.skeleton.domainv2.model.Master
import com.codepunk.skeleton.domainv2.model.Release
import com.codepunk.skeleton.util.networkBoundResource
import com.codepunk.skeleton.util.toThrowable
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DiscogsRepositoryImplV2(
    private val discogsDao: DiscogsDao,
    private val discogsWebService: DiscogsWebserviceV2
) : DiscogsRepositoryV2 {

    // region Methods
    override fun fetchArtist(artistId: Long): Flow<Ior<Throwable, Artist?>> = networkBoundResource(
        query = {
            discogsDao.getResourceAndArtist(artistId).map { it?.toDomainArtist() }
        },
        fetch = {
            discogsWebService.getArtist(artistId).fold(
                ifLeft = { throw it.toThrowable() },
                ifRight = { it }
            )
        },
        saveFetchResult = {
            discogsDao.insertResourceAndArtist(it.toLocalResourceAndArtist())
        }
    )

    override fun fetchLabel(labelId: Long): Flow<Ior<Throwable, Label?>> = networkBoundResource(
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

    override fun fetchMaster(masterId: Long): Flow<Ior<Throwable, Master?>> {
        TODO("Not yet implemented")
    }

    override fun fetchRelease(releaseId: Long): Flow<Ior<Throwable, Release?>> {
        TODO("Not yet implemented")
    }

    // endregion Methods

}
