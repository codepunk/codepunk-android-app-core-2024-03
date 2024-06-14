package com.codepunk.skeleton.di.module

import android.content.Context
import androidx.room.Room
import com.codepunk.skeleton.data.local.DiscogsDatabase
import com.codepunk.skeleton.data.local.dao.ArtistDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalModule {

    // region Methods

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): DiscogsDatabase =
        Room.databaseBuilder(
            context = context,
            klass = DiscogsDatabase::class.java,
            name = "discogs_db"
        ).build()

    @Provides
    @Singleton
    fun provideArtistDao(database: DiscogsDatabase): ArtistDao =
        database.artistDao()

    /*
    @Provides
    @Singleton
    fun provideLabelDao(database: DiscogsDatabase): LabelDao =
        database.labelDao()

    @Provides
    @Singleton
    fun provideMasterDao(database: DiscogsDatabase): MasterDao =
        MasterDaoWrapper(database.masterDao())

    @Provides
    @Singleton
    fun provideReleaseDao(database: DiscogsDatabase): ReleaseDao =
        ReleaseDaoWrapper(database.releaseDao())
     */

    // endregion Methods

}