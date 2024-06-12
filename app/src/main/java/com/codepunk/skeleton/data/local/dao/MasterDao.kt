package com.codepunk.skeleton.data.local.dao

import androidx.room.Dao
import androidx.room.Upsert
import com.codepunk.skeleton.data.local.entity.LocalMaster
import com.codepunk.skeleton.data.local.entity.LocalMasterDetail
import com.codepunk.skeleton.data.local.relation.LocalMasterWithDetails

@Dao
abstract class MasterDao {

    // region Methods

    @Upsert
    abstract suspend fun upsertMaster(master: LocalMaster): Long

    @Upsert
    abstract suspend fun upsertMasterDetail(detail: LocalMasterDetail)

    @Upsert
    abstract suspend fun upsertMasterDetails(details: List<LocalMasterDetail>)

    open suspend fun upsertMasterWithDetails(masterWithDetails: LocalMasterWithDetails) {
        // No op
    }

    // TODO SCOTT Query methods for LocalMaster !!

    // endregion Methods

}
