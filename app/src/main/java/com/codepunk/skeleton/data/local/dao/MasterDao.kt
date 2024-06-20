package com.codepunk.skeleton.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.codepunk.skeleton.data.local.entity.LocalMaster
import com.codepunk.skeleton.data.local.entity.LocalMasterDetail
import com.codepunk.skeleton.data.local.relation.LocalMasterCreditCrossRef
import com.codepunk.skeleton.data.local.relation.LocalMasterImageCrossRef
import com.codepunk.skeleton.data.local.relation.LocalMasterTrackCrossRef
import com.codepunk.skeleton.data.local.relation.LocalMasterVideoCrossRef
import com.codepunk.skeleton.data.local.relation.LocalMasterWithDetails
import kotlinx.coroutines.flow.Flow

@Dao
abstract class MasterDao {

    /*
     * TODO
     * Using OnConflictStrategy.IGNORE stops errors but will not work in the
     * future if I include things that actually change (like "stats" in releases)
     * In this case we can think about OnConflictStrategy.REPLACE
     * (or even @Update or @Upsert)
     */

    // region Methods

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract suspend fun insertMaster(master: LocalMaster): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract suspend fun insertMasters(masters: List<LocalMaster>): List<Long>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract suspend fun insertMasterCreditCrossRef(crossRef: LocalMasterCreditCrossRef)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract suspend fun insertMasterCreditCrossRefs(crossRefs: List<LocalMasterCreditCrossRef>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract suspend fun insertMasterImageCrossRef(crossRef: LocalMasterImageCrossRef)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract suspend fun insertMasterImageCrossRefs(crossRefs: List<LocalMasterImageCrossRef>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract suspend fun insertMasterTrackCrossRef(crossRef: LocalMasterTrackCrossRef)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract suspend fun insertMasterTrackCrossRefs(crossRefs: List<LocalMasterTrackCrossRef>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract suspend fun insertMasterVideoCrossRef(crossRef: LocalMasterVideoCrossRef)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract suspend fun insertMasterVideoCrossRefs(crossRefs: List<LocalMasterVideoCrossRef>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract suspend fun insertMasterDetail(detail: LocalMasterDetail)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract suspend fun insertMasterDetails(details: List<LocalMasterDetail>)

    @Transaction
    @Query("SELECT * FROM master WHERE id = :id")
    abstract fun getMasterWithDetails(id: Long): Flow<LocalMasterWithDetails?>

    open suspend fun insertMasterWithDetails(
        masterWithDetails: LocalMasterWithDetails
    ): Long = throw IllegalStateException(
        "This method can only be called from a descendant of this class"
    )

    // endregion Methods}

}
