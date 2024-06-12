package com.codepunk.skeleton.data.local.dao

import androidx.room.Dao
import androidx.room.Upsert
import com.codepunk.skeleton.data.local.entity.LocalLabel

@Dao
interface LabelDao {

    // region Methods

    @Upsert
    abstract suspend fun upsertLabel(label: LocalLabel): Long

    // TODO SCOTT Query methods for LocalLabel !!

    // endregion Methods

}
