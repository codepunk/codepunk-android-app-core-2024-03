package com.codepunk.skeleton.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.codepunk.skeleton.data.local.dao.DiscogsDao
import com.codepunk.skeleton.data.local.entity.LocalArtist
import com.codepunk.skeleton.data.local.entity.LocalArtistReference
import com.codepunk.skeleton.data.local.entity.LocalCredit
import com.codepunk.skeleton.data.local.entity.LocalFormat
import com.codepunk.skeleton.data.local.entity.LocalFormatDescription
import com.codepunk.skeleton.data.local.entity.LocalIdentifier
import com.codepunk.skeleton.data.local.entity.LocalImage
import com.codepunk.skeleton.data.local.entity.LocalLabel
import com.codepunk.skeleton.data.local.entity.LocalLabelReference
import com.codepunk.skeleton.data.local.entity.LocalMaster
import com.codepunk.skeleton.data.local.entity.LocalRelease
import com.codepunk.skeleton.data.local.entity.LocalResource
import com.codepunk.skeleton.data.local.entity.LocalResourceDetail
import com.codepunk.skeleton.data.local.entity.LocalTrack
import com.codepunk.skeleton.data.local.entity.LocalVideo
import com.codepunk.skeleton.data.local.relation.LocalResourceCreditCrossRef
import com.codepunk.skeleton.data.local.relation.LocalResourceImageCrossRef
import com.codepunk.skeleton.data.local.relation.LocalResourceTrackCrossRef
import com.codepunk.skeleton.data.local.relation.LocalResourceVideoCrossRef
import com.codepunk.skeleton.data.local.relation.LocalTrackCreditCrossRef
import com.codepunk.skeleton.data.local.typeconverter.BigDecimalTypeConverter
import com.codepunk.skeleton.data.local.typeconverter.InstantTypeConverter
import com.codepunk.skeleton.data.local.typeconverter.LocalDateTypeConverter

@Database(
    version = 1,
    entities = [
        LocalArtist::class,
        LocalArtistReference::class,
        LocalCredit::class,
        LocalFormat::class,
        LocalFormatDescription::class,
        LocalIdentifier::class,
        LocalImage::class,
        LocalLabel::class,
        LocalLabelReference::class,
        LocalMaster::class,
        LocalRelease::class,
        LocalResource::class,
        LocalResourceCreditCrossRef::class,
        LocalResourceDetail::class,
        LocalResourceImageCrossRef::class,
        LocalResourceTrackCrossRef::class,
        LocalResourceVideoCrossRef::class,
        LocalTrack::class,
        LocalTrackCreditCrossRef::class,
        LocalVideo::class
    ]
)
@TypeConverters(
    value = [
        BigDecimalTypeConverter::class,
        InstantTypeConverter::class,
        LocalDateTypeConverter::class
    ]
)
abstract class DiscogsDatabase: RoomDatabase() {

    // region Methods

    abstract fun discogsDao(): DiscogsDao

    // endregion Methods
}