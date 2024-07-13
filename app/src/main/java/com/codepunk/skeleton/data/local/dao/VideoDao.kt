package com.codepunk.skeleton.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.codepunk.skeleton.data.local.entity.LocalVideo
import com.codepunk.skeleton.data.local.relation.LocalResourceVideoCrossRef

@Dao
abstract class VideoDao {

    // region Methods

    @Insert
    abstract suspend fun insertVideos(videos: List<LocalVideo>): List<Long>

    @Insert
    abstract suspend fun insertResourceVideoCrossRefs(
        crossRefs: List<LocalResourceVideoCrossRef>
    ): List<Long>

    @Transaction
    @Query("")
    suspend fun insertResourceVideos(
        resourceId: Long,
        videos: List<LocalVideo>
    ): List<Long> = insertVideos(videos).apply {
        filter { it != -1L }
            .map { LocalResourceVideoCrossRef(resourceId, it) }
            .run { insertResourceVideoCrossRefs(this) }
    }

    @Query("""
      DELETE
      FROM video
      WHERE EXISTS (
         SELECT 1
           FROM resource_video_cross_ref
          WHERE resource_video_cross_ref.video_id = video.video_id
            AND resource_video_cross_ref.resource_id = :resourceId
      )
    """)
    abstract suspend fun deleteResourceVideos(resourceId: Long): Int

    // endregion Methods

}
