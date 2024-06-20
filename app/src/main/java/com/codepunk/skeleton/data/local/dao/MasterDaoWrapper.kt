package com.codepunk.skeleton.data.local.dao

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Transaction
import com.codepunk.skeleton.data.local.entity.LocalMaster
import com.codepunk.skeleton.data.local.entity.LocalMasterDetail
import com.codepunk.skeleton.data.local.relation.LocalMasterCreditCrossRef
import com.codepunk.skeleton.data.local.relation.LocalMasterImageCrossRef
import com.codepunk.skeleton.data.local.relation.LocalMasterTrackCrossRef
import com.codepunk.skeleton.data.local.relation.LocalMasterVideoCrossRef
import com.codepunk.skeleton.data.local.relation.LocalMasterWithDetails
import kotlinx.coroutines.flow.Flow

class MasterDaoWrapper(
    private val wrapped: MasterDao,
    private val imageDao: ImageDao,
    private val trackDao: TrackDao,
    private val creditDao: CreditDao,
    private val videoDao: VideoDao
) : MasterDao() {

    // region Implemented methods

    override suspend fun insertMaster(master: LocalMaster): Long =
        wrapped.insertMaster(master)

    override suspend fun insertMasters(masters: List<LocalMaster>): List<Long> =
        wrapped.insertMasters(masters)

    override suspend fun insertMasterImageCrossRef(crossRef: LocalMasterImageCrossRef) {
        wrapped.insertMasterImageCrossRef(crossRef)
    }

    override suspend fun insertMasterImageCrossRefs(crossRefs: List<LocalMasterImageCrossRef>) {
        wrapped.insertMasterImageCrossRefs(crossRefs)
    }

    override suspend fun insertMasterCreditCrossRef(crossRef: LocalMasterCreditCrossRef) {
        wrapped.insertMasterCreditCrossRef(crossRef)
    }

    override suspend fun insertMasterCreditCrossRefs(crossRefs: List<LocalMasterCreditCrossRef>) {
        wrapped.insertMasterCreditCrossRefs(crossRefs)
    }

    override suspend fun insertMasterTrackCrossRef(crossRef: LocalMasterTrackCrossRef) {
        wrapped.insertMasterTrackCrossRef(crossRef)
    }

    override suspend fun insertMasterTrackCrossRefs(crossRefs: List<LocalMasterTrackCrossRef>) {
        wrapped.insertMasterTrackCrossRefs(crossRefs)
    }

    override suspend fun insertMasterVideoCrossRef(crossRef: LocalMasterVideoCrossRef) {
        wrapped.insertMasterVideoCrossRef(crossRef)
    }

    override suspend fun insertMasterVideoCrossRefs(crossRefs: List<LocalMasterVideoCrossRef>) {
        wrapped.insertMasterVideoCrossRefs(crossRefs)
    }

    override suspend fun insertMasterDetail(detail: LocalMasterDetail) {
        wrapped.insertMasterDetail(detail)
    }

    override suspend fun insertMasterDetails(details: List<LocalMasterDetail>) {
        wrapped.insertMasterDetails(details)
    }

    override fun getMasterWithDetails(id: Long): Flow<LocalMasterWithDetails?> =
        wrapped.getMasterWithDetails(id)

    // endregion Implemented methods

    // region Methods

    @Transaction
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    override suspend fun insertMasterWithDetails(
        masterWithDetails: LocalMasterWithDetails
    ): Long {
        val masterId = wrapped.insertMaster(masterWithDetails.master)
        if (masterId != -1L) {
            val imageCrossRefs = imageDao.insertImages(masterWithDetails.images)
                .filter { it != -1L }
                .map { LocalMasterImageCrossRef(masterId = masterId, imageId = it) }
            wrapped.insertMasterImageCrossRefs(imageCrossRefs)
            wrapped.insertMasterDetails(masterWithDetails.details)
            val trackCrossRefs = trackDao.insertTracks(masterWithDetails.trackList)
                .filter { it != -1L }
                .map { LocalMasterTrackCrossRef(masterId = masterId, trackId = it) }
            wrapped.insertMasterTrackCrossRefs(trackCrossRefs)
            val creditCrossRefs = creditDao.insertCredits(masterWithDetails.artists)
                .filter { it != -1L }
                .map { LocalMasterCreditCrossRef(masterId = masterId, creditId = it) }
            wrapped.insertMasterCreditCrossRefs(creditCrossRefs)
            val videoCrossRefs = videoDao.insertVideos(masterWithDetails.videos)
                .filter { it != -1L }
                .map { LocalMasterVideoCrossRef(masterId = masterId, videoId = it) }
            wrapped.insertMasterVideoCrossRefs(videoCrossRefs)
        }
        return masterId
    }

    // endregion Methods

}