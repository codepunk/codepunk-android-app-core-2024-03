package com.codepunk.skeleton.di.module

import com.codepunk.skeleton.data.local.dao.ArtistDao
import com.codepunk.skeleton.data.local.dao.LabelDao
import com.codepunk.skeleton.data.remote.webservice.DiscogsWebService
import com.codepunk.skeleton.data.repository.DiscogsRepositoryImpl
import com.codepunk.skeleton.domain.repository.DiscogsRepository
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
        discogsWebService: DiscogsWebService,
    ): DiscogsRepository = DiscogsRepositoryImpl(
        artistDao = artistDao,
        labelDao = labelDao,
        discogsWebService = discogsWebService
    )

}
