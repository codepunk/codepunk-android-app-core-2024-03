package com.codepunk.skeleton.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.codepunk.skeleton.data.local.dao.ArtistDao
import com.codepunk.skeleton.data.local.dao.LabelDao
import com.codepunk.skeleton.data.local.dao.MasterDao
import com.codepunk.skeleton.data.local.dao.ReleaseDao
import com.codepunk.skeleton.data.local.entity.ReleaseFormat
import com.codepunk.skeleton.data.local.entity.ReleaseFormatDescription

@Database(
    version = 1,
    entities = [
        ReleaseFormat::class,
        ReleaseFormatDescription::class
    ]
)
abstract class DiscogsDatabase: RoomDatabase() {

    // region Methods

    /*
    abstract fun artistDao(): ArtistDao
    abstract fun labelDao(): LabelDao
    abstract fun masterDao(): MasterDao
     */
    abstract fun releaseDao(): ReleaseDao

    // endregion Methods

}
