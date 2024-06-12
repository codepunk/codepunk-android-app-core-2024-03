package com.codepunk.skeleton.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.codepunk.skeleton.data.local.converter.LocalDetailTypeTypeConverter
import com.codepunk.skeleton.data.local.dao.ArtistDao
import com.codepunk.skeleton.data.local.dao.LabelDao
import com.codepunk.skeleton.data.local.dao.MasterDao
import com.codepunk.skeleton.data.local.dao.ReleaseDao
import com.codepunk.skeleton.data.local.entity.LocalArtist
import com.codepunk.skeleton.data.local.entity.LocalLabel
import com.codepunk.skeleton.data.local.entity.LocalMaster
import com.codepunk.skeleton.data.local.entity.LocalMasterDetail
import com.codepunk.skeleton.data.local.entity.LocalRelease
import com.codepunk.skeleton.data.local.entity.LocalReleaseDetail
import com.codepunk.skeleton.data.local.entity.LocalReleaseFormat
import com.codepunk.skeleton.data.local.entity.LocalReleaseFormatDescription

@Database(
    version = 1,
    entities = [
        LocalArtist::class,
        LocalLabel::class,
        LocalRelease::class,
        LocalReleaseDetail::class,
        LocalReleaseFormat::class,
        LocalReleaseFormatDescription::class,
        LocalMaster::class,
        LocalMasterDetail::class
    ]
)
@TypeConverters(LocalDetailTypeTypeConverter::class)
abstract class DiscogsDatabase: RoomDatabase() {

    // region Methods

    abstract fun artistDao(): ArtistDao
    abstract fun labelDao(): LabelDao
    abstract fun masterDao(): MasterDao
    abstract fun releaseDao(): ReleaseDao

    // endregion Methods

}
