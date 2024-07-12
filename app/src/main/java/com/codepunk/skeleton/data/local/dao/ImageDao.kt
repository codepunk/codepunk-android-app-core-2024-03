package com.codepunk.skeleton.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.codepunk.skeleton.data.local.entity.LocalImage
import com.codepunk.skeleton.data.local.relation.LocalResourceImageCrossRef

@Dao
abstract class ImageDao {

    // region Methods

    @Insert
    abstract suspend fun insertImages(images: List<LocalImage>): List<Long>

    @Delete
    abstract fun deleteImages(images: List<LocalImage>): Int

    @Insert
    abstract suspend fun insertResourceImageCrossRefs(
        crossRefs: List<LocalResourceImageCrossRef>
    ): List<Long>

    suspend fun insertResourceImages(
        resourceId: Long,
        images: List<LocalImage>
    ): List<Long> = insertImages(images).apply {
        filter { it != -1L }
            .map { LocalResourceImageCrossRef(resourceId, it) }
            .run { insertResourceImageCrossRefs(this) }
    }

    @Query("""
        DELETE 
          FROM image 
         WHERE NOT EXISTS (
               SELECT resource_image_cross_ref.image_id
                 FROM resource_image_cross_ref
                WHERE image.image_id = resource_image_cross_ref.image_id
         )
    """)
    abstract suspend fun cleanImages()

    // endregion Methods

}
