package com.codepunk.skeleton.data.mapper

import com.codepunk.skeleton.data.local.entity.LocalFormat
import com.codepunk.skeleton.data.local.entity.LocalFormatDetail
import com.codepunk.skeleton.data.local.relation.LocalFormatWithDetails
import com.codepunk.skeleton.data.remote.entity.RemoteFormat
import com.codepunk.skeleton.domain.model.Format

fun RemoteFormat.toLocal(
    formatId: Long = 0L,
    releaseId: Long = 0L,
    formatIdx: Int
): LocalFormatWithDetails =
    LocalFormatWithDetails(
        format = LocalFormat(
            // formatId = formatId,
            releaseId = releaseId,
            formatIdx = formatIdx,
            name = name,
            quantity = quantity,
            text = text
        ),
        details = descriptions.mapIndexed { detailIdx, description ->
            LocalFormatDetail(
                formatId = formatId,
                detailIdx = detailIdx,
                detail = description
            )
        }
    )

fun LocalFormatWithDetails.toDomain(): Format =
    Format(
        name = format.name,
        quantity = format.quantity,
        text = format.text,
        descriptions = details.map { it.detail }
    )
