package com.codepunk.skeleton.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.codepunk.skeleton.data.local.entity.LocalLabel
import com.codepunk.skeleton.data.local.relation.LocalResourceAndLabel
import kotlinx.coroutines.flow.Flow

@Dao
interface LabelDao {

    // region Methods

    @Insert
    suspend fun insertLabel(label: LocalLabel): Long

    @Transaction
    @Query("""
        SELECT resource.*
          FROM resource
          LEFT OUTER JOIN label
            ON resource.resource_id = label.resource_id
         WHERE label.label_id = :labelId
    """)
    fun getResourceAndLabel(labelId: Long): Flow<LocalResourceAndLabel?>

    // endregion Methods

}
