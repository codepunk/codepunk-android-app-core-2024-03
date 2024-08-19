package com.codepunk.skeleton.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import arrow.core.Ior
import arrow.core.bothIor
import arrow.core.rightIor
import com.codepunk.skeleton.data.local.dao.AllDao
import com.codepunk.skeleton.data.local.dao.ArtistDao
import com.codepunk.skeleton.data.local.dao.LabelDao
import com.codepunk.skeleton.data.local.dao.MasterDao
import com.codepunk.skeleton.data.local.dao.RelatedReleaseDao
import com.codepunk.skeleton.data.local.dao.ReleaseDao
import com.codepunk.skeleton.data.mapper.toDomain
import com.codepunk.skeleton.data.mapper.toLocal
import com.codepunk.skeleton.data.paging.ReleasesByResourceRemoteMediatorFactory
import com.codepunk.skeleton.data.remote.webservice.DiscogsWebservice
import com.codepunk.skeleton.domain.model.Artist
import com.codepunk.skeleton.domain.model.Label
import com.codepunk.skeleton.domain.model.Master
import com.codepunk.skeleton.domain.model.RelatedRelease
import com.codepunk.skeleton.domain.model.Release
import com.codepunk.skeleton.domain.model.ResourceType
import com.codepunk.skeleton.domain.repository.DiscogsRepository
import com.codepunk.skeleton.ui.util.BBCodeProcessor
import com.codepunk.skeleton.util.networkBoundResource
import com.codepunk.skeleton.util.toThrowable
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DiscogsRepositoryImpl(
    private val artistDao: ArtistDao,
    private val labelDao: LabelDao,
    private val masterDao: MasterDao,
    private val relatedReleaseDao: RelatedReleaseDao,
    private val releaseDao: ReleaseDao,
    private val allDao: AllDao,
    private val discogsWebService: DiscogsWebservice,
    private val factory: ReleasesByResourceRemoteMediatorFactory,
    private val bbCodeProcessor: BBCodeProcessor
) : DiscogsRepository {

    // region Implemented methods

    override fun fetchArtist(artistId: Long): Flow<Ior<Throwable, Artist?>> =
        networkBoundResource(
            query = {
                artistDao.getArtist(artistId).map { it?.toDomain() }
            },
            fetch = {
                discogsWebService.getArtist(artistId).fold(
                    ifLeft = { throw it.toThrowable() },
                    ifRight = { remoteArtist ->
                        val profileHtml = bbCodeProcessor.process(
                            remoteArtist.profile
                        ) { resourceType, value ->
                            value.fold(
                                ifLeft = { id ->
                                    Pair(id, getTitle(resourceType, id)).bothIor()
                                },
                                ifRight = { title ->
                                    title.rightIor()
                                }
                            )
                        }
                        remoteArtist.copy(profile = profileHtml)
                    }
                )
            },
            saveFetchResult = {
                allDao.insertArtist(it.toLocal())
            }
        )

    // TODO Can this somehow be Flow<Ior<PagingData<RelatedRelease>>>?
    @OptIn(ExperimentalPagingApi::class)
    override fun fetchArtistReleases(
        artistId: Long,
        pageSize: Int,
        sort: String,
        ascending: Boolean
    ): Flow<PagingData<RelatedRelease>> = Pager(
        config = PagingConfig(
            pageSize = pageSize
        ),
        remoteMediator = factory.create(
            artistId = artistId,
            pageSize = pageSize,
            sort = sort,
            ascending = ascending
        ),
        pagingSourceFactory = {
            relatedReleaseDao.getReleasesByArtist(artistId = artistId)
        }
    ).flow.map { pagingData ->
        pagingData.map { it.toDomain() }
    }

    override fun fetchLabel(labelId: Long): Flow<Ior<Throwable, Label?>> =
        networkBoundResource(
            query = {
                labelDao.getLabel(labelId).map { it?.toDomain() }
            },
            fetch = {
                discogsWebService.getLabel(labelId).fold(
                    ifLeft = { throw it.toThrowable() },
                    ifRight = { remoteLabel ->
                        val profileHtml = bbCodeProcessor.process(remoteLabel.profile) { resourceType, value ->
                            value.fold(
                                ifLeft = { id ->
                                    Pair(id, getTitle(resourceType, id)).bothIor()
                                },
                                ifRight = { title ->
                                    title.rightIor()
                                }
                            )
                        }
                        remoteLabel.copy(profile = profileHtml)
                    }
                )
            },
            saveFetchResult = {
                allDao.insertLabel(it.toLocal())
            }
        )

    override fun fetchMaster(masterId: Long): Flow<Ior<Throwable, Master?>> =
        networkBoundResource(
            query = {
                masterDao.getMaster(masterId).map { it?.toDomain() }
            },
            fetch = {
                discogsWebService.getMaster(masterId).fold(
                    ifLeft = { throw it.toThrowable() },
                    ifRight = { it }
                )
            },
            saveFetchResult = {
                allDao.insertMaster(it.toLocal())
            }
        )

    override fun fetchRelease(releaseId: Long): Flow<Ior<Throwable, Release?>> =
        networkBoundResource(
            query = {
                releaseDao.getRelease(releaseId).map { it?.toDomain() }
            },
            fetch = {
                discogsWebService.getRelease(releaseId).fold(
                    ifLeft = { throw it.toThrowable() },
                    ifRight = { it }
                )
            },
            saveFetchResult = {
                allDao.insertRelease(it.toLocal())
            }
        )

    // endregion Implemented methods

    // region Methods

    private suspend fun getTitle(resourceType: ResourceType, id: Long): String =
        when (resourceType) {
            ResourceType.ARTIST -> {
                discogsWebService.getArtist(id).fold(
                    ifLeft = { UNKNOWN_TITLE },
                    ifRight = { it.name }
                )
            }
            ResourceType.LABEL -> {
                discogsWebService.getLabel(id).fold(
                    ifLeft = { UNKNOWN_TITLE },
                    ifRight = { it.name }
                )
            }
            ResourceType.MASTER -> {
                discogsWebService.getMaster(id).fold(
                    ifLeft = { UNKNOWN_TITLE },
                    ifRight = { it.title }
                )
            }
            ResourceType.RELEASE -> {
                discogsWebService.getRelease(id).fold(
                    ifLeft = { UNKNOWN_TITLE },
                    ifRight = { it.title }
                )
            }
        }

    // endregion Methods

    // region Companion object

    companion object {
        private const val UNKNOWN_ID = -1L
        private const val UNKNOWN_TITLE = "???"
    }

    // endregion Companion object

}
