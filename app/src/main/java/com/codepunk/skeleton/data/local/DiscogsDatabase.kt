package com.codepunk.skeleton.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.codepunk.skeleton.data.local.dao.ArtistDao
import com.codepunk.skeleton.data.local.dao.CreditDao
import com.codepunk.skeleton.data.local.dao.ImageDao
import com.codepunk.skeleton.data.local.dao.LabelDao
import com.codepunk.skeleton.data.local.dao.MasterDao
import com.codepunk.skeleton.data.local.dao.TrackDao
import com.codepunk.skeleton.data.local.dao.VideoDao
import com.codepunk.skeleton.data.local.entity.LocalArtist
import com.codepunk.skeleton.data.local.entity.LocalArtistDetail
import com.codepunk.skeleton.data.local.entity.LocalArtistRelationship
import com.codepunk.skeleton.data.local.entity.LocalCredit
import com.codepunk.skeleton.data.local.entity.LocalFormat
import com.codepunk.skeleton.data.local.entity.LocalFormatDetail
import com.codepunk.skeleton.data.local.entity.LocalImage
import com.codepunk.skeleton.data.local.entity.LocalLabel
import com.codepunk.skeleton.data.local.entity.LocalLabelDetail
import com.codepunk.skeleton.data.local.entity.LocalMaster
import com.codepunk.skeleton.data.local.entity.LocalMasterDetail
import com.codepunk.skeleton.data.local.entity.LocalLabelRelationship
import com.codepunk.skeleton.data.local.entity.LocalRelease
import com.codepunk.skeleton.data.local.entity.LocalReleaseDetail
import com.codepunk.skeleton.data.local.entity.LocalTrack
import com.codepunk.skeleton.data.local.entity.LocalVideo
import com.codepunk.skeleton.data.local.relation.LocalArtistAliasCrossRef
import com.codepunk.skeleton.data.local.relation.LocalArtistGroupCrossRef
import com.codepunk.skeleton.data.local.relation.LocalArtistImageCrossRef
import com.codepunk.skeleton.data.local.relation.LocalArtistMemberCrossRef
import com.codepunk.skeleton.data.local.relation.LocalLabelImageCrossRef
import com.codepunk.skeleton.data.local.relation.LocalLabelSubLabelCrossRef
import com.codepunk.skeleton.data.local.relation.LocalMasterCreditCrossRef
import com.codepunk.skeleton.data.local.relation.LocalMasterImageCrossRef
import com.codepunk.skeleton.data.local.relation.LocalMasterTrackCrossRef
import com.codepunk.skeleton.data.local.relation.LocalMasterVideoCrossRef
import com.codepunk.skeleton.data.local.typeconverter.BigDecimalTypeConverter

@Database(
    version = 1,
    entities = [
        LocalArtist::class,
        LocalArtistDetail::class,
        LocalArtistAliasCrossRef::class,
        LocalArtistGroupCrossRef::class,
        LocalArtistImageCrossRef::class,
        LocalArtistMemberCrossRef::class,
        LocalArtistRelationship::class,
        LocalCredit::class,
        LocalImage::class,
        LocalFormat::class,
        LocalFormatDetail::class,
        LocalLabel::class,
        LocalLabelDetail::class,
        LocalLabelImageCrossRef::class,
        LocalLabelSubLabelCrossRef::class,
        LocalMaster::class,
        LocalMasterDetail::class,
        LocalMasterCreditCrossRef::class,
        LocalMasterTrackCrossRef::class,
        LocalMasterImageCrossRef::class,
        LocalMasterVideoCrossRef::class,
        LocalLabelRelationship::class,
        LocalRelease::class,
        LocalReleaseDetail::class,
        LocalTrack::class,
        LocalVideo::class
    ]
)
@TypeConverters(
    BigDecimalTypeConverter::class
)
abstract class DiscogsDatabase: RoomDatabase() {

    // region Methods

    abstract fun artistDao(): ArtistDao
    abstract fun creditDao(): CreditDao
    abstract fun imageDao(): ImageDao
    abstract fun labelDao(): LabelDao
    abstract fun masterDao(): MasterDao
    abstract fun trackDao(): TrackDao
    abstract fun videoDao(): VideoDao
    /*
    abstract fun releaseDao(): ReleaseDao
     */

    // endregion Methods

}
