package com.codepunk.skeleton.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.codepunk.skeleton.data.local.entity.LocalLabel
import com.codepunk.skeleton.data.local.entity.LocalLabelDetail
import com.codepunk.skeleton.data.local.entity.LocalLabelRelationship
import com.codepunk.skeleton.data.local.relation.LocalLabelImageCrossRef
import com.codepunk.skeleton.data.local.relation.LocalLabelSubLabelCrossRef
import com.codepunk.skeleton.data.local.relation.LocalLabelWithDetails
import kotlinx.coroutines.flow.Flow

@Dao
abstract class LabelDao {

    /*
     * TODO
     * Using OnConflictStrategy.IGNORE stops errors but will not work in the
     * future if I include things that actually change (like "stats" in releases)
     * In this case we can think about OnConflictStrategy.REPLACE
     * (or even @Update or @Upsert)
     */

    // region Methods

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract suspend fun insertLabel(label: LocalLabel): Long

    @Suppress("Unused")
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract suspend fun insertLabels(labels: List<LocalLabel>): List<Long>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract suspend fun insertLabelImageCrossRef(crossRef: LocalLabelImageCrossRef)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract suspend fun insertLabelImageCrossRefs(crossRefs: List<LocalLabelImageCrossRef>)

    @Suppress("Unused")
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract suspend fun insertLabelDetail(detail: LocalLabelDetail)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract suspend fun insertLabelDetails(details: List<LocalLabelDetail>)

    @Suppress("Unused")
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract suspend fun insertLabelRelationship(subLabel: LocalLabelRelationship): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract suspend fun insertLabelRelationships(
        subLabels: List<LocalLabelRelationship>
    ): List<Long>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract suspend fun insertLabelSubLabelCrossRefs(crossRefs: List<LocalLabelSubLabelCrossRef>)

    @Transaction
    @Query("SELECT * FROM label WHERE id = :id")
    abstract fun getLabelWithDetails(id: Long): Flow<LocalLabelWithDetails?>

    open suspend fun insertLabelWithDetails(
        labelWithDetails: LocalLabelWithDetails
    ): Long = throw IllegalStateException(
        "This method can only be called from a descendant of this class"
    )

    // endregion Methods

}