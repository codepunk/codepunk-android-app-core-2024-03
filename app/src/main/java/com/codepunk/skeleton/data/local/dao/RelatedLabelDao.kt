package com.codepunk.skeleton.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.codepunk.skeleton.data.local.entity.LocalRelatedLabel

@Dao
abstract class RelatedLabelDao {

    // region Methods

    @Insert
    abstract suspend fun insertRelatedLabels(relatedLabels: List<LocalRelatedLabel>): List<Long>

    @Query("")
    suspend fun insertRelatedLabels(
        resourceId: Long,
        relatedLabels: List<LocalRelatedLabel>
    ): List<Long> = insertRelatedLabels(relatedLabels.map { it.copy(resourceId = resourceId) })

    // endregion Methods

}
