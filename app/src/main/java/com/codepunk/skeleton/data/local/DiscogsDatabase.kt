package com.codepunk.skeleton.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.codepunk.skeleton.data.local.dao.ArtistDao
import com.codepunk.skeleton.data.local.dao.ImageDao
import com.codepunk.skeleton.data.local.dao.LabelDao
import com.codepunk.skeleton.data.local.entity.LocalArtist
import com.codepunk.skeleton.data.local.entity.LocalArtistDetail
import com.codepunk.skeleton.data.local.entity.LocalArtistRelationship
import com.codepunk.skeleton.data.local.entity.LocalImage
import com.codepunk.skeleton.data.local.entity.LocalLabel
import com.codepunk.skeleton.data.local.entity.LocalLabelDetail
import com.codepunk.skeleton.data.local.entity.LocalSubLabel
import com.codepunk.skeleton.data.local.relation.LocalArtistImageCrossRef
import com.codepunk.skeleton.data.local.relation.LocalLabelImageCrossRef

@Database(
    version = 1,
    entities = [
        LocalArtist::class,
        LocalArtistDetail::class,
        LocalArtistImageCrossRef::class,
        LocalArtistRelationship::class,
        LocalImage::class,
        LocalLabel::class,
        LocalLabelDetail::class,
        LocalLabelImageCrossRef::class,
        LocalSubLabel::class,
    ]
)
abstract class DiscogsDatabase: RoomDatabase() {

    // region Methods

    abstract fun artistDao(): ArtistDao
    abstract fun imageDao(): ImageDao
    abstract fun labelDao(): LabelDao
    /*
    abstract fun masterDao(): MasterDao
    abstract fun releaseDao(): ReleaseDao
     */

    // endregion Methods

}
