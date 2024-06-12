package com.codepunk.skeleton.data.local.dao

import androidx.room.Transaction
import com.codepunk.skeleton.data.local.entity.LocalRelease
import com.codepunk.skeleton.data.local.entity.LocalReleaseDetail
import com.codepunk.skeleton.data.local.entity.LocalReleaseDetail.DetailType
import com.codepunk.skeleton.data.local.entity.LocalReleaseFormat
import com.codepunk.skeleton.data.local.entity.LocalReleaseFormatDescription
import com.codepunk.skeleton.data.local.relation.LocalReleaseFormatWithDescriptions
import com.codepunk.skeleton.data.local.relation.LocalReleaseWithDetails
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ReleaseDaoWrapper @Inject constructor(
    private val base: ReleaseDao
) : ReleaseDao() {

    override suspend fun upsertRelease(release: LocalRelease): Long =
        base.upsertRelease(release)

    override suspend fun upsertReleaseDetail(detail: LocalReleaseDetail) {
        base.upsertReleaseDetail(detail)
    }

    override suspend fun upsertReleaseDetails(details: List<LocalReleaseDetail>) {
        base.upsertReleaseDetails(details)
    }

    override suspend fun upsertReleaseFormat(format: LocalReleaseFormat): Long =
        base.upsertReleaseFormat(format)

    override suspend fun upsertReleaseFormats(formats: List<LocalReleaseFormat>) {
        base.upsertReleaseFormats(formats)
    }

    override suspend fun upsertReleaseFormatDescription(
        description: LocalReleaseFormatDescription
    ) {
        base.upsertReleaseFormatDescription(description)
    }

    override suspend fun upsertReleaseFormatDescriptions(
        descriptions: List<LocalReleaseFormatDescription>
    ) {
        base.upsertReleaseFormatDescriptions(descriptions)
    }

    @Transaction
    override suspend fun upsertReleaseFormatWithDescriptions(
        formatWithDescriptions: LocalReleaseFormatWithDescriptions
    ) {
        val formatId = upsertReleaseFormat(formatWithDescriptions.releaseFormat)
        upsertReleaseFormatDescriptions(
            formatWithDescriptions.descriptions.mapIndexed { index, description ->
                description.copy(
                    formatId = formatId,
                    descriptionIdx = index
                )
            }
        )
    }

    @Transaction
    override suspend fun upsertReleaseWithDetails(releaseWithDetails: LocalReleaseWithDetails) {
        upsertRelease(releaseWithDetails.release)
        upsertReleaseDetails(releaseWithDetails.toLocalReleaseDetails())
        upsertReleaseFormatsWithDescriptions(releaseWithDetails.formats)
    }

    private fun LocalReleaseWithDetails.toLocalReleaseDetails(): List<LocalReleaseDetail> =
        DetailType.entries.map { detailType ->
            when (detailType) {
                DetailType.FORMAT -> format
                DetailType.STYLE -> style
                DetailType.LABEL -> label
                DetailType.GENRE -> genre
                DetailType.BARCODE -> barcode
            }.mapIndexed { index, detail ->
                LocalReleaseDetail(
                    releaseId = release.releaseId,
                    detailType = detailType,
                    detailIdx = index,
                    detail = detail
                )
            }
        }.flatten()

    private suspend fun upsertReleaseFormatsWithDescriptions(
        formats: List<LocalReleaseFormatWithDescriptions>
    ) {
        formats.forEach {
            upsertReleaseFormatWithDescriptions(it)
        }
    }

}
