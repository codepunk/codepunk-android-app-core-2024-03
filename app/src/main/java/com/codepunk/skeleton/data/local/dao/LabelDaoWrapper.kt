package com.codepunk.skeleton.data.local.dao

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Transaction
import com.codepunk.skeleton.data.local.entity.LocalLabel
import com.codepunk.skeleton.data.local.entity.LocalLabelDetail
import com.codepunk.skeleton.data.local.entity.LocalLabelRelationship
import com.codepunk.skeleton.data.local.relation.LocalLabelImageCrossRef
import com.codepunk.skeleton.data.local.relation.LocalLabelSubLabelCrossRef
import com.codepunk.skeleton.data.local.relation.LocalLabelWithDetails
import kotlinx.coroutines.flow.Flow

class LabelDaoWrapper(
    private val wrapped: LabelDao,
    private val imageDao: ImageDao
) : LabelDao() {

    // region Implemented methods

    override suspend fun insertLabel(label: LocalLabel): Long =
        wrapped.insertLabel(label)

    override suspend fun insertLabels(labels: List<LocalLabel>): List<Long> =
        wrapped.insertLabels(labels)

    override suspend fun insertLabelImageCrossRef(crossRef: LocalLabelImageCrossRef) {
        wrapped.insertLabelImageCrossRef(crossRef)
    }

    override suspend fun insertLabelImageCrossRefs(crossRefs: List<LocalLabelImageCrossRef>) {
        wrapped.insertLabelImageCrossRefs(crossRefs)
    }

    override suspend fun insertLabelDetail(detail: LocalLabelDetail) {
        wrapped.insertLabelDetail(detail)
    }

    override suspend fun insertLabelDetails(details: List<LocalLabelDetail>) {
        wrapped.insertLabelDetails(details)
    }

    override suspend fun insertLabelRelationship(relationship: LocalLabelRelationship): Long =
        wrapped.insertLabelRelationship(relationship)

    override suspend fun insertLabelRelationships(
        relationships: List<LocalLabelRelationship>
    ): List<Long> = wrapped.insertLabelRelationships(relationships)

    override suspend fun insertLabelSubLabelCrossRefs(crossRefs: List<LocalLabelSubLabelCrossRef>) {
        wrapped.insertLabelSubLabelCrossRefs(crossRefs)
    }

    override fun getLabelWithDetails(id: Long): Flow<LocalLabelWithDetails?> =
        wrapped.getLabelWithDetails(id)

    // endregion Implemented methods

    // region Methods

    @Transaction
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    override suspend fun insertLabelWithDetails(
        labelWithDetails: LocalLabelWithDetails
    ): Long {
        labelWithDetails.label.parentLabel?.also { insertLabelRelationship(it) }
        val labelId = insertLabel(labelWithDetails.label)
        if (labelId != -1L) {
            val imageCrossRefs = imageDao.insertImages(labelWithDetails.images)
                .filter { it != -1L }
                .map { LocalLabelImageCrossRef(labelId = labelId, imageId = it) }
            insertLabelImageCrossRefs(imageCrossRefs)
            insertLabelDetails(labelWithDetails.details)
            val subLabelCrossRefs = wrapped.insertLabelRelationships(labelWithDetails.subLabels)
                .filter { it != -1L }
                .mapIndexed { index, id -> LocalLabelSubLabelCrossRef(labelId, id, index) }
            insertLabelSubLabelCrossRefs(subLabelCrossRefs)
        }
        return labelId
    }

    // endregion Methods

}