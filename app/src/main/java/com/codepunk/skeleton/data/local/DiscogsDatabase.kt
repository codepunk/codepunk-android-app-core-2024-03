package com.codepunk.skeleton.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
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
import com.codepunk.skeleton.data.local.entity.LocalArtist
import com.codepunk.skeleton.data.local.entity.LocalRelatedArtist
import com.codepunk.skeleton.data.local.entity.LocalCredit
import com.codepunk.skeleton.data.local.entity.LocalFormat
import com.codepunk.skeleton.data.local.entity.LocalFormatDetail
import com.codepunk.skeleton.data.local.entity.LocalIdentifier
import com.codepunk.skeleton.data.local.entity.LocalImage
import com.codepunk.skeleton.data.local.entity.LocalLabel
import com.codepunk.skeleton.data.local.entity.LocalRelatedLabel
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
        LocalRelatedArtist::class,
        LocalCredit::class,
        LocalFormat::class,
        LocalFormatDetail::class,
        LocalIdentifier::class,
        LocalImage::class,
        LocalLabel::class,
        LocalRelatedLabel::class,
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

    abstract fun artistDao(): ArtistDao

    abstract fun creditDao(): CreditDao

    abstract fun formatDao(): FormatDao

    abstract fun identifierDao(): IdentifierDao

    abstract fun imageDao(): ImageDao

    abstract fun labelDao(): LabelDao

    abstract fun masterDao(): MasterDao

    abstract fun relatedArtistDao(): RelatedArtistDao

    abstract fun relatedLabelDao(): RelatedLabelDao

    abstract fun releaseDao(): ReleaseDao

    abstract fun resourceDao(): ResourceDao

    abstract fun trackDao(): TrackDao

    abstract fun videoDao(): VideoDao

    // endregion Methods
}