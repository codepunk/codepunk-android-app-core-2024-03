package com.codepunk.skeleton.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.codepunk.skeleton.data.local.entity.LocalVideo

@Dao
interface VideoDao {

    // region Methods

    @Suppress("Unused")
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertVideo(video: LocalVideo): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertVideos(videos: List<LocalVideo>): List<Long>

    // endregion Methods

}
