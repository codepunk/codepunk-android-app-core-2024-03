package com.codepunk.skeleton.data.local.dao

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Transaction
import com.codepunk.skeleton.data.local.entity.LocalLabel
import com.codepunk.skeleton.data.local.entity.LocalLabelDetail
import com.codepunk.skeleton.data.local.entity.LocalLabelRelationship
import com.codepunk.skeleton.data.local.relation.LocalLabelImageCrossRef
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

    override suspend fun insertLabelSubLabel(subLabel: LocalLabelRelationship) {
        wrapped.insertLabelSubLabel(subLabel)
    }

    override suspend fun insertLabelSubLabels(subLabels: List<LocalLabelRelationship>) {
        wrapped.insertLabelSubLabels(subLabels)
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
        val labelId = insertLabel(labelWithDetails.label)
        if (labelId != -1L) {
            val crossRefs = imageDao.insertImages(labelWithDetails.images)
                .filter { it != -1L }
                .map { LocalLabelImageCrossRef(labelId = labelId, imageId = it) }
            insertLabelImageCrossRefs(crossRefs)
            insertLabelDetails(labelWithDetails.details)
            insertLabelSubLabels(labelWithDetails.subLabels)
        }
        return labelId
    }

    // endregion Methods

}