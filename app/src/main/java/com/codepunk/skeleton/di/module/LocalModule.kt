package com.codepunk.skeleton.di.module

import android.content.Context
import androidx.room.Room
import com.codepunk.skeleton.data.local.DiscogsDatabase
import com.codepunk.skeleton.data.local.dao.ArtistDao
import com.codepunk.skeleton.data.local.dao.CreditDao
import com.codepunk.skeleton.data.local.dao.DiscogsDao
import com.codepunk.skeleton.data.local.dao.FormatDao
import com.codepunk.skeleton.data.local.dao.IdentifierDao
import com.codepunk.skeleton.data.local.dao.ImageDao
import com.codepunk.skeleton.data.local.dao.LabelDao
import com.codepunk.skeleton.data.local.dao.MasterDao
import com.codepunk.skeleton.data.local.dao.RelatedArtistDao
import com.codepunk.skeleton.data.local.dao.RelatedLabelDao
import com.codepunk.skeleton.data.local.dao.ReleaseDao
import com.codepunk.skeleton.data.local.dao.ResourceDao
import com.codepunk.skeleton.data.local.dao.TrackDao
import com.codepunk.skeleton.data.local.dao.VideoDao
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
    fun provideDiscogsDao(database: DiscogsDatabase): DiscogsDao = database.discogsDao()

    @Provides
    @Singleton
    fun provideArtistDao(database: DiscogsDatabase): ArtistDao = database.artistDao()

    @Provides
    @Singleton
    fun provideCreditDao(database: DiscogsDatabase): CreditDao = database.creditDao()

    @Provides
    @Singleton
    fun provideFormatDao(database: DiscogsDatabase): FormatDao = database.formatDao()

    @Provides
    @Singleton
    fun provideIdentifierDao(database: DiscogsDatabase): IdentifierDao = database.identifierDao()

    @Provides
    @Singleton
    fun provideImageDao(database: DiscogsDatabase): ImageDao = database.imageDao()

    @Provides
    @Singleton
    fun provideLabelDao(database: DiscogsDatabase): LabelDao = database.labelDao()

    @Provides
    @Singleton
    fun provideMasterDao(database: DiscogsDatabase): MasterDao = database.masterDao()

    @Provides
    @Singleton
    fun provideRelatedArtistDao(database: DiscogsDatabase): RelatedArtistDao =
        database.relatedArtistDao()

    @Provides
    @Singleton
    fun provideRelatedLabelDao(database: DiscogsDatabase): RelatedLabelDao =
        database.relatedLabelDao()

    @Provides
    @Singleton
    fun provideReleaseDao(database: DiscogsDatabase): ReleaseDao = database.releaseDao()

    @Provides
    @Singleton
    fun provideResourceDao(database: DiscogsDatabase): ResourceDao = database.resourceDao()

    @Provides
    @Singleton
    fun provideTrackDao(database: DiscogsDatabase): TrackDao = database.trackDao()

    @Provides
    @Singleton
    fun provideVideoDao(database: DiscogsDatabase): VideoDao = database.videoDao()

    // endregion Methods

}