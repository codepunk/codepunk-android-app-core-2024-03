package com.codepunk.skeleton.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import com.codepunk.skeleton.data.local.entity.LocalRelatedLabel

@Dao
interface RelatedLabelDao {

    // region Methods

    @Insert
    abstract suspend fun insertRelatedLabels(
        relatedLabels: List<LocalRelatedLabel>
    ): List<Long>

    // endregion Methods

}
