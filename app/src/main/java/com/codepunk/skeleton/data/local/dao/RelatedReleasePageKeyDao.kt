package com.codepunk.skeleton.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.codepunk.skeleton.data.local.entity.LocalRelatedReleasePageKeys

@Dao
interface RelatedReleasePageKeyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPageKeys(pageKeys: List<LocalRelatedReleasePageKeys>)

    @Query("""
        SELECT * 
        FROM related_release_page_keys 
        WHERE related_release_id = :relatedReleaseId
    """)
    suspend fun getPageKeys(relatedReleaseId: Long): LocalRelatedReleasePageKeys?

    @Query("""
        SELECT created_at 
        FROM related_release_page_keys 
        ORDER BY created_at DESC 
        LIMIT 1
    """)
    suspend fun getCreationTime(): Long?

}
