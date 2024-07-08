package com.codepunk.skeleton.di.module

import com.codepunk.skeleton.data.local.dao.ArtistDao
import com.codepunk.skeleton.data.local.dao.LabelDao
import com.codepunk.skeleton.data.local.dao.MasterDao
import com.codepunk.skeleton.data.localv2.dao.DiscogsDao
import com.codepunk.skeleton.data.remote.webservice.DiscogsWebService
import com.codepunk.skeleton.data.remote.webservice.DiscogsWebserviceV2
import com.codepunk.skeleton.data.repository.DiscogsRepositoryImpl
import com.codepunk.skeleton.data.repositoryv2.DiscogsRepositoryImplV2
import com.codepunk.skeleton.domain.repository.DiscogsRepository
import com.codepunk.skeleton.domain.repository.DiscogsRepositoryV2
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
        discogsWebService: DiscogsWebService,
    ): DiscogsRepository = DiscogsRepositoryImpl(
        artistDao = artistDao,
        labelDao = labelDao,
        masterDao = masterDao,
        discogsWebService = discogsWebService
    )

    @Singleton
    @Provides
    fun provideDiscogsRepositoryV2(
        discogsDao: DiscogsDao,
        discogsWebserviceV2: DiscogsWebserviceV2
    ) : DiscogsRepositoryV2 = DiscogsRepositoryImplV2(
        discogsDao = discogsDao,
        discogsWebService = discogsWebserviceV2
    )

}
