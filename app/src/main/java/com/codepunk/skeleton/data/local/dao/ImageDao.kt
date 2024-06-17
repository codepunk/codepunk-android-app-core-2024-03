package com.codepunk.skeleton.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.codepunk.skeleton.data.local.entity.LocalImage

@Dao
interface ImageDao {

    @Suppress("Unused")
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertImage(image: LocalImage): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertImages(images: List<LocalImage>): List<Long>

}