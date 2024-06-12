package com.codepunk.skeleton.data.local.dao

import androidx.room.Dao
import androidx.room.Upsert
import com.codepunk.skeleton.data.local.entity.LocalRelease
import com.codepunk.skeleton.data.local.entity.LocalReleaseDetail
import com.codepunk.skeleton.data.local.entity.LocalReleaseFormat
import com.codepunk.skeleton.data.local.entity.LocalReleaseFormatDescription
import com.codepunk.skeleton.data.local.relation.LocalReleaseFormatWithDescriptions
import com.codepunk.skeleton.data.local.relation.LocalReleaseWithDetails

@Dao
abstract class ReleaseDao {

    // region Methods

    @Upsert
    abstract suspend fun upsertRelease(release: LocalRelease): Long

    @Upsert
    abstract suspend fun upsertReleaseDetail(detail: LocalReleaseDetail)

    @Upsert
    abstract suspend fun upsertReleaseDetails(details: List<LocalReleaseDetail>)

    @Upsert
    abstract suspend fun upsertReleaseFormat(format: LocalReleaseFormat): Long

    @Upsert
    abstract suspend fun upsertReleaseFormats(formats: List<LocalReleaseFormat>)

    @Upsert
    abstract suspend fun upsertReleaseFormatDescription(
        description: LocalReleaseFormatDescription
    )

    @Upsert
    abstract suspend fun upsertReleaseFormatDescriptions(
        descriptions: List<LocalReleaseFormatDescription>
    )

    open suspend fun upsertReleaseFormatWithDescriptions(
        formatWithDescriptions: LocalReleaseFormatWithDescriptions
    ) {
        // No op
    }

    open suspend fun upsertReleaseWithDetails(releaseWithDetails: LocalReleaseWithDetails) {
        // No op
    }

    // TODO SCOTT Query methods for LocalRelease !!

    // endregion Methods

}
