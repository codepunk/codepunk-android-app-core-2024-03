package com.codepunk.skeleton.di.module

import com.codepunk.skeleton.data.local.dao.DiscogsDao
import com.codepunk.skeleton.data.remote.webservice.DiscogsWebservice
import com.codepunk.skeleton.data.repository.DiscogsRepositoryImpl
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
        discogsDao: DiscogsDao,
        discogsWebservice: DiscogsWebservice
    ) : DiscogsRepositoryV2 = DiscogsRepositoryImpl(
        discogsDao = discogsDao,
        discogsWebService = discogsWebservice
    )

}
