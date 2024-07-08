package com.codepunk.skeleton.data.localv2

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.codepunk.skeleton.data.local.typeconverter.BigDecimalTypeConverter
import com.codepunk.skeleton.data.localv2.dao.DiscogsDao
import com.codepunk.skeleton.data.localv2.entity.LocalArtist
import com.codepunk.skeleton.data.localv2.entity.LocalArtistReference
import com.codepunk.skeleton.data.localv2.entity.LocalFormat
import com.codepunk.skeleton.data.localv2.entity.LocalFormatDescription
import com.codepunk.skeleton.data.localv2.entity.LocalIdentifier
import com.codepunk.skeleton.data.localv2.entity.LocalImage
import com.codepunk.skeleton.data.localv2.entity.LocalLabel
import com.codepunk.skeleton.data.localv2.entity.LocalLabelReference
import com.codepunk.skeleton.data.localv2.entity.LocalResource
import com.codepunk.skeleton.data.localv2.entity.LocalResourceDetail
import com.codepunk.skeleton.data.localv2.entity.LocalTrack
import com.codepunk.skeleton.data.localv2.entity.LocalVideo
import com.codepunk.skeleton.data.localv2.relation.LocalArtistArtistReferenceCrossRef
import com.codepunk.skeleton.data.localv2.relation.LocalLabelLabelReferenceCrossRef
import com.codepunk.skeleton.data.localv2.relation.LocalResourceImageCrossRef

@Database(
    version = 1,
    entities = [
        LocalArtist::class,
        LocalArtistArtistReferenceCrossRef::class,
        LocalArtistReference::class,
        LocalFormat::class,
        LocalFormatDescription::class,
        LocalIdentifier::class,
        LocalImage::class,
        LocalLabel::class,
        LocalLabelLabelReferenceCrossRef::class,
        LocalLabelReference::class,
        LocalResource::class,
        LocalResourceDetail::class,
        LocalResourceImageCrossRef::class,
        LocalTrack::class,
        LocalVideo::class
    ]
)
@TypeConverters(
    BigDecimalTypeConverter::class
)
abstract class DiscogsDatabaseV2: RoomDatabase() {

    // region Methods

    abstract fun discogsDao(): DiscogsDao

    // endregion Methods
}