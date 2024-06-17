package com.codepunk.skeleton.data.local.dao

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Transaction
import com.codepunk.skeleton.data.local.entity.LocalLabel
import com.codepunk.skeleton.data.local.entity.LocalLabelDetail
import com.codepunk.skeleton.data.local.entity.LocalSubLabel
import com.codepunk.skeleton.data.local.relation.LocalLabelImageCrossRef
import com.codepunk.skeleton.data.local.relation.LocalLabelWithDetails
import kotlinx.coroutines.flow.Flow

class LabelDaoWrapper(
    private val labelDao: LabelDao,
    private val imageDao: ImageDao
) : LabelDao() {

    // region Implemented methods

    override suspend fun insertLabel(label: LocalLabel): Long =
        labelDao.insertLabel(label)

    override suspend fun insertLabels(labels: List<LocalLabel>): List<Long> =
        labelDao.insertLabels(labels)

    override suspend fun insertLabelImageCrossRef(crossRef: LocalLabelImageCrossRef) {
        labelDao.insertLabelImageCrossRef(crossRef)
    }

    override suspend fun insertLabelImageCrossRefs(crossRefs: List<LocalLabelImageCrossRef>) {
        labelDao.insertLabelImageCrossRefs(crossRefs)
    }

    override suspend fun insertLabelDetail(detail: LocalLabelDetail) {
        labelDao.insertLabelDetail(detail)
    }

    override suspend fun insertLabelDetails(details: List<LocalLabelDetail>) {
        labelDao.insertLabelDetails(details)
    }

    override suspend fun insertLabelSubLabel(subLabel: LocalSubLabel) {
        labelDao.insertLabelSubLabel(subLabel)
    }

    override suspend fun insertLabelSubLabels(subLabels: List<LocalSubLabel>) {
        labelDao.insertLabelSubLabels(subLabels)
    }

    override fun getLabelWithDetails(id: Long): Flow<LocalLabelWithDetails?> =
        labelDao.getLabelWithDetails(id)

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