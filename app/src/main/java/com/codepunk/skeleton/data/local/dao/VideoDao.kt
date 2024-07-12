package com.codepunk.skeleton.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
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

    suspend fun insertResourceVideos(
        resourceId: Long,
        videos: List<LocalVideo>
    ): List<Long> = insertVideos(videos).apply {
        filter { it != -1L }
            .map { LocalResourceVideoCrossRef(resourceId, it) }
            .run { insertResourceVideoCrossRefs(this) }
    }

    // endregion Methods

}
