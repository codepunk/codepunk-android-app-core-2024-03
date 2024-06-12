package com.codepunk.skeleton.data.local.dao

import com.codepunk.skeleton.data.local.DetailType
import com.codepunk.skeleton.data.local.entity.LocalMaster
import com.codepunk.skeleton.data.local.entity.LocalMasterDetail
import com.codepunk.skeleton.data.local.relation.LocalMasterWithDetails
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MasterDaoWrapper @Inject constructor(
    private val base: MasterDao
) : MasterDao() {

    override suspend fun upsertMaster(master: LocalMaster): Long =
        base.upsertMaster(master)

    override suspend fun upsertMasterDetail(detail: LocalMasterDetail) {
        base.upsertMasterDetail(detail)
    }

    override suspend fun upsertMasterDetails(details: List<LocalMasterDetail>) {
        base.upsertMasterDetails(details)
    }

    override suspend fun upsertMasterWithDetails(masterWithDetails: LocalMasterWithDetails) {
        upsertMaster(masterWithDetails.master)
        upsertMasterDetails(masterWithDetails.toLocalMasterDetails())
    }

    private fun LocalMasterWithDetails.toLocalMasterDetails(): List<LocalMasterDetail> =
        DetailType.entries.map { detailType ->
            when (detailType) {
                DetailType.FORMAT -> format
                DetailType.STYLE -> style
                DetailType.LABEL -> label
                DetailType.GENRE -> genre
                DetailType.BARCODE -> barcode
            }.mapIndexed { index, detail ->
                LocalMasterDetail(
                    masterId = master.id,
                    detailType = detailType,
                    detailIdx = index,
                    detail = detail
                )
            }
        }.flatten()

}