package com.codepunk.skeleton.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.codepunk.skeleton.data.local.entity.LocalResource
import com.codepunk.skeleton.data.local.entity.LocalResourceDetail

@Dao
interface ResourceDao {

    // region Methods

    @Insert
    suspend fun insertResource(resource: LocalResource): Long

    @Delete
    fun deleteResource(resource: LocalResource): Int

    @Insert
    abstract suspend fun insertResourceDetails(details: List<LocalResourceDetail>): List<Long>

    @Query("""
        SELECT resource.*
          FROM resource
          LEFT OUTER JOIN artist
            ON resource.resource_id = artist.resource_id
         WHERE artist.artist_id = :artistId
    """)
    abstract suspend fun getResourceByArtistId(artistId: Long): LocalResource?

    @Query(
        """
        SELECT resource.*
          FROM resource
          LEFT OUTER JOIN label
            ON resource.resource_id = label.resource_id
         WHERE label.label_id = :labelId
    """
    )
    abstract suspend fun getResourceByLabelId(labelId: Long): LocalResource?

    @Query(
        """
        SELECT resource.*
          FROM resource
          LEFT OUTER JOIN master
            ON resource.resource_id = master.resource_id
         WHERE master.master_id = :masterId
    """
    )
    abstract suspend fun getResourceByMasterId(masterId: Long): LocalResource?

    @Query(
        """
        SELECT resource.*
          FROM resource
          LEFT OUTER JOIN `release`
            ON resource.resource_id = `release`.release_id
         WHERE `release`.release_id = :releaseId
    """
    )
    abstract suspend fun getResourceByReleaseId(releaseId: Long): LocalResource?

    // Delete detail should be cascaded when deleting Resource

    // endregion Methods

}
