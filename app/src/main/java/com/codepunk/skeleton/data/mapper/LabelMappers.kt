package com.codepunk.skeleton.data.mapper

import com.codepunk.skeleton.data.local.entity.LocalLabel
import com.codepunk.skeleton.data.local.entity.LocalRelatedLabel
import com.codepunk.skeleton.data.local.entity.LocalRelatedLabel.RelationType
import com.codepunk.skeleton.data.local.entity.LocalResourceDetail
import com.codepunk.skeleton.data.local.entity.LocalResourceDetail.DetailType
import com.codepunk.skeleton.data.local.relation.LocalLabelWithDetails
import com.codepunk.skeleton.data.local.relation.LocalResourceAndLabel
import com.codepunk.skeleton.data.remote.entity.RemoteLabel
import com.codepunk.skeleton.data.remote.entity.RemoteRelatedLabel
import com.codepunk.skeleton.domain.model.Label
import com.codepunk.skeleton.domain.model.RelatedLabel

fun RemoteLabel.toLocalLabel(
    resourceId: Long
): LocalLabel =
    LocalLabel(
        labelId = id,
        resourceId = resourceId,
        name = name,
        profile = profile,
        releasesUrl = releasesUrl,
        contactInfo = contactInfo
    )

fun RemoteLabel.toLocal(
    resourceId: Long = 0L
): LocalResourceAndLabel =
    LocalResourceAndLabel(
        resource = toLocalResource(resourceId),
        labelWithDetails = LocalLabelWithDetails(
            label = toLocalLabel(resourceId),
            images = images.mapIndexed { imageIdx, image ->
                image.toLocal(imageIdx = imageIdx)
            },
            details = urls.mapIndexed { detailIdx, url ->
                LocalResourceDetail(resourceId, DetailType.URL, detailIdx, url)
            },
            relatedLabels = listOfNotNull(
                parentLabel?.toLocal(resourceId = resourceId, type = RelationType.PARENT_LABEL)
            ) + subLabels.mapIndexed { labelIdx, relatedLabel ->
                relatedLabel.toLocal(resourceId = resourceId, type = RelationType.SUB_LABEL)
            }
        )
    )

fun LocalResourceAndLabel.toDomain(): Label =
    Label(
        id =labelWithDetails.label.labelId,
        resourceUrl = resource.resourceUrl,
        uri = resource.uri,
        images = labelWithDetails.images.map { it.toDomain() },
        dataQuality = resource.dataQuality,
        name = labelWithDetails.label.name,
        profile = labelWithDetails.label.profile,
        releasesUrl = labelWithDetails.label.releasesUrl,
        urls = labelWithDetails.details.filter {
            it.type == DetailType.URL
        }.map { it.detail },
        contactInfo = labelWithDetails.label.contactInfo,
        parentLabel = labelWithDetails.relatedLabels.filter {
            it.type == RelationType.PARENT_LABEL
        }.map { it.toDomain() }.firstOrNull(),
        subLabels = labelWithDetails.relatedLabels.filter {
            it.type == RelationType.SUB_LABEL
        }.map { it.toDomain() }
    )

fun RemoteRelatedLabel.toLocal(
    relatedLabelId: Long = 0L,
    resourceId: Long = 0L,
    type: RelationType
): LocalRelatedLabel =
    LocalRelatedLabel(
        relatedLabelId = relatedLabelId,
        resourceId = resourceId,
        type = type,
        labelId = labelId,
        name = name,
        resourceUrl = resourceUrl
    )

fun LocalRelatedLabel.toDomain(): RelatedLabel =
    RelatedLabel(
        labelId = labelId,
        name = name,
        resourceUrl = resourceUrl
    )
