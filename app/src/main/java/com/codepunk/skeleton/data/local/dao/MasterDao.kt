package com.codepunk.skeleton.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.codepunk.skeleton.data.local.entity.LocalMaster
import com.codepunk.skeleton.data.local.relation.LocalResourceAndMaster
import kotlinx.coroutines.flow.Flow

@Dao
interface MasterDao {

    // region Methods

    @Insert
    suspend fun insertMaster(master: LocalMaster): Long

    @Transaction
    @Query("""
        SELECT resource.*
          FROM resource
          LEFT OUTER JOIN master
            ON resource.resource_id = master.resource_id
         WHERE master.master_id = :masterId
    """)
    fun getMaster(masterId: Long): Flow<LocalResourceAndMaster?>

    // endregion Methods

}
