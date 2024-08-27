package com.codepunk.skeleton.di.module

import com.codepunk.skeleton.data.local.dao.AllDao
import com.codepunk.skeleton.data.local.dao.ArtistDao
import com.codepunk.skeleton.data.local.dao.LabelDao
import com.codepunk.skeleton.data.local.dao.MasterDao
import com.codepunk.skeleton.data.local.dao.RelatedReleaseDao
import com.codepunk.skeleton.data.local.dao.ReleaseDao
import com.codepunk.skeleton.data.paging.ReleasesByResourceRemoteMediatorFactory
import com.codepunk.skeleton.data.remote.webservice.DiscogsWebservice
import com.codepunk.skeleton.data.repository.DiscogsRepositoryImpl
import com.codepunk.skeleton.domain.repository.DiscogsRepository
import com.codepunk.skeleton.util.BBCodeProcessinator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Singleton
    @Provides
    fun provideDiscogsRepository(
        artistDao: ArtistDao,
        labelDao: LabelDao,
        masterDao: MasterDao,
        relatedReleaseDao: RelatedReleaseDao,
        releaseDao: ReleaseDao,
        allDao: AllDao,
        discogsWebService: DiscogsWebservice,
        factory: ReleasesByResourceRemoteMediatorFactory,
        bbCodeProcessinator: BBCodeProcessinator
    ) : DiscogsRepository = DiscogsRepositoryImpl(
        artistDao = artistDao,
        labelDao = labelDao,
        masterDao = masterDao,
        relatedReleaseDao = relatedReleaseDao,
        releaseDao = releaseDao,
        allDao = allDao,
        discogsWebService = discogsWebService,
        factory = factory,
        bbCodeProcessinator = bbCodeProcessinator
    )

}
