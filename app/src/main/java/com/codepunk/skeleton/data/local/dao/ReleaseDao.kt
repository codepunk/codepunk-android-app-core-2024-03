package com.codepunk.skeleton.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.codepunk.skeleton.data.local.entity.LocalRelease
import com.codepunk.skeleton.data.local.relation.LocalResourceAndRelease
import kotlinx.coroutines.flow.Flow

@Dao
interface ReleaseDao {

    // region Methods

    @Insert
    suspend fun insertRelease(release: LocalRelease): Long

    @Transaction
    @Query("""
        SELECT resource.*
          FROM resource
          LEFT OUTER JOIN `release`
            ON resource.resource_id = `release`.resource_id
         WHERE `release`.release_id = :releaseId
    """)
    fun getResourceAndRelease(releaseId: Long): Flow<LocalResourceAndRelease?>

    // endregion Methods

}
